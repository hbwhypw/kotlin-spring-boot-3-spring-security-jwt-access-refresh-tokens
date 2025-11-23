package com.codersee.jwtauth

import com.codersee.jwtauth.config.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
class JwtAuthApplication

fun main(args: Array<String>) {
	runApplication<JwtAuthApplication>(*args)
}
