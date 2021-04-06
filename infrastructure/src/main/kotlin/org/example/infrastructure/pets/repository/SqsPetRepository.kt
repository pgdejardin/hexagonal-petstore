package org.example.infrastructure.pets.repository

import arrow.core.Either
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import org.example.domain.SqsFailures
import org.example.infrastructure.pets.sqs.QueueOperations
import org.example.infrastructure.pets.sqs.SqsConfig

object SqsPetRepository {
  private val sqsConfig = ConfigFactory.load().extract<SqsConfig>("aws.sqs")

  fun createNewPetQueue(): Either<SqsFailures, String> = QueueOperations.createQueue(sqsConfig.newPetQueueName)
}
