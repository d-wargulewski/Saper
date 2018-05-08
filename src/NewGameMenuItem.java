import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameMenuItem extends JFrame {
    JTextField textFieldX = new JTextField(10);
    JLabel labelX = new JLabel("X size:");

    JTextField textFieldY = new JTextField(10);
    JLabel labelY = new JLabel("Y size:");

    JTextField textFieldBombs = new JTextField(10);
    JLabel labelBombs = new JLabel("Bombs:");

    NewGameMenuItem() {
        super("New Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setVisible(true);
        setResizable(false);
        centerFrame();
        JPanel panel;

        // GUI
        panel = new JPanel();
        panel.setLayout(new MigLayout());

        panel.add(labelX);
        panel.add(textFieldX, "wrap");
        panel.add(labelY);
        panel.add(textFieldY, "wrap");
        panel.add(labelBombs);
        panel.add(textFieldBombs, "wrap");

        JButton confirm = new JButton("Commit");
        confirm.addActionListener(new MyActionListener());

        panel.add(confirm);
        add(panel);
        pack();
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int x = Integer.parseInt(textFieldX.getText());
                int y = Integer.parseInt(textFieldY.getText());
                int bombs = Integer.parseInt(textFieldBombs.getText());

                if(x*y > bombs){
                    new mainFrame(x, y, bombs);
                    dispose();
                } else {
                    textFieldBombs.setText("To much bombs!");
                }
            } catch (Exception exc) {
                System.out.println("Incorrect input data.");
            }
        }
    }

    private void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
    }
}
