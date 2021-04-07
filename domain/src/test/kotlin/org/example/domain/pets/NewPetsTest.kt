package org.example.domain.pets

import arrow.core.Either
import arrow.core.Either.Right
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.example.domain.PetErrors
import org.example.domain.PetErrors.CannotSavePetInDB
import org.example.domain.pets.adapter.PetProducer
import org.example.domain.pets.adapter.PetRepository

class NewPetsTest : FunSpec({
  val petRepository = object : PetRepository {
    override fun create(pet: Pet): Either<PetErrors, Pet> = Right(pet)
  }
  val petProducer =  object : PetProducer {
    override fun newPetAdded(pet: Pet): Either<PetErrors.PetProducerError, Pet> = Right(pet)
  }
  val newPets = NewPets(PetEnvironment(petRepository, petProducer))
  val petToCreate = Pet(name = "name", category = "Shiba Inu", bio = "Biography", id = null)

  test("New pet is created with an ID") {
    val created = newPets.create(petToCreate)

    assertSoftly {
      created.shouldBeRight()
      val newPet = (created.value as Pet)
      newPet.id shouldNotBe null
      newPet.name shouldBe petToCreate.name
    }
  }

  test("NewPets should not created is there is some errors in adapter") {
    val failNewPets = NewPets(PetEnvironment(object : PetRepository {
      override fun create(pet: Pet): Either<PetErrors, Pet> = Either.Left(CannotSavePetInDB)
    }, petProducer))
    val created = failNewPets.create(petToCreate)

    created.shouldBeLeft(CannotSavePetInDB)
  }
})
