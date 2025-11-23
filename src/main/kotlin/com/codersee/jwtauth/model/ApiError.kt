package com.codersee.jwtauth.model

/**
 *
 * @author 杨彭伟
 * @date 2025-11-23
 */
data class ApiError(
	val code: Int,
	val message: String,
	val errors: List<ValidationError> = emptyList(),
	val timestamp: Long = System.currentTimeMillis()
)
