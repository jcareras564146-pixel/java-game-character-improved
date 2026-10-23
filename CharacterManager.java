package game;

public class CharacterManager {

    private static class Node {
        GameCharacter character;
        Node next;

        Node(GameCharacter character) {
            this.character = character;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public CharacterManager() {
        this.head = null;
        this.size = 0;
    }

    public void addCharacter(GameCharacter character) {
        Node newNode = new Node(character);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public boolean removeCharacter(String name) {
        if (head == null) return false;

        if (head.character.getName().equalsIgnoreCase(name)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null && !current.next.character.getName().equalsIgnoreCase(name)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }
        return false;
    }

    public GameCharacter findCharacter(String name) {
        Node current = head;
        while (current != null) {
            if (current.character.getName().equalsIgnoreCase(name)) {
                return current.character;
            }
            current = current.next;
        }
        return null;
    }

    public GameCharacter getAt(int index) {
        if (index < 0 || index >= size) return null;
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.character;
    }

    public int getSize() {
        return size;
    }

    public void resetAll() {
        Node current = head;
        while (current != null) {
            current.character.reset();
            current = current.next;
        }
    }
}