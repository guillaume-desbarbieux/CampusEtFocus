package fr.campusetfocus.game;

public class Game {
    private Board board;
    private Character player;
    private Dice dice;

    public Game() {
        this.board = new Board();
        this.dice = new Dice();
    }

    @Override
    public String toString() {
        return "Game {Board=" + board + ", player=" + player + ", dice=" + dice + "}";
    }

    public Board getBoard() {
        return board;
    }

    public Character getPlayer() {
        return player;
    }
    public void setPlayer(Character player) {
       this.player = player;
    }

    public Dice getDice() {
        return dice;
    }

    public void start() {
    }


}
