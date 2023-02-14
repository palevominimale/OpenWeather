package app.seals.weather.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import app.seals.weather.UiIntent
import app.seals.weather.UiState
import app.seals.weather.ui.screens.LocationDialog
import app.seals.weather.ui.screens.MainScreen
import app.seals.weather.ui.screens.TopBar
import app.seals.weather.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by inject()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState = vm.uiState.collectAsState()
            val scaffoldState = rememberBottomSheetScaffoldState()
            val scope = rememberCoroutineScope()
            val kbController = LocalSoftwareKeyboardController.current

            MyApplicationTheme {
                BottomSheetScaffold(
                    drawerGesturesEnabled = true,
                    sheetGesturesEnabled = true,
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = 12.dp,
                    sheetContent = {
                        LocationDialog(
                            autocomplete = if(uiState.value is UiState.Ready) (uiState.value as UiState.Ready).autocomplete else emptyList(),
                            locations = if(uiState.value is UiState.Ready) (uiState.value as UiState.Ready).locations else emptyList(),
                            reducer = vm::process
                        )
                    },
                    topBar = {
                        if(uiState.value is UiState.Ready) TopBar(
                            location = (uiState.value as UiState.Ready).weather.location,
                            locations = (uiState.value as UiState.Ready).locations,
                            openSearch = {
                                scope.launch {
                                    if(scaffoldState.bottomSheetState.isCollapsed) scaffoldState.bottomSheetState.expand()
                                    else {
                                        kbController?.hide()
                                        scaffoldState.bottomSheetState.collapse()
                                    }
                                }
                            },
                            selectForecast = { vm.process(UiIntent.SetLocation(it)) }
                        )
                    }
                ) {
                    when(uiState.value) {
                        is UiState.Ready -> {
                            MainScreen(
                                modifier = Modifier.padding(it),
                                weather = (uiState.value as UiState.Ready).weather,
                                reducer = vm::process,
                                isRefreshing = (uiState.value as UiState.Ready).isRefreshing,
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}