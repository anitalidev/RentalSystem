package ui.pages;

import javax.swing.*;

import java.awt.*;

// Abstract Class Menu that holds the basic items of a menu, namely the panel
public abstract class Menu extends JFrame {

    protected static final int WIDTH = 500;
    protected static final int HEIGHT = 600;

    protected JPanel buttonPanel;
    protected String question;
    protected String userInput;
    
    // EFFECTS: Creates a menu
    public Menu(String name) {
        super(name);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        question = "";
    }

    // EFFECTS: handles showing main frame for the menu
    abstract void runMenu();

    // MODIFIES: this
    // EFFECTS: sets the frame to visible
    protected void showMenu() {
        this.setVisible(true);
    }

    // EFFECTS: Asks user question and sets userInput to submitted input
    protected JDialog makeDialogue() {
        JDialog dialog = new JDialog(this, question, JDialog.ModalityType.DOCUMENT_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Container items = dialog.getContentPane();
        dialog.setSize(new Dimension(600, 100));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JTextField input = new JTextField(15);
        input.setBounds(50, 50, 250, 15);
        JButton submitButton = new JButton("Submit");
        JLabel inputLabel = new JLabel(question);
    
        submitButton.addActionListener(e -> {
            userInput = input.getText();
            dialog.setVisible(false);
        });

        buttonPanel.add(inputLabel);
        buttonPanel.add(input);
        buttonPanel.add(submitButton);

        items.add(buttonPanel);

        return dialog;
    }

    // EFECTS: clears displayed frame
    protected void clear() {
        getContentPane().removeAll();  
        getContentPane().revalidate(); 
        getContentPane().repaint();
    }
}
