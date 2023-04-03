package com.example.multigame.ui


import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.multigame.data.allAnswers
import com.example.multigame.ui.theme.MultiGameTheme
import com.example.multigame.ui.theme.Purple700
import com.example.multigame.ui.theme.Teal400

@Composable
fun QuizeGameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    onCancelButtonClicked: () -> Unit,
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameStatus(
            questionCount = gameUiState.currentQuestionCount,
            score = gameUiState.score
        )

        GameLayout(
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            currentQuestion = gameUiState.currentQuestion as String,
            currentAnswer = gameUiState.currentAnswer,
            gameViewModel = gameViewModel
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked,
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Teal400
                )
            ) {
                Text(
                    text = "Go Back",
                    fontSize = 16.sp
                )
            }
            OutlinedButton(
                onClick = { gameViewModel.skipQuestion() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Teal400,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Skip",
                    fontSize = 16.sp
                )
            }
        }
        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}

@Composable
fun GameStatus(questionCount: Int, score: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = "$questionCount / 10",
            color = Purple700,
            fontFamily = FontFamily.Monospace,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = "Score: $score",
            fontFamily = FontFamily.Monospace,
            color = Purple700,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun GameLayout(
    currentQuestion: String,
    currentAnswer:String,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = currentQuestion,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Which one is the best choice?",
            fontFamily = FontFamily.Monospace,
            fontSize = 17.sp,
            color = Purple700,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        val radioOptions = ArrayList<String>()
        radioOptions.add(currentAnswer)
        while (radioOptions.size != 4) {
            val answer = allAnswers.random()
            if (! radioOptions.contains(answer)) {
                radioOptions.add(answer)
            }
        }
        radioOptions.shuffle()
        var selectedItem by remember { mutableStateOf(null) }

        Column(modifier = Modifier.selectableGroup()) {

            radioOptions.forEach { label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (selectedItem == label),
                            onClick = {
                                gameViewModel.checkUserGuess(label)
                            },
                            role = Role.RadioButton,
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier.padding(end = 16.dp),
                        selected = (selectedItem == label),
                        onClick = {
                            gameViewModel.checkUserGuess(label) },
                    )
                    Text(
                        text = label,
                        fontFamily = FontFamily.Monospace,
                        color = Purple700,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Congratulations!",
            color = Color.White) },
        text = { Text(text = "You got $score out of 10",
            color = Color.White) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = "Exit",
                    color = Color.White
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = "Restart",
                    color = Color.White
                )
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFA9E2E1)
@Composable
fun QuizeGamePreview() {
    MultiGameTheme {
        QuizeGameScreen(
            gameViewModel = GameViewModel(),
            onCancelButtonClicked = {}
        )
    }
}
