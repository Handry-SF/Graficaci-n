import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Handry Serrano Fabela
 */
public class Fractal_1 extends JPanel {
    
    private final int ancho = 800;
    private final int alto = 800;
    private final int iterMax = 570;
    private final double zoom = 200;
    private BufferedImage imagen;

    public Fractal_1() {
        
        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(ancho, alto));
        generateCubicMandelbrot();
        
    }

    private void generateCubicMandelbrot() {
        
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                double zx = 0;
                double zy = 0;
                double cx = (x - ancho / 2.0) / zoom;
                double cy = (y - alto / 2.0) / zoom;
                int iter = 0;
                while (zx * zx + zy * zy < 4 && iter < iterMax) {
                    double tmp = zx * zx * zx - 3 * zx * zy * zy + cx;
                    zy = 3 * zx * zx * zy - zy * zy * zy + cy;
                    zx = tmp;
                    iter++;
                }
                int color = iter == iterMax ? 0x000000 : Color.HSBtoRGB((float) iter / iterMax, 1, 1);
                imagen.setRGB(x, y, color);
                
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, this);
        
    }

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Fractal 1");
        Fractal_1 fractal = new Fractal_1();
        frame.add(fractal);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
}