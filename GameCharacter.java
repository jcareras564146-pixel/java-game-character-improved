package game;

public abstract class GameCharacter {
    protected String name;
    protected String characterClass;
    protected int health;
    protected int maxHealth;
    protected int mana;

    public GameCharacter(String name, String characterClass, int health, int mana) {
        this.name = name;
        this.characterClass = characterClass;
        this.health = health;
        this.maxHealth = health;
        this.mana = mana;
    }

    // Abstract methods
    public abstract String attack(GameCharacter target);
    public abstract String specialMove(GameCharacter target);

    // Concrete combat methods
    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public void reset() {
        this.health = this.maxHealth;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    // Getters
    public String getName() { return name; }
    public String getCharacterClass() { return characterClass; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getMana() { return mana; }
}