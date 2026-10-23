# 1v1 Java Game Character Arena

An interactive, object-oriented 1v1 battle arena built using Java Swing. This project demonstrates core computer science principles including Abstraction, Abstract Data Types (ADT), dynamic Graphical User Interface (GUI) design, user input validation, and turn-based combat state management.

---

## Technical Concepts Applied

### 1. Abstraction
- **Abstract Base Class (`GameCharacter`):** Defines the foundational structure (Name, Class, HP, Max HP, MP) and abstract behaviors (`attack()` and `specialMove()`) required by all game entities.
- **Polymorphism:** Method signatures pass dynamic targets (`GameCharacter target`), allowing concrete subclasses to execute unique combat abilities while standardizing damage dealing and health reduction across all character types.

### 2. Abstract Data Type (ADT)
- **Custom Singly Linked List (`CharacterManager`):** Implements a custom linear data structure built with `Node` elements to manage the game characters dynamically without using built-in Java collections like `ArrayList`.
- **ADT Operations Supported:**
  - Dynamic element insertion (`addCharacter`)
  - Element traversal and search by index (`getAt`) and name (`findCharacter`)
  - Node removal (`removeCharacter`)
  - Health state reset across nodes (`resetAll`)

### 3. Graphical User Interface (GUI)
- Built using **Java Swing** with a modern dark theme interface (`#1E1E2E`).
- **Interactive UI Components:**
  - `JTable` rendering live ADT node states (Name, Class, HP / Max HP, MP, Status).
  - `JSplitPane` layout separating control panels and active combat logs.
  - `JTextArea` acting as a live console to display battle results and status alerts.

### 4. Input Validation & Error Handling
- Prevents creation of empty character names via text-field validation.
- Prevents duplicate character additions within the ADT list.
- Guarantees valid selection state prior to triggering attack/special actions.
- Handles turn validation to prevent defeated characters (0 HP) from making moves.

---

## Character Types

| Class | Base HP | Base MP | Basic Attack | Special Ability |
| :--- | :--- | :--- | :--- | :--- |
| **Mage** | 80 | 150 | Fireball (20 DMG) | Lightning Storm (40 DMG) |
| **Archer** | 100 | 60 | Precision Arrow (15 DMG) | Arrow Rain (35 DMG) |

---

## How to Run the Program

### Run in Eclipse IDE
1. Clone or download this repository to your computer.
2. Open Eclipse and select **File > Import > General > Projects from Folder or Archive**.
3. Select the folder containing this repository and click **Finish**.
4. Right-click `Main.java` in the Package Explorer.
5. Choose **Run As > Java Application**.

### Run via Command Line

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/YourUsername/java-game-character-improved.git](https://github.com/YourUsername/java-game-character-improved.git)
   cd java-game-character-improved
