package cs5004.tictactoe;

/**
 * A player enum.
 */
public enum Player {
  X("X"),
  O("O");

  private final String playerString;

  Player(String playerString) {
    this.playerString = playerString;
  }

  public String getPlayerString() {
    return playerString;
  }

  @Override
  public String toString() {
    return getPlayerString();
  }
}
