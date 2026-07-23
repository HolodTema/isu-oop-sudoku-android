import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VictoryViewModel @Inject constructor(

) : ViewModel() {
    private val _state: MutableStateFlow<VictoryState> = run {

    }
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<VictoryEffect>()
    val effect = _effect.asSharedFlow()

    fun handleIntent(intent: VictoryIntent) {
        when (intent) {
            VictoryIntent.OnBackToMainMenuIntent -> {
                viewModelScope.launch {
                    _effect.emit(VictoryEffect.OnNavigateToMainMenu)
                }
            }
        }
    }
}