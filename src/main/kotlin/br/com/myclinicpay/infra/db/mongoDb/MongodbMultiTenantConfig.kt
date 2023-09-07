package br.com.myclinicpay.infra.db.mongoDb

import br.com.myclinicpay.main.config.TenantHolder
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter

@Configuration
class MongodbMultiTenantConfig : AbstractMongoClientConfiguration() {
    @Autowired
    private lateinit var tenantHolder: TenantHolder

    @Value("\${spring.data.mongodb.uri}")
    private lateinit var connectionString: String

    override fun getDatabaseName(): String {
        return tenantHolder.getTenant().toString()
    }

    @Primary
    @Bean
    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(connectionString)
        val mongodbClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()

        Connection.setConnection(mongodbClientSettings, tenantHolder.getTenant().toString())
        return MongoClients.create(mongodbClientSettings)
    }

    @Primary
    @Bean
    override fun mongoTemplate(databaseFactory: MongoDatabaseFactory, converter: MappingMongoConverter): MongoTemplate {
        return MongoTemplate(databaseFactory, converter)
    }
}
