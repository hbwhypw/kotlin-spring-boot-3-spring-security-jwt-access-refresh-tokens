package com.codersee.jwtauth.config.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


/**
 * 验证JWT过期时间范围是否在指定范围内
 * @author 杨彭伟
 * @date 2025-11-23
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExpirationRangeValidator::class])
annotation class ExpirationRange(
	val min: Long = 300,
	val max: Long = 2592000,
	val message: String = "{validation.jwt.expiration.range}",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
