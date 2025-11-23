package com.codersee.jwtauth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*


/**
 * 配置国际化，支持英文、中文、韩文，默认英文
 * @author 杨彭伟
 * @date 2025-11-23
 */
@Configuration
class LocaleConfig {
	@Bean
	fun localeResolver(): LocaleResolver {
		val resolver = AcceptHeaderLocaleResolver()
		resolver.setDefaultLocale(Locale.ENGLISH)
		resolver.supportedLocales = listOf(Locale.ENGLISH, Locale.CHINA, Locale.KOREA)
		return resolver
	}
}
