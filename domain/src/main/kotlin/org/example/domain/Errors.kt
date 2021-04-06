package org.example.domain

interface Errors

sealed class PetErrors : Errors, Throwable() {
  object CannotGenerateId : PetErrors()
  object CannotSavePetInDB : PetErrors()
  object PetHasNoId : PetErrors()
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
}
