package fr.campusetfocus.game;

public abstract class Cell  {
    protected int position;

    public Cell(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }

    public abstract Object getContent();

    @Override
    public  String toString() {
        return getClass().getSimpleName() + " [number=" + this.position + "content=" + getContent().toString() + "]";
    }

    public abstract String getSymbol();
    }
