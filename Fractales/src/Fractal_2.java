import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Handry Serrano Fabela
 */
public class Fractal_2 extends JFrame {

    private double zx, zy, cX, cY, tmp;
    private final int iterMax = 570;
    private final double zoom = 200;
    private BufferedImage imagen;

    public Fractal_2() {
        
        super("Fractal 2");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        imagen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 400) / zoom;
                cY = (y - 300) / zoom;
                int iter = iterMax;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double zx2 = zx * zx;
                    double zy2 = zy * zy;
                    double zx4 = zx2 * zx2;
                    double zy4 = zy2 * zy2;

                    tmp = zx4 - 6 * zx2 * zy2 + zy4 + cX;
                    zy = 4 * Math.pow(zx, 3) * zy - 4 * zx * Math.pow(zy, 3) + cY;
                    zx = tmp;

                    iter--;
                }
                int color = iter | (iter << 8);
                imagen.setRGB(x, y, color);
                
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        
        g.drawImage(imagen, 0, 0, this);
        
    }

    public static void main(String[] args) {
        
        new Fractal_2().setVisible(true);
        
    }
}