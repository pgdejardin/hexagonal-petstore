package org.example.domain.pets

import java.util.UUID

data class Pet(
  val name: String,
  val category: String,
  val bio: String,
  val status: PetStatus = PetStatus.Available,
  val tags: Set<String> = emptySet(),
  val photoUrls: Set<String> = emptySet(),
  val id: UUID?,
)

sealed class PetStatus {
  object Available : PetStatus()
  object Pending : PetStatus()
  object Adopted : PetStatus()
}
