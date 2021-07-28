package br.com.myclinicpay.infra.db.mongoDb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.MongoTemplate

class Connection {
    companion object MongoConnection {
        var connectionInstance : MongoClient? = null
        private fun connect() {
            val connectionString = ConnectionString("mongodb+srv://lgaricoix:010133@cluster0.42jwu.mongodb.net/my-clinic-pay-dev?retryWrites=true&w=majority")
            val mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build()
            connectionInstance = MongoClients.create(mongoClientSettings)
        }

        private fun getConnection(): MongoClient? {
            if (connectionInstance == null) {
                connect()
            }
            return connectionInstance
        }

        fun getTemplate(): MongoTemplate {
            return MongoTemplate(getConnection()!!, "my-clinic-pay-dev")
        }
    }
}
