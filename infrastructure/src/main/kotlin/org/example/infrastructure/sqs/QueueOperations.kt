package org.example.infrastructure.sqs

import arrow.core.Either
import arrow.core.flatMap
import org.example.domain.SqsFailures
import org.example.domain.SqsFailures.*
import org.example.infrastructure.sqs.Client.sqsClient
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageResponse

object QueueOperations {
  fun createQueue(name: String): Either<SqsFailures, String> {
    val createQueueRequest = CreateQueueRequest.builder().queueName(name).build()

    return Either.catch { sqsClient.createQueue(createQueueRequest) }
      .mapLeft { CannotCreateSqsQueue(it) }
      .flatMap { Either.catch { sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(name).build()) }.mapLeft { CannotGetQueueUrl(it) } }
      .map { it.queueUrl() }
  }

  fun getQueueUrlFromName(name: String): Either<SqsFailures, String> {
    val queueUrlRequest = GetQueueUrlRequest.builder().queueName(name).build()
    return Either.catch { sqsClient.getQueueUrl(queueUrlRequest) }.mapLeft { CannotGetQueueUrl(it) }
      .map { it.queueUrl() }
  }

  fun sendMessage(queueUrl: String, message: String): Either<SqsFailures, SendMessageResponse> = Either.catch {
    sqsClient.sendMessage(
      SendMessageRequest.builder()
        .queueUrl(queueUrl)
        .messageBody(message)
        .delaySeconds(10)
        .build()
    )
  }.mapLeft { CannotSendMessageInQueue(it) }
}
