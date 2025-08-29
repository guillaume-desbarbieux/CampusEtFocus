package fr.campusetfocus.game.interaction;

public class Interaction {
    private final InteractionType type;
    private final Object object;

    public Interaction(InteractionType type, Object object) {
        this.type = type;
        this.object = object;
    }

    public Interaction(InteractionType type) {
        this(type, null);
    }

    public InteractionType getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

}
