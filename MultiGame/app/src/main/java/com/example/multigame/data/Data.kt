package com.example.multigame.data

const val MAX_NO_OF_QUESTIONS = 10
const val SCORE_INCREASE = 1


val allQuestions: Set<List<String>> =
    setOf(
        listOf("1 + 1 = ?", "2"),
        listOf("1 + 2 = ?", "3"),
        listOf("1 + 3 = ?", "4"),
        listOf("1 + 4 = ?", "5"),
        listOf("1 + 5 = ?", "6"),
        listOf("1 + 6 = ?", "7"),
        listOf("1 + 7 = ?", "8"),
        listOf("1 + 8 = ?", "9"),
        listOf("1 + 9 = ?", "10"),
        listOf("1 + 10 = ?", "11"),
        listOf("2 + 1 = ?", "3"),
        listOf("2 + 2 = ?", "4"),
        listOf("2 + 3 = ?", "5"),
        listOf("2 + 4 = ?", "6"),
        listOf("2 + 5 = ?", "7"),
        listOf("2 + 6 = ?", "8"),
        listOf("2 + 7 = ?", "9"),
        listOf("2 + 8 = ?", "10"),
        listOf("2 + 9 = ?", "11"),
        listOf("2 + 10 = ?", "12"),
        listOf("3 + 1 = ?", "4"),
        listOf("3 + 2 = ?", "5"),
        listOf("3 + 3 = ?", "6"),
        listOf("3 + 4 = ?", "7"),
        listOf("3 + 5 = ?", "8"),
        listOf("3 + 6 = ?", "9"),
        listOf("3 + 7 = ?", "10"),
        listOf("3 + 8 = ?", "11"),
        listOf("3 + 9 = ?", "12"),
        listOf("3 + 10 = ?", "13"),
        listOf("4 + 1 = ?", "5"),
        listOf("4 + 2 = ?", "6"),
        listOf("4 + 3 = ?", "7"),
        listOf("4 + 4 = ?", "8"),
        listOf("4 + 5 = ?", "9"),
        listOf("4 + 6 = ?", "10"),
        listOf("4 + 7 = ?", "11"),
        listOf("4 + 8 = ?", "12"),
        listOf("4 + 9 = ?", "13"),
        listOf("4 + 10 = ?", "14"),
        listOf("5 + 1 = ?", "6"),
        listOf("5 + 2 = ?", "7"),
        listOf("5 + 3 = ?", "8"),
        listOf("5 + 4 = ?", "9"),
        listOf("5 + 5 = ?", "10"),
        listOf("5 + 6 = ?", "11"),
        listOf("5 + 7 = ?", "12"),
        listOf("5 + 8 = ?", "13"),
        listOf("5 + 9 = ?", "14"),
        listOf("5 + 10 = ?", "15"),
        listOf("6 + 1 = ?", "7"),
        listOf("6 + 2 = ?", "8"),
        listOf("6 + 3 = ?", "9"),
        listOf("6 + 4 = ?", "10"),
        listOf("6 + 5 = ?", "11"),
        listOf("6 + 6 = ?", "12"),
        listOf("6 + 7 = ?", "13"),
        listOf("6 + 8 = ?", "14"),
        listOf("6 + 9 = ?", "15"),
        listOf("6 + 10 = ?", "16"),
        listOf("7 + 1 = ?", "8"),
        listOf("7 + 2 = ?", "9"),
        listOf("7 + 3 = ?", "10"),
        listOf("7 + 4 = ?", "11"),
        listOf("7 + 5 = ?", "12"),
        listOf("7 + 6 = ?", "13"),
        listOf("7 + 7 = ?", "14"),
        listOf("7 + 8 = ?", "15"),
        listOf("7 + 9 = ?", "16"),
        listOf("7 + 10 = ?", "17"),
        listOf("8 + 1 = ?", "9"),
        listOf("8 + 2 = ?", "10"),
        listOf("8 + 3 = ?", "11"),
        listOf("8 + 4 = ?", "12"),
        listOf("8 + 5 = ?", "13"),
        listOf("8 + 6 = ?", "14"),
        listOf("8 + 7 = ?", "15"),
        listOf("8 + 8 = ?", "16"),
        listOf("8 + 9 = ?", "17"),
        listOf("8 + 10 = ?", "18"),
        listOf("9 + 1 = ?", "10"),
        listOf("9 + 2 = ?", "11"),
        listOf("9 + 3 = ?", "12"),
        listOf("9 + 4 = ?", "13"),
        listOf("9 + 5 = ?", "14"),
        listOf("9 + 6 = ?", "15"),
        listOf("9 + 7 = ?", "16"),
        listOf("9 + 8 = ?", "17"),
        listOf("9 + 9 = ?", "18"),
        listOf("9 + 10 = ?", "19"),
        listOf("10 + 1 = ?", "11"),
        listOf("10 + 2 = ?", "12"),
        listOf("10 + 3 = ?", "13"),
        listOf("10 + 4 = ?", "14"),
        listOf("10 + 5 = ?", "15"),
        listOf("10 + 6 = ?", "16"),
        listOf("10 + 7 = ?", "17"),
        listOf("10 + 8 = ?", "18"),
        listOf("10 + 9 = ?", "19"),
        listOf("10 + 10 = ?", "20"),
    )

val allAnswers: Set<String> =
    setOf(
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20"
    )

val gameOptions: List<String> =
    listOf(
        "Number Guessing Game",
        "Quiz Game",
        "Tic Tac Toe"
    )
