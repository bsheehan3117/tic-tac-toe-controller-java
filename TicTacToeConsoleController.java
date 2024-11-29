package cs5004.tictactoe;

import java.io.IOException;
import java.util.Scanner;

/**
 * A controller class for the Tic tac toe model, implements
 * the tic tac toe controller interface.
 */

public class TicTacToeConsoleController implements TicTacToeController {
  private final Scanner scanner;
  private final Appendable ap;

  /**
   * Controller for the tic tac toe model.
   *     @param rd Readable.
   *     @param ap Appendable.
   */
  public TicTacToeConsoleController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null.");
    }
    this.scanner = new Scanner(rd);
    this.ap = ap;
  }

  @Override
  public void playGame(TicTacToe m) {
    if (m == null) {

      // throw exception for null input
      throw new IllegalArgumentException("Tic Tac Toe model cannot be null.");
    }

    try {
      while (!m.isGameOver()) {
        this.ap.append(m.toString()).append("\n");
        this.ap.append("Enter a move for ").append(m.getTurn().toString()).append(":\n");

        // check for another line, if not, game over
        if (!scanner.hasNextLine()) {
          this.ap.append("Game quit! Ending game state:\n").append(m.toString()).append("\n");
          return;
        }

        String input = scanner.nextLine();

        // check if player quit, if so add message
        if (input.equalsIgnoreCase("q")) {
          this.ap.append("Game quit! Ending game state:\n").append(m.toString()).append("\n");
          return;
        }

        try {

          // split input coords
          String[] splitInput = input.split(" ");
          int row = Integer.parseInt(splitInput[0]) - 1;
          int col = Integer.parseInt(splitInput[1]) - 1;

          // make move
          m.move(row, col);
        } catch (NumberFormatException e) {

          // exception for invalid format
          this.ap.append("Invalid input, please enter two numbers.\n");
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.ap.append(e.getMessage()).append("\n");
        }
      }

      // game over
      this.ap.append(m.toString()).append("\n");
      this.ap.append("Game is over!\n");

      // check for winner, add message for result
      Player winner = m.getWinner();
      if (winner != null) {
        this.ap.append(winner.toString()).append(" wins.\n");
      } else {
        this.ap.append("Tie game.\n");
      }
    } catch (IOException e) {

      // input output exception
      throw new IllegalStateException("Input or output error", e);
    }
  }
}

