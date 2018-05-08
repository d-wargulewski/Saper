import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class SaperField extends JButton {
    public static int width = 30;
    public static int height = 30;
    public int iValue;
    public int xAxis;
    public int yAxis;
    public boolean discovered = false;
    public boolean markBomb = false;


    public SaperField(int value, int x, int y) {
        iValue = value;
        xAxis = x;
        yAxis = y;

        setBorderPainted(true);
        setFont(new Font("Calibri", 1, 18));
        setFocusPainted(false);
        setBorder(new EtchedBorder());
        setSize(width, height);
        paint(0);
    }

    public void Uncover() {
        discovered = true;
        setText("");

        if (iValue > 0 && iValue < 9) {
            setText(String.valueOf(iValue));
            paint(iValue);
        } else if (iValue == 9) {
            setText("B");
            paint(iValue);
        }

        setBackground(Color.WHITE);
    }

    public void mark(MouseEvent ev) {
        if (!discovered) {
            if (SwingUtilities.isRightMouseButton(ev)) {
                if (getText().isEmpty()) {
                    markBomb = true;
                    setText("B");
                    setForeground(new Color(102, 0, 0));
                } else if (getText().contains("B")) {
                    markBomb = false;
                    setText("?");
                    setForeground(Color.black);
                } else {
                    setText("");
                }
            }
        }
    }

    private void paint(int value) {
        switch (value) {
            case 0:
                setBackground(new Color(206, 206, 206));
                break;
            case 1:
                setForeground(new Color(0, 0, 254));
                break;
            case 2:
                setForeground(new Color(0, 128, 0));
                break;
            case 3:
                setForeground(new Color(254, 0, 0));
                break;
            case 4:
                setForeground(new Color(0, 0, 128));
                break;
            case 5:
                setForeground(new Color(129, 1, 2));
                break;
            case 6:
                setForeground(new Color(0, 128, 129));
                break;
            case 7:
                setForeground(new Color(0, 0, 0));
                break;
            case 8:
                setForeground(new Color(128, 128, 128));
                break;
            case 9:
                setForeground(new Color(0, 0, 0));
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "x: " + xAxis + " y: " + yAxis + " value: " + iValue;
    }
}
