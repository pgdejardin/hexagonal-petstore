package org.example.infrastructure.pets.api

import org.example.domain.pets.Pet
import org.example.domain.pets.PetStatus

data class JsonPet(
  val id: String,
  val name: String,
  val category: String,
  val bio: String,
  val status: PetStatus?,
  val tags: Set<String> = emptySet(),
  val photoUrls: Set<String> = emptySet(),
)

data class CreatePet(
  val name: String,
  val category: String,
  val bio: String,
  val status: PetStatus?,
  val tags: Set<String> = emptySet(),
  val photoUrls: Set<String> = emptySet(),
)

fun CreatePet.toPet() = Pet(
  name = name,
  category = category,
  bio = bio,
  status = status ?: PetStatus.Available,
  tags = tags,
  photoUrls = photoUrls,
  id = null
)


fun Pet.toJsonPet() = JsonPet(
  id = id?.toString()!!,
  name = name,
  category = category,
  bio = bio,
  status = status,
  tags = tags,
  photoUrls = photoUrls,
)
