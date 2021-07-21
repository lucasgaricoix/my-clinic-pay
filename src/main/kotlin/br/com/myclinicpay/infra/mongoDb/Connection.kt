package br.com.myclinicpay.infra.mongoDb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.MongoTemplate

class Connection {
    companion object MongoConnection {
        var connectionInstance : MongoClient? = null
        private fun connect() {
            val connectionString = ConnectionString("mongodb://lgaricoix:010133@localhost:27017/myclinicpay")
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
            return MongoTemplate(getConnection()!!, "myclinicpay")
        }
    }
}
