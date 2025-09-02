package fr.campusetfocus.gameobject;

public abstract class GameObject {
    protected Integer id;
    protected String name;
    protected String description;
    protected int durability;

    public GameObject(String name, String description) {
        this.name = name;
        this.description = description;
        this.durability = 100;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public int getDurability() {
        return durability;
    }

    protected void setDurability(int durability) {
        this.durability = Math.max(0, Math.min(durability, 100));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + name + ", description=" + description + "]";
    }

}
