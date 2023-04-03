package com.example.multigame

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.multigame.data.gameOptions
import com.example.multigame.ui.GameViewModel
import com.example.multigame.ui.NumberGuessingGameScreen
import com.example.multigame.ui.QuizGameScreen
import com.example.multigame.ui.StartGameScreen
import com.example.multigame.ui.TicTacToe.TicTacToeScreen
import kotlin.random.Random

enum class MultiGameScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    NumberGuessingGame(title = R.string.app_name_number),
    QuizGame(title = R.string.app_name_quize),
    TicTacToe(title = R.string.app_name_tictactoe)
}

@Composable
fun MultiGameAppBar(
    currentScreen: MultiGameScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title))},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}


@Composable
fun MultiGameApp(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MultiGameScreen.valueOf(
        backStackEntry?.destination?.route ?: MultiGameScreen.Start.name
    )

    Scaffold(
        topBar = {
            MultiGameAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ){  innerPadding ->
        val uiState by gameViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = MultiGameScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ){
            composable(route = MultiGameScreen.Start.name){
                StartGameScreen(
                    gameOptions = gameOptions,
                    onAppSelected = { selectedGame ->
                        when (selectedGame) {
                            "Number Guessing Game" -> navController.navigate(MultiGameScreen.NumberGuessingGame.name)
                            "Quiz Game" -> navController.navigate(MultiGameScreen.QuizGame.name)
                            "Tic Tac Toe" -> navController.navigate(MultiGameScreen.TicTacToe.name)
                        }
                    }
                )
            }
            composable(route = MultiGameScreen.NumberGuessingGame.name){
                NumberGuessingGameScreen(
                    randomNumber = Random.nextInt(1, 1000),
                    onCancelButtonClicked = {
                        cancelGameAndNavigateToStart(gameViewModel, navController)
                    }
                )
            }
            composable(route = MultiGameScreen.QuizGame.name){
                QuizGameScreen(
                    onCancelButtonClicked = {
                        cancelGameAndNavigateToStart(gameViewModel, navController)
                    },
                )
            }
            composable(route = MultiGameScreen.TicTacToe.name){
                TicTacToeScreen(
                    onCancelButtonClicked = {
                        cancelGameAndNavigateToStart(gameViewModel, navController)
                    }
                )
            }
        }
    }
}

private fun cancelGameAndNavigateToStart(
    gameViewModel: GameViewModel,
    navController: NavHostController
) {
    gameViewModel.resetGame()
    navController.popBackStack(MultiGameScreen.Start.name, inclusive = false)
}