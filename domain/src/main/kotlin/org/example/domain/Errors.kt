package org.example.domain

interface Errors

sealed class PetErrors : Errors, Throwable() {
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

sealed class SqsFailures : Errors, Throwable() {
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

sealed class TechnicalFailures: Errors, Throwable() {
  object SerializationFailure: TechnicalFailures()
}
