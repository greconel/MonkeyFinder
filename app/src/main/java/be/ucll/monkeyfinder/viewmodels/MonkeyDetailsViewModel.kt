package be.ucll.monkeyfinder.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.ucll.monkeyfinder.models.Monkey
import be.ucll.monkeyfinder.repositories.IMonkeyRepository
import kotlinx.coroutines.launch

class MonkeyDetailsViewModel(val monkeyRepository: IMonkeyRepository, bookId: Int) : ViewModel() {
    var monkey: Monkey? by mutableStateOf(null)

    init {
        getMonkey(bookId)
    }

    fun getMonkey(monkeyId: Int) {
        viewModelScope.launch {
            monkey = monkeyRepository.getMonkey(monkeyId)
        }
    }
}