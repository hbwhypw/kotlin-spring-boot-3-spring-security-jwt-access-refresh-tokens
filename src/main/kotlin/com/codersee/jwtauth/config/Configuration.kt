package com.codersee.jwtauth.config


import com.codersee.jwtauth.repository.UserRepository
import com.codersee.jwtauth.service.CustomUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

  @Bean
  fun userDetailsService(userRepository: UserRepository): UserDetailsService =
    CustomUserDetailsService(userRepository)

	/**
	 * 配置认证提供程序，使用自定义用户详情服务和密码编码器
	 * @param userRepository 用户仓库，用于加载用户详情
	 * @return 认证提供程序
	 */
  @Bean
  fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider =
    DaoAuthenticationProvider()
      .also {
        it.setUserDetailsService(userDetailsService(userRepository))
        it.setPasswordEncoder(encoder())
      }

	/**
	 * 配置认证管理器，用于处理认证请求
	 * @param config 认证配置，包含认证提供程序
	 * @return 认证管理器
	 */
  @Bean
  fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
    config.authenticationManager

	/**
	 * 配置密码编码器，使用BCrypt算法
	 * @return 密码编码器
	 */
  @Bean
  fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}
