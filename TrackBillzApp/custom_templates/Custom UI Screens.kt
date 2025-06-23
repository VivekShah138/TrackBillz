#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "") package ${PACKAGE_NAME} #end

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ${NAME}Root(
    viewModel: ${NAME}ViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    ${NAME}Screen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ${NAME}Screen(
    state: ${NAME}States,
    onEvent: (${NAME}Events) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    ${PROJECT_NAME}Theme {
        ${NAME}Screen(
            state = ${NAME}States(),
            onEvent = {}
        )
    }
}