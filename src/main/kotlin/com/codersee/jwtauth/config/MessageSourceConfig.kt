package com.codersee.jwtauth.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

/**
 *
 * @author 杨彭伟
 * @date 2025-11-23
 */
@Configuration
class MessageSourceConfig {

	@Bean
	fun messageSource(): MessageSource {
		val messageSource = ReloadableResourceBundleMessageSource()
		messageSource.setBasename("classpath:messages")
		messageSource.setDefaultEncoding("UTF-8")
		messageSource.setUseCodeAsDefaultMessage(true)
		return messageSource
	}

	@Bean
	fun validator(messageSource: MessageSource): LocalValidatorFactoryBean {
		val validator = LocalValidatorFactoryBean()
		validator.setValidationMessageSource(messageSource)
		return validator
	}
}
