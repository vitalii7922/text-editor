package editor;


import javax.swing.*;
import java.io.IOException;

public class ApplicationRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                    new TextEditor();
            }
        });
    }
}
