package game;

public class Archer extends GameCharacter {
    public Archer(String name) {
        super(name, "Archer", 100, 60);
    }

    @Override
    public String attack(GameCharacter target) {
        int damage = 15;
        target.takeDamage(damage);
        return String.format("%s shoots a precision arrow at %s for %d damage!", name, target.getName(), damage);
    }

    @Override
    public String specialMove(GameCharacter target) {
        int damage = 35;
        target.takeDamage(damage);
        return String.format("%s unleashes Arrow Rain on %s for %d damage!", name, target.getName(), damage);
    }
}