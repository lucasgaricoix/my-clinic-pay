package br.com.myclinicpay.infra.mongoDb.repository.person

import br.com.myclinicpay.infra.mongoDb.entities.PersonEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PersonRepository : MongoRepository<PersonEntity, ObjectId> {
    fun findPersonById(id: ObjectId) : PersonEntity
}