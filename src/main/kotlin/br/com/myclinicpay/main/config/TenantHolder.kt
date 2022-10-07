package br.com.myclinicpay.main.config

import org.springframework.stereotype.Component

@Component
class TenantHolder {
    companion object {
        const val DEFAULT_INSTANCE = "default"
    }

    private val tenantId = ThreadLocal<String>()

    fun setTenant(tenantId: String?) {
        if (tenantId.isNullOrBlank()) {
            this.tenantId.set(DEFAULT_INSTANCE)
        }
        this.tenantId.set(tenantId)
    }

    fun getTenant(): String? {
        if (this.tenantId.get() == null) {
            return DEFAULT_INSTANCE
        }
        return this.tenantId.get()
    }

    fun clearTenant() {
        this.tenantId.remove()
    }
}