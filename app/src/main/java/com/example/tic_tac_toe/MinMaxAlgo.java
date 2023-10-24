package com.example.tic_tac_toe;

import java.util.ArrayList;
import java.util.List;

public class MinMaxAlgo {
    // Constants for players
    private static final int PLAYER_X = 0;
    private static final int PLAYER_O = 1;
    public static final int EMPTY = 2;

    // Define the game board as a 3x3 array
    public int[][] board = new int[3][3];

    public MinMaxAlgo() {
        // Initialize the game board with empty cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public boolean isGameOver() {
        return isWin(PLAYER_X) || isWin(PLAYER_O) || getEmptyCells().isEmpty();
    }

    public boolean isWin(int player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true; // Row win
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true; // Column win
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true; // Diagonal win (top-left to bottom-right)
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true; // Diagonal win (top-right to bottom-left)
        }
        return false;
    }

    public int evaluate() {
        if (isWin(PLAYER_X)) {
            return 1; // Player X wins
        } else if (isWin(PLAYER_O)) {
            return -1; // Player O wins
        } else {
            return 0; // Draw
        }
    }

    public List<Cell> getEmptyCells() {
        List<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    emptyCells.add(new Cell(i, j));
                }
            }
        }
        return emptyCells;
    }

    public int minimax(int depth, int player) {
        List<Cell> emptyCells = getEmptyCells();

        if (isWin(PLAYER_X)) {
            return 1;
        } else if (isWin(PLAYER_O)) {
            return -1;
        } else if (emptyCells.isEmpty()) {
            return 0; // Draw
        }

        int bestScore = (player == PLAYER_X) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Cell cell : emptyCells) {
            board[cell.row][cell.col] = player;

            int score = (player == PLAYER_X) ? minimax(depth + 1, PLAYER_O) : minimax(depth + 1, PLAYER_X);

            board[cell.row][cell.col] = EMPTY;

            if (player == PLAYER_X) {
                bestScore = Math.max(bestScore, score);
            } else {
                bestScore = Math.min(bestScore, score);
            }
        }

        return bestScore;
    }

    public Cell findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        Cell bestMove = new Cell(-1, -1);

        List<Cell> emptyCells = getEmptyCells();

        for (Cell cell : emptyCells) {
            board[cell.row][cell.col] = PLAYER_X;
            int score = minimax(0, PLAYER_O);
            board[cell.row][cell.col] = EMPTY;

            if (score > bestScore) {
                bestScore = score;
                bestMove = cell;
            }
        }

        return bestMove;
    }
}

class Cell {
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
