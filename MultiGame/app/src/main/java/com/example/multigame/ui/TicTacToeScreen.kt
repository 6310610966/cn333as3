package com.example.multigame.ui.TicTacToe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.multigame.ui.*
import com.example.multigame.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TicTacToeScreen(
    gameViewModel: GameViewModel = viewModel(),
    onCancelButtonClicked: () -> Unit,
) {
    var state = gameViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackground)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Player 'O': ${state.playCircleCount}",
                fontSize = 16.sp,
                color = Black
            )
            Text(
                text = "Draw: ${state.drawCount}",
                fontSize = 16.sp,
                color = Black
            )
            Text(
                text = "Player 'X': ${state.playCrossCount}",
                fontSize = 16.sp,
                color = Black
            )
        }
        Text(
            text = "Tic Tac Toe",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Brown,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground),
            contentAlignment = Alignment.Center
        ) {
            BoardBase()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(3),
            ){
                gameViewModel.boardItems.forEach { (cellNo, boardCellValue) ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    gameViewModel.onAction(
                                        UserActions.BoardTapped(cellNo)
                                    )
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                visible = gameViewModel.boardItems[cellNo] != BoardCellValue.NONE,
//                                time to animate
                                enter = scaleIn(tween(1000))
                            ) {
                                if (boardCellValue == BoardCellValue.CIRCLE) {
                                    Circle()
                                } else if (boardCellValue == BoardCellValue.CROSS) {
                                    Cross()
                                }
                            }

                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = state.hasWon,
                    enter = fadeIn(tween(2000))
                ) {
                    DrawVictoryLine(state = state)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.hintText,
                fontSize = 20.sp,
                color = Black
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked,
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = DeepPink
                )
            ) {
                Text(
                    text = "Go Back",
                    fontSize = 16.sp
                )
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    gameViewModel.onAction(
                        UserActions.PlayAgainButtonClicked
                    )
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DeepPink,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun DrawVictoryLine(
    state: GameUiState
) {
    when (state.victoryType) {
        VictoryType.HORIZONTAL1 -> WinHorizontallLine1()
        VictoryType.HORIZONTAL2 -> WinHorizontallLine2()
        VictoryType.HORIZONTAL3 -> WinHorizontallLine3()
        VictoryType.VERTICAL1 -> WinVerticalLine1()
        VictoryType.VERTICAL2 -> WinVerticalLine2()
        VictoryType.VERTICAL3 -> WinVerticalLine3()
        VictoryType.DIAGONAL1 -> WinDiagonaLine1()
        VictoryType.DIAGONAL2 -> WinDiagonaLine2()
        VictoryType.NONE -> {}
    }
}


@Preview(showBackground = true)
@Composable
fun TicTacToePreview() {
    TicTacToeScreen(
        gameViewModel = GameViewModel(),
        onCancelButtonClicked = {}
    )
}