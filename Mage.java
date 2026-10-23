package game;

public class Mage extends GameCharacter {
    public Mage(String name) {
        super(name, "Mage", 80, 150);
    }

    @Override
    public String attack(GameCharacter target) {
        int damage = 20;
        target.takeDamage(damage);
        return String.format("%s casts Fireball on %s for %d damage!", name, target.getName(), damage);
    }

    @Override
    public String specialMove(GameCharacter target) {
        int damage = 40;
        target.takeDamage(damage);
        return String.format("%s invokes Lightning Storm on %s for %d damage!", name, target.getName(), damage);
    }
}