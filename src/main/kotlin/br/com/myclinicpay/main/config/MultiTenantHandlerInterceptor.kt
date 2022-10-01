package br.com.myclinicpay.main.config

import br.com.myclinicpay.infra.db.mongoDb.Connection
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class MultiTenantHandlerInterceptor(val tenantHolder: TenantHolder) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val tenantId = request.getHeader("X-tenant-id")
        Connection.setTenantId(tenantId)
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        tenantHolder.clearTenant()
    }
}