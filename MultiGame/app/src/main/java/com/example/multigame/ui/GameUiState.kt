package com.example.multigame.ui

data class GameUiState(

    //QuizeGame
    val currentQuestion: String = "",
    val currentAnswer: String = "",
    val currentQuestionCount: Int = 1,
    val score: Int = 0,
    val isGameOver: Boolean = false,

    //TicTacToe
    val playCircleCount: Int = 0,
    val playCrossCount: Int = 0,
    val drawCount: Int = 0,
    val hintText: String = "Play 'O' turn",
    val currentTurn: BoardCellValue = BoardCellValue.CIRCLE,
    val victoryType: VictoryType = VictoryType.NONE,
    val hasWon: Boolean = false
)

enum class BoardCellValue {
    CIRCLE,
    CROSS,
    NONE
}

enum class VictoryType {
    HORIZONTAL1,
    HORIZONTAL2,
    HORIZONTAL3,
    VERTICAL1,
    VERTICAL2,
    VERTICAL3,
    DIAGONAL1,
    DIAGONAL2,
    NONE
}
