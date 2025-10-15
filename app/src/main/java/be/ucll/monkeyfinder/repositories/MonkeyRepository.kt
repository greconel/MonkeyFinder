package be.ucll.monkeyfinder.repositories

import be.ucll.monkeyfinder.models.Monkey
import be.ucll.monkeyfinder.services.MonkeyApi

class MonkeyRepository : IMonkeyRepository {
    override suspend fun getMonkeys(): List<Monkey> {
        return MonkeyApi.retrofitService.getMonkeys()
    }

    override suspend fun getMonkey(monkeyId: Int): Monkey {
        return MonkeyApi.retrofitService.getMonkey(monkeyId)
    }
}