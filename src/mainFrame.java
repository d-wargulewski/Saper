import static javax.swing.JOptionPane.showMessageDialog;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mainFrame extends JFrame {
    JPanel panelSaperArea;
    SaperField[][] SaperArea;
    private int iBomb;
    private int xTsize;
    private int yTsize;
    boolean GameOver = false;
    boolean firstFieldUncovered = false;

    public mainFrame(int XX, int YY, int BB) {
        super("Saper 1.0");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout());
        SetComponents();

        // Default input
        xTsize = XX;
        yTsize = YY;
        iBomb = BB;

        setSize(((xTsize + 1) * SaperField.width), ((yTsize + 2) * SaperField.height + 15));
        CenterFrame();

        // Call a method "FillAreaGame" and fill SaperArea with numbers
        SaperArea = FillAreaGame(xTsize, yTsize, 0);

        // panelSaperArea GUI
        panelSaperArea.setLayout(new MigLayout("gap 0px 0px, wrap " + xTsize));
        panelSaperArea.removeAll();

        String StrConstraints =
                String.format("width %d:%d:%d, height %d:%d:%d",
                        SaperField.width,
                        SaperField.width,
                        SaperField.width,
                        SaperField.height,
                        SaperField.height,
                        SaperField.height);

        ///// Add fields to grid || MiGLayout
        for (int y = 0; y < yTsize; y++) {
            for (int x = 0; x < xTsize; x++) {
                SaperArea[x][y].addActionListener(new MyActionListener());
                SaperArea[x][y].addMouseListener(new MouseList());
                panelSaperArea.add(SaperArea[x][y], StrConstraints);
            }
        }

        pack();
    }

    private SaperField[][] FillAreaGame(int x, int y, int bombs) {
        SaperField[][] SF = new SaperField[x][y];

        for (int i = 0; i < y; i++) {
            for (int k = 0; k < x; k++) {
                SF[k][i] = new SaperField(EnumFS.EMPTY.value(), k, i);
                // System.out.print(SF[k][i].iValue + "\t");
            }
            // System.out.print("\n");
        }

        return SF;
    }

    private void FillAreaGame(SaperField[][] SapF, int xFFU, int yFFU) {
        ////// Variables
        // SapF[xTsize][yTsize]
        Random gen = new Random();
        int bombs_left = this.iBomb;

        ////// Fill area with empty fields, that equals = 0
        for (int i = 0; i < this.xTsize; i++) {
            for (int k = 0; k < this.yTsize; k++) {
                SapF[i][k].iValue = EnumFS.EMPTY.value();
            }
        }

        ////// Bombs has been planted
        while (bombs_left > 0) {
            int rand_x, rand_y;

            boolean correct_x = false;
            boolean correct_y = false;

            do {
                rand_x = gen.nextInt(xTsize);
                if (rand_x != xFFU) correct_x = true;

                rand_y = gen.nextInt(yTsize);
                if (rand_y != yFFU) correct_y = true;

            } while (!correct_x || !correct_y);

            if (SapF[rand_x][rand_y].iValue == EnumFS.EMPTY.value()) {
                SapF[rand_x][rand_y].iValue = EnumFS.BOMB.value();
                bombs_left--;
            }
        }

        for (int i = 0; i < this.xTsize; i++) {
            for (int k = 0; k < this.yTsize; k++) {
                //SapF[i][k].Uncover();
            }
        }

        ////// How many bombs around the field?
        // SapF[xTsize][yTsize]
        for (int i = 0; i < xTsize; i++) {
            for (int k = 0; k < yTsize; k++) {

                // If empty, check around for bombs
                if (SapF[i][k].iValue == EnumFS.EMPTY.value()) {
                    int number_of_bombs = 0;


                    for (int kk = k - 1; kk < k + 2; kk++) {
                        for (int ii = i - 1; ii < i + 2; ii++) {
                            try {
                                if (SapF[ii][kk].iValue == EnumFS.BOMB.value()) number_of_bombs++;
                            } catch (ArrayIndexOutOfBoundsException ee) {
                                // do nothing
                            }
                        }
                    }

                    SapF[i][k].iValue = number_of_bombs;
                }
            }
        }
    }

    private void SetComponents() {
        JMenuBar menuBar;
        JMenu menuGame;
        JMenu menuHelp;
        JMenuItem menuNewGame;
        JMenuItem menuEasy;
        JMenuItem menuMedium;
        JMenuItem menuHard;
        JMenuItem menuExit;
        JMenuItem menuDescription;

        // BAR
        menuBar = new JMenuBar();

        // MENU
        menuGame = new JMenu("Game");
        menuGame.setMnemonic(KeyEvent.VK_G);

        menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(KeyEvent.VK_H);

        // ITEMS
        menuNewGame = new JMenuItem("New Game");
        menuEasy = new JMenuItem("Easy");
        menuMedium = new JMenuItem("Medium");
        menuHard = new JMenuItem("Hard");
        menuExit = new JMenuItem("Exit");
        menuDescription = new JMenuItem("About");


        /////////// ADD
        // menuGame
        menuGame.add(menuNewGame);
        menuGame.addSeparator();
        menuGame.add(menuEasy);
        menuGame.add(menuMedium);
        menuGame.add(menuHard);
        menuGame.addSeparator();
        menuGame.add(menuExit);

        // menuHelp
        menuHelp.add(menuDescription);

        for (int i = 0; i < menuGame.getItemCount(); i++) {
            if (menuGame.getItem(i) != null) {
                menuGame.getItem(i).addActionListener(new MenuListener());
            }
        }

        for (int i = 0; i < menuHelp.getItemCount(); i++) {
            if (menuHelp.getItem(i) != null) {
                menuHelp.getItem(i).addActionListener(new MenuListener());
            }
        }

        // MenuBar
        menuBar.add(menuGame);
        menuBar.add(menuHelp);

        // JFrame
        add(menuBar, "dock north");
        add(panelSaperArea, "dock south");
    }

    private void CenterFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
    }

    private void Close() {
        System.exit(0);
    }

    class MouseList extends MouseAdapter {
        public void mousePressed(MouseEvent ev) {
            SaperField SF = (SaperField) ev.getSource();
            if(!GameOver) SF.mark(ev);
        }
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SaperField field = (SaperField) e.getSource();

            if(!GameOver){
                if (!field.markBomb) {
                    if (firstFieldUncovered == false) {
                        FillAreaGame(SaperArea, field.xAxis, field.yAxis);
                        firstFieldUncovered = true;
                    }

                    if (field.iValue == 9) {
                        field.Uncover();
                        for (SaperField SF[] : SaperArea) {
                            for (SaperField SFelement : SF) {
                                if (SFelement.iValue == 9) {
                                    SFelement.Uncover();
                                }

                                GameOver = true;
                            }
                        }
                        showMessageDialog(null, "Bomb! You are dead.");
                    } else if (field.iValue > 0 && field.iValue < 9)
                        field.Uncover();
                    else {
                        ArrayList lista = new ArrayList();
                        boolean el_exist = false;
                        int i = 0;

                        lista.add(field);

                        // Prepare empty fields to uncover it
                        do {
                            if (((SaperField) lista.get(i)).iValue == 0) {
                                for (int kk = ((SaperField) lista.get(i)).xAxis - 1; kk <= ((SaperField) lista.get(i)).xAxis + 1; kk++) {
                                    for (int ii = ((SaperField) lista.get(i)).yAxis - 1; ii <= ((SaperField) lista.get(i)).yAxis + 1; ii++) {
                                        try {
                                            if (SaperArea[kk][ii].iValue == 0) {
                                                el_exist = false;
                                                for (int h = 0; h < lista.size(); h++) {
                                                    if ((SaperArea[kk][ii].xAxis == ((SaperField) lista.get(h)).xAxis) & (SaperArea[kk][ii].yAxis == ((SaperField) lista.get(h)).yAxis)) {
                                                        el_exist = true;
                                                    }
                                                }

                                                if (el_exist == false) {
                                                    lista.add(SaperArea[kk][ii]);
                                                }
                                            }
                                        } catch (ArrayIndexOutOfBoundsException ee) {
                                            // Do nothing
                                        } // Try, catch
                                    }
                                } // for
                            } // if

                            if (el_exist = true)
                                i++;
                        } while (i < lista.size());


                        for (int h = 0; h < lista.size(); h++) {
                            for (int kk = ((SaperField) lista.get(h)).xAxis - 1; kk <= ((SaperField) lista.get(h)).xAxis + 1; kk++) {
                                for (int ii = ((SaperField) lista.get(h)).yAxis - 1; ii <= ((SaperField) lista.get(h)).yAxis + 1; ii++) {
                                    try {
                                        SaperArea[kk][ii].Uncover();
                                    } catch (ArrayIndexOutOfBoundsException ee) {

                                    }
                                }
                            }
                        } // for(lista)
                    }
                }
            }
        }
    }

    class MenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String compName = ((JMenuItem) e.getSource()).getText();

            switch (compName) {
                case "New Game":
                    System.out.println(compName);
                    new NewGameMenuItem();
                    dispose();
                    break;
                case "Easy":
                    System.out.println(compName);
                    dispose();
                    new mainFrame(8, 8, 10);
                    break;
                case "Medium":
                    System.out.println(compName);
                    dispose();
                    new mainFrame(16, 16, 40);
                    break;
                case "Hard":
                    System.out.println(compName);
                    dispose();
                    new mainFrame(30, 16, 99);
                    break;
                case "Exit":
                    System.out.println(compName);
                    Close();
                    break;
                case "Help":
                    System.out.println(compName);
                    break;
                default:
                    System.out.println("Default!");
                    break;
            }
        }
    }
}
