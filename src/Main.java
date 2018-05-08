import java.awt.*;

public class Main {
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new mainFrame(16, 16, 40);
            }
        });
    }
}

