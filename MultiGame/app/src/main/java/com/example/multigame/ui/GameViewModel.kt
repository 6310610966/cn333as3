package com.example.multigame.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.multigame.data.MAX_NO_OF_QUESTIONS
import com.example.multigame.data.SCORE_INCREASE
import com.example.multigame.data.allQuestions
import com.example.multigame.data.gameOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    //QuizeGame
    var userGuess by mutableStateOf("")
        private set

    // question used in the game
    private var usedQuestions: MutableSet<String> = mutableSetOf()

    // question and answer in the turn
    private lateinit var currentQuestion: String
    private lateinit var currentAnswer: String

    fun resetAllGame() {
        _uiState.value = GameUiState()
    }

    init {
        resetGame()
    }

    fun resetGame() {
        usedQuestions.clear()

        val data = pickRandomQuestionAndShuffle()

        _uiState.value = GameUiState(currentQuestion = data[0], currentAnswer = data[1])
    }

    fun checkUserGuess(selectedItem: String? = null) {
        if (selectedItem == currentAnswer) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)

            updateGameState(updatedScore)
        } else {
            skipQuestion()
        }
    }

    fun skipQuestion() {
        updateGameState(_uiState.value.score)
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedQuestions.size == MAX_NO_OF_QUESTIONS){
            // last turn
            _uiState.update { currentState ->
                currentState.copy(
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            val data = pickRandomQuestionAndShuffle()

            _uiState.update { currentState ->
                currentState.copy(
                    currentQuestion = data[0],
                    currentAnswer = data[1],
                    currentQuestionCount = currentState.currentQuestionCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    private fun pickRandomQuestionAndShuffle(): List<String> {
        // random unique question
        val randomQuestion = allQuestions.random()
        currentQuestion = randomQuestion[0]
        currentAnswer = randomQuestion[1]

        if (usedQuestions.contains(currentQuestion)) {
            return pickRandomQuestionAndShuffle()
        } else {
            usedQuestions.add(currentQuestion)

            return listOf(currentQuestion, currentAnswer)
        }
    }

    //TicTacToe
    var state by mutableStateOf(GameUiState())

    val boardItems: MutableMap<Int, BoardCellValue> = mutableMapOf(
        1 to BoardCellValue.NONE,
        2 to BoardCellValue.NONE,
        3 to BoardCellValue.NONE,
        4 to BoardCellValue.NONE,
        5 to BoardCellValue.NONE,
        6 to BoardCellValue.NONE,
        7 to BoardCellValue.NONE,
        8 to BoardCellValue.NONE,
        9 to BoardCellValue.NONE,
    )

    fun onAction(action: UserActions) {
        when (action) {
            is UserActions.BoardTapped -> {
                addValueToBoard(action.cellNo)
            }
            UserActions.PlayAgainButtonClicked -> {
                gameReset()
            }
        }
    }

    private fun gameReset() {
        boardItems.forEach{ (i, _) ->
            boardItems[i] = BoardCellValue.NONE
        }
        state = state.copy(
            hintText = "Player 'O' turn",
            currentTurn = BoardCellValue.CIRCLE,
            victoryType = VictoryType.NONE,
            hasWon = false
        )
    }

    private fun addValueToBoard(cellNo: Int) {
        if (boardItems[cellNo] != BoardCellValue.NONE) {
            return
        }
        if (state.currentTurn == BoardCellValue.CIRCLE) {
            boardItems[cellNo] = BoardCellValue.CIRCLE
            if (checkForVictory(BoardCellValue.CIRCLE)){
                state = state.copy(
                    hintText = "Player 'O' Won!",
                    playCircleCount = state.playCircleCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            } else if (hasBoardFull()){
                state = state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            }
            else {
                state = state.copy(
                    hintText = "Play 'X' turn",
                    currentTurn = BoardCellValue.CROSS
                )
            }
        } else if (state.currentTurn == BoardCellValue.CROSS) {
            boardItems[cellNo] = BoardCellValue.CROSS
            if (checkForVictory(BoardCellValue.CROSS)){
                state = state.copy(
                    hintText = "Player 'X' Won!",
                    playCrossCount = state.playCrossCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            }else if (hasBoardFull()){
                state = state.copy(
                    hintText = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            } else {
                state = state.copy(
                    hintText = "Play 'O' turn",
                    currentTurn = BoardCellValue.CIRCLE
                )
            }
        }
    }
    private fun checkForVictory(boardValue: BoardCellValue): Boolean {
        when {
            boardItems[1] == boardValue && boardItems[2] == boardValue && boardItems[3] == boardValue -> {
                state = state.copy(victoryType = VictoryType.HORIZONTAL1)
                return true
            }
            boardItems[4] == boardValue && boardItems[5] == boardValue && boardItems[6] == boardValue -> {
                state = state.copy(victoryType = VictoryType.HORIZONTAL2)
                return true
            }
            boardItems[7] == boardValue && boardItems[8] == boardValue && boardItems[9] == boardValue -> {
                state = state.copy(victoryType = VictoryType.HORIZONTAL3)
                return true
            }
            boardItems[1] == boardValue && boardItems[2] == boardValue && boardItems[3] == boardValue -> {
                state = state.copy(victoryType = VictoryType.VERTICAL1)
                return true
            }
            boardItems[4] == boardValue && boardItems[5] == boardValue && boardItems[6] == boardValue -> {
                state = state.copy(victoryType = VictoryType.VERTICAL2)
                return true
            }
            boardItems[7] == boardValue && boardItems[8] == boardValue && boardItems[9] == boardValue -> {
                state = state.copy(victoryType = VictoryType.VERTICAL3)
                return true
            }
            boardItems[1] == boardValue && boardItems[5] == boardValue && boardItems[9] == boardValue -> {
                state = state.copy(victoryType = VictoryType.DIAGONAL1)
                return true
            }
            boardItems[3] == boardValue && boardItems[5] == boardValue && boardItems[7] == boardValue -> {
                state = state.copy(victoryType = VictoryType.DIAGONAL2)
                return true
            }
            else -> return false
        }
    }
    private fun hasBoardFull(): Boolean {
        if (boardItems.containsValue(BoardCellValue.NONE)) return false
        return true
    }
}


