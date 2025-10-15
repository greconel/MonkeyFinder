package be.ucll.monkeyfinder.models

import kotlinx.serialization.Serializable

@Serializable
data class Monkey(
    val id: Int,
    val name: String,
    val location: String,
    val details: String,
    val image: String,
    val population: Int,
    val latitude: Double,
    val longitude: Double
)
