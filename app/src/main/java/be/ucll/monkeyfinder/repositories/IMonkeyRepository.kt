package be.ucll.monkeyfinder.repositories

import be.ucll.monkeyfinder.models.Monkey

interface IMonkeyRepository {
    suspend fun getMonkeys(): List<Monkey>
    suspend fun getMonkey(monkeyId: Int): Monkey
}