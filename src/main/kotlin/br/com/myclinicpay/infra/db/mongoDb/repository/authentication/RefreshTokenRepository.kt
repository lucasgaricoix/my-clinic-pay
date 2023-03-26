package br.com.myclinicpay.infra.db.mongoDb.repository.authentication

import br.com.myclinicpay.data.usecases.authentication.RefreshTokenRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.RefreshTokenEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class RefreshTokenRepository : RefreshTokenRepository {

    private val collectionName = "refresh_token"

    override fun findByToken(token: String): RefreshTokenEntity? {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("token").isEqualTo(token))
        return mongodbTemplate.findOne(query, collectionName)
    }

    override fun findByUsername(username: String): RefreshTokenEntity? {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("user.email").isEqualTo(username))
        return mongodbTemplate.findOne(query, collectionName)
    }

    override fun create(user: UserEntity, token: String, expirationDate: LocalDateTime): RefreshTokenEntity {
        val mongodbTemplate = Connection.getTemplate()
        val entity = RefreshTokenEntity(
            ObjectId.get(),
            user,
            token,
            expirationDate
        )
        return mongodbTemplate.save(entity, collectionName)
    }

    override fun deleteById(id: String): Boolean {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("_id").isEqualTo(id))

        val deleted = mongodbTemplate.remove(query, collectionName)

        if (deleted.deletedCount > 0) {
            return true
        }

        return false
    }
}
