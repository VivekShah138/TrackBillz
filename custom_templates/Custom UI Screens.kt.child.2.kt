#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME} #end

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ${NAME}ViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(${NAME}States())
    val state: StateFlow<${NAME}States> = _state.asStateFlow()
    
    init{
    
    }
    
    fun onEvent(events: ${NAME}Events) {
        when(events){
            else -> TODO("Handle actions")
        }
     }
}