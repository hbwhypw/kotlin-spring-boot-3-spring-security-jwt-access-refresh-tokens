package com.codersee.jwtauth.config.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 * 验证JWT过期时间范围是否在指定范围内
 * @author 杨彭伟
 * @date 2025-11-23
 */
class ExpirationRangeValidator : ConstraintValidator<ExpirationRange, Long> {

	private var min: Long = 300
	private var max: Long = 2592000

	override fun initialize(constraintAnnotation: ExpirationRange) {
		min = constraintAnnotation.min
		max = constraintAnnotation.max
	}

	override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
		if (value == null) return false

		if (value !in min..max) {
			// 禁用默认消息
			context.disableDefaultConstraintViolation()
			val customMessage = context.defaultConstraintMessageTemplate
				.replace("{min}", min.toString())
				.replace("{max}", max.toString())
			context.buildConstraintViolationWithTemplate(customMessage)
				.addConstraintViolation()
			return false
		}
		return true
	}
}
