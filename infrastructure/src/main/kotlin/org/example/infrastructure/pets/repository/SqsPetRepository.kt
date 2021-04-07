package org.example.infrastructure.pets.repository

import arrow.core.Either
import arrow.core.flatMap
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import org.example.domain.PetErrors.CannotSerializePet
import org.example.domain.PetErrors.PetProducerError
import org.example.domain.SqsFailures
import org.example.domain.pets.Pet
import org.example.domain.pets.adapter.PetProducer
import org.example.infrastructure.serialization.JsonMapper.mapper
import org.example.infrastructure.serialization.unmarshall
import org.example.infrastructure.sqs.QueueOperations
import org.example.infrastructure.sqs.QueueOperations.createQueue
import org.example.infrastructure.sqs.QueueOperations.getQueueUrlFromName
import org.example.infrastructure.sqs.SqsConfig

object SqsPetRepository : PetProducer {
  private val sqsConfig = ConfigFactory.load().extract<SqsConfig>("aws.sqs")

  fun createNewPetQueue(): Either<SqsFailures, String> = createQueue(sqsConfig.newPetQueueName)

  override fun newPetAdded(pet: Pet): Either<PetProducerError, Pet> {
    return mapper.unmarshall(pet)
      .mapLeft { CannotSerializePet }
      .flatMap { message ->
        getQueueUrlFromName(sqsConfig.newPetQueueName)
          .flatMap { QueueOperations.sendMessage(it, message) }
      }
      .mapLeft { PetProducerError(it) }
      .map { pet }
  }
}
