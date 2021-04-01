package org.example.domain

interface Errors

sealed class PetErrors : Errors, Throwable() {
  object PetHasNoId: PetErrors()
  object CannotGenerateId: PetErrors()
}
