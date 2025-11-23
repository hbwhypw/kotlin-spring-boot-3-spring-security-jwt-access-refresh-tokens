package com.codersee.jwtauth.config

import com.codersee.jwtauth.config.validator.ExpirationRange
import jakarta.validation.constraints.Size
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

/**
 * jwt配置属性
 * @author 杨彭伟
 * @date 2025-11-23
 */
@Validated
@ConfigurationProperties("jwt")
data class JwtProperties(
	// jwt密钥，长度必须大于32位
	@field:Size(min = 33, message = "{validation.jwt.key.size}")
  val key: String,

	// 访问令牌过期时间，单位毫秒，默认1小时，最小300秒，最大1天
	@field:ExpirationRange(min = 300, max = 86400000, message = "访问令牌{validation.jwt.expiration.range}")
	val accessTokenExpiration: Long = 3600000,

	// 刷新令牌过期时间，单位毫秒，默认1小时，最小300秒，最大1天
	@field:ExpirationRange(min = 300, max = 86400000, message = "刷新令牌{validation.jwt.expiration.range}")
	val refreshTokenExpiration: Long = 3600000,
)
