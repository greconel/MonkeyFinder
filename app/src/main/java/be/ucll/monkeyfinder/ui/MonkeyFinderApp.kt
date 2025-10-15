package be.ucll.monkeyfinder.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import be.ucll.monkeyfinder.R
import be.ucll.monkeyfinder.models.Monkey
import be.ucll.monkeyfinder.repositories.IMonkeyRepository
import be.ucll.monkeyfinder.repositories.MonkeyRepository
import be.ucll.monkeyfinder.ui.screens.DetailsScreen
import be.ucll.monkeyfinder.ui.screens.HomeScreen
import be.ucll.monkeyfinder.viewmodels.MonkeyDetailsViewModel
import be.ucll.monkeyfinder.viewmodels.MonkeysViewModel
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Details(val monkeyId: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonkeyFinderApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route?.substringBefore('/') ?: Home::class.qualifiedName
    val title = when (currentScreen) {
        Home::class.qualifiedName -> stringResource(id = R.string.app_name)
        Details::class.qualifiedName -> stringResource(id = R.string.details)
        else -> ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<Home> {
                val monkeyRepository: IMonkeyRepository = MonkeyRepository()
                val viewModel = viewModel<MonkeysViewModel> {
                    MonkeysViewModel(monkeyRepository)
                }

                val onListItemClick = { monkey: Monkey ->
                    navController.navigate(Details(monkey.id))
                }

                HomeScreen(
                    viewModel = viewModel,
                    onListItemClick = onListItemClick,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<Details> { backStackEntry ->
                val args: Details = backStackEntry.toRoute<Details>()

                val monkeyRepository: IMonkeyRepository = MonkeyRepository()
                val viewModel = viewModel<MonkeyDetailsViewModel> {
                    MonkeyDetailsViewModel(monkeyRepository,args.monkeyId)
                }

                DetailsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}