package org.example.infrastructure.pets.sqs

import arrow.core.Either
import arrow.core.flatMap
import org.example.domain.SqsFailures
import org.example.domain.SqsFailures.CannotCreateSqsQueue
import org.example.domain.SqsFailures.CannotGetQueueUrl
import org.example.infrastructure.pets.sqs.Client.sqsClient
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest


object QueueOperations {
  fun createQueue(name: String): Either<SqsFailures, String> {
    val createQueueRequest = CreateQueueRequest.builder().queueName(name).build()

    return Either.catch { sqsClient.createQueue(createQueueRequest) }
      .mapLeft { CannotCreateSqsQueue(it) }
      .flatMap { Either.catch { sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(name).build()) }.mapLeft { CannotGetQueueUrl(it) } }
      .map { it.queueUrl() }
  }
}
