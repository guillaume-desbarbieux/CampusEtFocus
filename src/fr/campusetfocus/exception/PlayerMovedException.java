package fr.campusetfocus.exception;

public class PlayerMovedException extends Exception {
    private final int move;
    public PlayerMovedException(String message, int move) {
        super(message);
        this.move = move;
    }

    public int getMove() {
        return move;
    }
}
