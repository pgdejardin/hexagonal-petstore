package org.example.domain

sealed class Errors : Throwable()

sealed class PetErrors : Errors() {
  object CannotGenerateId : PetErrors()
  object CannotSavePetInDB : PetErrors()
  object CannotSerializePet : PetErrors()
  object PetHasNoId : PetErrors()
  class PetProducerError(private val throwable: Throwable) : PetErrors() {
    override fun toString(): String {
      return throwable.toString()
    }
  }
}

sealed class SqsFailures : Errors() {
  class CannotCreateSqsQueue(private val throwable: Throwable) : SqsFailures() {
    override fun toString(): String {
      return throwable.toString()
    }
  }

  class CannotGetQueueUrl(private val throwable: Throwable) : SqsFailures() {
    override fun toString(): String {
      return throwable.toString()
    }
  }

  class CannotSendMessageInQueue(private val throwable: Throwable) : SqsFailures() {
    override fun toString(): String {
      return throwable.toString()
    }
  }
}

sealed class TechnicalFailures: Errors() {
  object SerializationFailure: TechnicalFailures()
}
