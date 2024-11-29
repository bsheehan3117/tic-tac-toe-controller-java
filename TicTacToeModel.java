package cs5004.tictactoe;

import java.util.Arrays;

import java.util.stream.Collectors;

/**
 * This class represents a game of tictactoe.
 */

public class TicTacToeModel implements TicTacToe {
  private Player[][] board;
  private Player turn;
  private boolean gameOver;
  private Player winner;

  /**
   * Tictactoe constructor.
   */

  public TicTacToeModel() {

    board = new Player[3][3];
    turn = Player.X;
    gameOver = false;
    winner = null;
  }

  /**
   * The player makes a move placing an x or o in the grid.
   *     @param r the row of the move
   *     @param c the column of the move
   */

  @Override
  public void move(int r, int c) {
    validatePosition(r, c);
    checkGameOver();
    checkPosition(r, c);

    board[r][c] = turn;

    if (checkWin(turn)) {
      endGame(turn);
    } else if (isBoardFull()) {
      endGame(null);
    } else {
      changeTurns();
    }

    if (isGameOver()) {
      System.out.println("Game over " + getWinner() + " wins");
    }
  }

  /**
   * Checks if the game is over, throws an exception if so.
   *     @throws IllegalStateException if game is over.
   */
  private void checkGameOver() {
    if (gameOver) {
      throw new IllegalStateException("Game is over.");
    }
  }

  /**
   * Checks if a position is occupied.
   *     @param r the row of the move.
   *     @param c the columnof the move.
   */
  private void checkPosition(int r, int c) {
    if (board[r][c] != null) {
      throw new IllegalArgumentException("Position is occupied.");
    }
  }

  /**
   * Ends the game if there is a winner.
   *     @param winner the winner of the game.
   */
  private void endGame(Player winner) {
    this.gameOver = true;
    this.winner = winner;
  }

  /**
   * Change the turn of the player.
   */
  private void changeTurns() {
    this.turn = (this.turn == Player.X) ? Player.O : Player.X;
  }

  @Override
  public Player getTurn() {
    return turn;
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  /**
   * Return the current game state, as a 2D array of Player. A {@code null} value in the grid
   * indicates an empty position on the board.
   *
   * @return the current game board
   */

  @Override
  public Player[][] getBoard() {
    Player[][] copy = new Player[3][3];
    for (int i = 0; i < 3; i++) {
      copy[i] = Arrays.copyOf(board[i], 3);
    }
    return copy;
  }

  /**
   * Return the current {@link Player} mark at a given row and column, or {@code null} if the
   * position is empty.
   *
   * @param r the row
   * @param c the column
   * @return the player at the given position, or null if it's empty
   */

  @Override
  public Player getMarkAt(int r, int c) {
    validatePosition(r, c);
    return board[r][c];
  }

  /**
   * Check if a position is valid.
   * @param r the row of the position.
   * @param c the columnof the position.
   */

  private void validatePosition(int r, int c) {
    if (r < 0 || r >= 3 || c < 0 || c >= 3) {
      throw new IllegalArgumentException("Position is invalid.");
    }
  }

  /**
   * Checks if a player has won the game.
   *     @param player the player who's turn it was.
   *     @return true if win, false if not.
   */
  private boolean checkWin(Player player) {
    for (int i = 0; i < 3; i++) {

      // check rows
      if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
              // check columns
              (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
        return true;
      }
    }
    // check diagonal
    return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player);
  }

  /**
   * Check if game board is full.
   *     @return True if full, false if not.
   */
  private boolean isBoardFull() {

    // loop through board positions and check if any are null.
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == null) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
                    row -> " " + Arrays.stream(row).map(
                            p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
            .collect(Collectors.joining("\n-----------\n"));

  }
}