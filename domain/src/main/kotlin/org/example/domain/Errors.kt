package org.example.domain

interface Errors

sealed class PetErrors : Errors, Throwable() {
  object CannotGenerateId: PetErrors()
  object CannotSavePetInDB: PetErrors()
  object PetHasNoId: PetErrors()
}
