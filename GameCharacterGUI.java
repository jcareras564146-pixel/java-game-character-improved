package game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GameCharacterGUI extends JFrame {
    private final CharacterManager manager = new CharacterManager();

    private JTextField nameField;
    private JComboBox<String> classDropdown;
    private JTable characterTable;
    private DefaultTableModel tableModel;
    private JTextArea logArea;

    public GameCharacterGUI() {
        setTitle("1v1 Game Character Arena");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Styling Theme
        Color darkBg = new Color(30, 30, 46);
        Color panelBg = new Color(45, 45, 68);
        Color textColor = new Color(220, 220, 240);
        Color accentColor = new Color(98, 114, 164);

        getContentPane().setBackground(darkBg);
        setLayout(new BorderLayout(10, 10));

        // Top Control Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.setBackground(panelBg);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(textColor);
        nameField = new JTextField(10);

        JLabel classLabel = new JLabel("Class:");
        classLabel.setForeground(textColor);
        classDropdown = new JComboBox<>(new String[]{"Mage", "Archer"});

        JButton addButton = new JButton("Add Fighter");
        JButton deleteButton = new JButton("Remove Selected");
        JButton resetButton = new JButton("Reset Health");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(classLabel);
        inputPanel.add(classDropdown);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(resetButton);

        // Center Panel (ADT Character View)
        String[] columnNames = {"Name", "Class", "HP / Max HP", "MP", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        characterTable = new JTable(tableModel);
        characterTable.setBackground(darkBg);
        characterTable.setForeground(textColor);
        characterTable.setGridColor(accentColor);
        characterTable.setSelectionBackground(accentColor);

        JScrollPane tableScrollPane = new JScrollPane(characterTable);
        tableScrollPane.setPreferredSize(new Dimension(400, 200));

        // Right Panel (Actions & Battle Log)
        JPanel actionPanel = new JPanel(new BorderLayout(5, 5));
        actionPanel.setBackground(panelBg);

        JPanel actionButtons = new JPanel(new GridLayout(2, 1, 5, 5));
        actionButtons.setBackground(panelBg);
        JButton attackBtn = new JButton("Attack Opponent");
        JButton specialBtn = new JButton("Use Special Ability");
        actionButtons.add(attackBtn);
        actionButtons.add(specialBtn);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(20, 20, 30));
        logArea.setForeground(new Color(80, 250, 123));
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logScrollPane = new JScrollPane(logArea);

        actionPanel.add(actionButtons, BorderLayout.NORTH);
        actionPanel.add(logScrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, actionPanel);
        splitPane.setResizeWeight(0.5);

        add(inputPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // Listeners
        addButton.addActionListener(e -> handleAddCharacter());
        deleteButton.addActionListener(e -> handleDeleteCharacter());
        resetButton.addActionListener(e -> handleReset());
        attackBtn.addActionListener(e -> handleCombat(true));
        specialBtn.addActionListener(e -> handleCombat(false));

        // Seed 1v1 Initial Setup
        manager.addCharacter(new Mage("Mage (GabMids)"));
        manager.addCharacter(new Archer("Archer (Legolas)"));
        refreshTable();
        logArea.append("=== 1v1 Arena Ready ===\nSelect a character on the table and hit Attack or Special Move!\n\n");
    }

    private void handleAddCharacter() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Character name cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (manager.findCharacter(name) != null) {
            JOptionPane.showMessageDialog(this, "A character with this name already exists!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedClass = (String) classDropdown.getSelectedItem();
        GameCharacter newChar = "Mage".equals(selectedClass) ? new Mage(name) : new Archer(name);

        manager.addCharacter(newChar);
        refreshTable();
        logArea.append("[SYSTEM] Joined Arena: " + name + " (" + selectedClass + ")\n");
        nameField.setText("");
    }

    private void handleDeleteCharacter() {
        int selectedRow = characterTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a character to delete.", "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String name = (String) tableModel.getValueAt(selectedRow, 0);
        manager.removeCharacter(name);
        refreshTable();
        logArea.append("[SYSTEM] Removed: " + name + "\n");
    }

    private void handleReset() {
        manager.resetAll();
        refreshTable();
        logArea.append("[SYSTEM] All characters restored to full HP!\n");
    }

    private void handleCombat(boolean isBasicAttack) {
        int selectedRow = characterTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an attacker from the list first!", "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (manager.getSize() < 2) {
            JOptionPane.showMessageDialog(this, "You need at least 2 fighters in the table for a 1v1 battle!", "Arena Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        GameCharacter attacker = manager.getAt(selectedRow);

        if (!attacker.isAlive()) {
            JOptionPane.showMessageDialog(this, attacker.getName() + " is defeated and cannot attack!", "Action Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Determine target (the other character in 1v1 context)
        int targetIndex = (selectedRow == 0) ? 1 : 0;
        GameCharacter target = manager.getAt(targetIndex);

        if (!target.isAlive()) {
            logArea.append("[SYSTEM] " + target.getName() + " is already defeated! Reset HP to fight again.\n");
            return;
        }

        String logMsg = isBasicAttack ? attacker.attack(target) : attacker.specialMove(target);
        logArea.append("> " + logMsg + "\n");

        if (!target.isAlive()) {
            logArea.append("⚡ " + target.getName() + " HAS BEEN DEFEATED!\n");
        }

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < manager.getSize(); i++) {
            GameCharacter c = manager.getAt(i);
            String status = c.isAlive() ? "ALIVE" : "DEFEATED";
            tableModel.addRow(new Object[]{
                c.getName(),
                c.getCharacterClass(),
                c.getHealth() + " / " + c.getMaxHealth(),
                c.getMana(),
                status
            });
        }
    }
}