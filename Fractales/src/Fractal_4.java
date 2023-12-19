import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author Handry Serrano Fabela
 */
public class Fractal_4 extends JFrame {
    
    private double zx, zy, cX, cY, tmp, cx, cy;
    private final int iterMax = 570;
    private final double zoom = 200;
    private BufferedImage imagen;

    public Fractal_4() {
        
        super("Fractal 4");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        imagen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        cx = -0.7;
        cy = 0.27015;

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = (x - 400) / zoom;
                zy = (y - 300) / zoom;
                int iter = iterMax;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cx;
                    zy = 2 * zx * zy + cy;
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
        
        new Fractal_4().setVisible(true);
        
    }
}