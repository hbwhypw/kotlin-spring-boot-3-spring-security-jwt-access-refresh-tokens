package com.codersee.jwtauth.config

import com.codersee.jwtauth.model.ApiError
import com.codersee.jwtauth.model.ValidationError
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.LocaleResolver
import java.util.*

/**
 * 全局异常处理类，用于处理应用程序中抛出的异常
 * @author 杨彭伟
 * @date 2025-11-23
 */
@RestControllerAdvice
class GlobalExceptionHandler(
	private val messageSource: MessageSource,
	private val localeResolver: LocaleResolver
) {

	/**
	 * 处理约束校验异常
	 * @param ex 约束校验异常
	 * @param request HTTP请求对象
	 * @return 包含错误信息的API响应实体
	 */
	@ExceptionHandler(ConstraintViolationException::class)
	fun handleConstraintViolation(
		ex: ConstraintViolationException,
		request: HttpServletRequest
	): ResponseEntity<ApiError> {
		val locale = localeResolver.resolveLocale(request)
		val errors = ex.constraintViolations.map { violation ->
			val fieldName = violation.propertyPath.toString()
			val messageTemplate = violation.messageTemplate

			// 处理消息模板和国际化
			val message = if (messageTemplate.startsWith("{") && messageTemplate.endsWith("}")) {
				val messageKey = messageTemplate.substring(1, messageTemplate.length - 1)
				try {
					// 获取约束属性作为消息参数
					val attributes = violation.constraintDescriptor.attributes
					val messageParams = attributes.values.map { it.toString() }.toTypedArray()
					messageSource.getMessage(messageKey, messageParams, locale)
				} catch (e: Exception) {
					// 回退到默认消息
					violation.message
				}
			} else {
				violation.message
			}
			ValidationError(fieldName, message)
		}

		val apiError = ApiError(
			code = HttpStatus.BAD_REQUEST.value(),
			message = messageSource.getMessage("validation.error", null, locale),
			errors = errors
		)

		return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
	}

	/**
	 * 处理请求参数校验异常
	 * @param ex 请求参数校验异常
	 * @param request HTTP请求对象
	 * @return 包含错误信息的API响应实体
	 */
	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun handleMethodArgumentNotValid(
		ex: MethodArgumentNotValidException,
		request: HttpServletRequest
	): ResponseEntity<ApiError> {
		val locale = localeResolver.resolveLocale(request)
		val errors = ex.bindingResult.fieldErrors.map { error ->
			ValidationError(error.field, getLocalizedMessage(error, locale))
		}

		val apiError = ApiError(
			code = HttpStatus.BAD_REQUEST.value(),
			message = messageSource.getMessage("validation.error", null, locale),
			errors = errors
		)

		return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
	}

	private fun getLocalizedMessage(error: FieldError, locale: Locale): String {
		return if (error.defaultMessage?.startsWith("{") == true && error.defaultMessage?.endsWith("}") == true) {
			val messageKey = error.defaultMessage!!.substring(1, error.defaultMessage!!.length - 1)
			try {
				// 传递字段错误的参数
				val args = error.arguments?.let {
					it.sliceArray(1 until it.size) // 跳过第一个参数（通常是目标对象）
				} ?: emptyArray()
				messageSource.getMessage(messageKey, args, locale)
			} catch (e: Exception) {
				error.defaultMessage ?: "Validation error"
			}
		} else {
			error.defaultMessage ?: "Validation error"
		}
	}
}
