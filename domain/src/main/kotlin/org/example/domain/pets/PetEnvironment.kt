package org.example.domain.pets

import org.example.domain.pets.adapter.PetRepository

data class PetEnvironment(val petRepository: PetRepository)
