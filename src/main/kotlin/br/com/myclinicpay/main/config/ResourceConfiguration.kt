package br.com.myclinicpay.main.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ResourceConfiguration(
    private val multiTenantHandlerInterceptor: MultiTenantHandlerInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(multiTenantHandlerInterceptor)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "https://myclinicpay.vercel.app", "https://myclinicpay.vercel.app/")
            .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
            .allowedHeaders("*")
    }
}
