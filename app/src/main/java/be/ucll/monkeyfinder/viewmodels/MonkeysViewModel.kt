package be.ucll.monkeyfinder.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.ucll.monkeyfinder.models.Monkey
import be.ucll.monkeyfinder.repositories.IMonkeyRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class MonkeyUiState {
    data class Success(val monkeys: List<Monkey>) : MonkeyUiState()
    data object Error : MonkeyUiState()
    data object Loading : MonkeyUiState()
}

class MonkeysViewModel(val monkeyRepository: IMonkeyRepository): ViewModel() {
    var monkeyUiState: MonkeyUiState by mutableStateOf(MonkeyUiState.Loading)

    init {
        getMonkeys()
    }

    fun getMonkeys() {
        viewModelScope.launch {
            monkeyUiState = MonkeyUiState.Loading
            monkeyUiState = try {
                MonkeyUiState.Success(monkeyRepository.getMonkeys())
            } catch (e: IOException) {
                MonkeyUiState.Error
            } catch (e: HttpException) {
                MonkeyUiState.Error
            }
        }
    }
}