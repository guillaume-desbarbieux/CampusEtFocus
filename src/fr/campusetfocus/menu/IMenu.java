package fr.campusetfocus.menu;

import fr.campusetfocus.game.Board;

public interface IMenu {
    void display(String title, String message);
    void displayError(String title, String message);
    void displayWarning(String title, String message);
    void displaySuccess(String title, String message);
    String getString(String message);
    int getInt(String message);
    int getChoice(String message, String[] choices);
    void displayBoard(int position, Board board);
}
