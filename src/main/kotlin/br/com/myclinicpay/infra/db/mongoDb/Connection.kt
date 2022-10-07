package br.com.myclinicpay.infra.db.mongoDb

import br.com.myclinicpay.main.config.TenantHolder
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import java.lang.StringBuilder

@Component
class Connection {
    companion object MongodbConnection {
        private const val DB_PREFIX = "my-clinic-pay"

        private var connectionInstance: MongoClient? = null
        var tenantHolder = TenantHolder()

        fun setConnection(connection: MongoClientSettings, tenantId: String?) {
            connectionInstance = MongoClients.create(connection)
            tenantHolder.setTenant(tenantId)
        }

        fun setTenantId(tenantId: String?) {
            tenantHolder.setTenant(tenantId)
        }

        fun getTemplate(): MongoTemplate {
            var instanceString = StringBuilder(DB_PREFIX)
            if (tenantHolder.getTenant() != null) {
                instanceString = instanceString.append("-").append(tenantHolder.getTenant().toString())
            }
            // instanceString.toString()
            return MongoTemplate(connectionInstance!!, "my-clinic-pay")
        }

    }
}
