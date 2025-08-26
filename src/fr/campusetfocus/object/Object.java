package fr.campusetfocus.object;

public abstract class Object {
    protected String name;
    protected String description;

    public Object(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + name + ", description=" + description + "]";
    }

}
