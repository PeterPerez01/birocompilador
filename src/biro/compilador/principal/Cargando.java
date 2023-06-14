package biro.compilador.principal;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Cargando implements Runnable {
    private JTextArea textArea;
    private String animation = "|/-\\";
    private int position = 0;
    private boolean stop = false;

    public Cargando(JTextArea bar) {
        this.textArea = bar;
    }

    public void start() {
        new Thread(this).start();
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        while (!stop) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 2000) {
                break;
            }

            String loadingText = String.format("Cargando... %c", animation.charAt(position));
            position = (position + 1) % animation.length();
            SwingUtilities.invokeLater(() -> textArea.setText(loadingText));
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(() -> textArea.setText("Listo!"));
    }   
    
}
