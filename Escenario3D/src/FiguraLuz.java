import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Serrano Fabela Handry
 */
public class FiguraLuz extends GLJPanel implements GLEventListener, KeyListener {

    private static String Titulo = "Escenario 3D Con Iluminacion";
    private static final int Canvas_Ancho = 640;
    private static final int Canvas_Alto = 480;
    private static final int FPS = 60;
    private static final float factInc = 5.0f;
    private float fovy = 45.0f;

    float rotacionX = 90.0f;
    float rotacionY = 0.0f;
    float rotacionZ = 0.0f;

    float luzX = 1f;
    float luzY = 1f;
    float luzZ = 1f;
    float luzDesplaza = 0.05f;

    float camaraX = 2.0f;
    float camaraY = 2.0f;
    float camaraZ = 8.0f;

    //                         R        G       B      A
    final float[] ambiente = {0.282f, 0.427f, 0.694f, 1.0f};
    final float[] posicion = {luzX, luzY, luzZ, 1.0f};

    //                             R    G    B    A
    final float[] colorNegro = {0.0f, 0.0f, 0.0f, 1.0f};
    final float[] colorBlanco = {1.0f, 1.0f, 1.0f, 1.0f};
    final float[] colorGris = {0.4f, 0.4f, 0.4f, 1.0f};
    final float[] colorGrisOsc = {0.2f, 0.2f, 0.2f, 1.0f};
    final float[] colorRojo = {1.0f, 0.0f, 0.0f, 1.0f};
    final float[] colorVerde = {0.0f, 1.0f, 0.0f, 1.0f};
    final float[] colorAzul = {0.0f, 0.0f, 0.6f, 1.0f};
    final float[] colorAmarillo = {1.0f, 1.0f, 0.0f, 1.0f};
    final float[] colorAmarilloClaro = {.5f, .5f, 0.0f, 1.0f};
    final float[] colorAmarilloIntenso = {1.0f, 1.0f, 0.2f, 1.0f};
    float esferaTamLuz = 1.5f;

    //                            R     G     B     A          
    final float[] mat_difuso = {0.6f, 0.6f, 0.6f, 1.0f};
    final float[] mat_especular = {1.0f, 1.0f, 1.0f, 1.0f};
    final float[] mat_brillo = {50.0f};
    private float aspecto;

    public FiguraLuz() {
        this.addGLEventListener(this);
        this.addKeyListener(this);
    }

    private GLU glu;
    private GLUT glut;

    public void init(GLAutoDrawable dibuja) {
        
        GL2 gl = dibuja.getGL().getGL2();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glClearDepth(1.0f);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glShadeModel(GL_SMOOTH);

        float[] luzAmbienteGlobal = {0.2f, 0.2f, 0.2f, 1.0f};
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, luzAmbienteGlobal, 0);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);

        // gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambiente, 0 );
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, colorBlanco, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, colorBlanco, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicion, 0);

        this.initPosition(gl);

        glu = new GLU();
        glut = new GLUT();
        
    }

    public void initPosition(GL2 gl) {
        
        float[] posicionLuz1 = {luzX, luzY, luzZ, 1.0f};
        float[] spotDireccion1 = {0.0f, -1.f, 0.f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicionLuz1, 0);
        
    }

    float esferaLuzX = 4.0f;
    float esferaLuzY = 0.0f;
    float esferaLuzDesplaza = 0.1f;

    public void animate(GL2 gl, GLU glu, GLUT glut) {

        float[] posicionLuz0 = {luzX, luzY, luzZ, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicionLuz0, 0);
        esferaLuz(gl, glu, glut);
        
    }

    public void setEsferaLuzMaterial(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, colorAzul, 0);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, colorAzul, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, colorAzul, 0);
        gl.glMateriali(cara, GL2.GL_SHININESS, 100);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorAzul, 0);
        
    }

    public void setAlgunMaterial(GL2 gl, int cara, float rgba[], int offset) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, rgba, offset);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, rgba, offset);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, rgba, offset);
        gl.glMaterialfv(cara, GL2.GL_SHININESS, rgba, offset);
        gl.glMateriali(cara, GL2.GL_SHININESS, 4);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorNegro, 0);
    }

    public void setAlgunMaterial(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, mat_difuso, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, mat_especular, 0);
        gl.glMaterialfv(cara, GL2.GL_SHININESS, mat_brillo, 0);
        
    }

    public void setMaterialBlanco(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, colorBlanco, 0);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, colorBlanco, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, colorBlanco, 0);
        gl.glMateriali(cara, GL2.GL_SHININESS, 4);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorNegro, 0);
        
    }

    public void setMaterialGris(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, colorGris, 0);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, colorGris, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, colorGris, 0);
        gl.glMateriali(cara, GL2.GL_SHININESS, 4);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorNegro, 0);
        
    }

    public void setMaterialGrisOsc(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, colorGrisOsc, 0);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, colorGrisOsc, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, colorGrisOsc, 0);
        gl.glMateriali(cara, GL2.GL_SHININESS, 4);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorNegro, 0);
        
    }

    public void setMaterialAmarillo(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, colorNegro, 0);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, colorAmarillo, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, colorAmarillo, 0);
        gl.glMateriali(cara, GL2.GL_SHININESS, 5);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorNegro, 0);
        
    }

    public void setMaterialAzul(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, colorAzul, 0);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, colorAzul, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, colorAzul, 0);
        gl.glMateriali(cara, GL2.GL_SHININESS, 4);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorNegro, 0);
        
    }

    public void setMaterialRojo(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, colorRojo, 0);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, colorRojo, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, colorBlanco, 0);
        gl.glMateriali(cara, GL2.GL_SHININESS, 100);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorNegro, 0);
        
    }

    public void setMaterialVerde(GL2 gl, int cara) {
        
        gl.glMaterialfv(cara, GL2.GL_AMBIENT, colorGrisOsc, 0);
        gl.glMaterialfv(cara, GL2.GL_DIFFUSE, colorVerde, 0);
        gl.glMaterialfv(cara, GL2.GL_SPECULAR, colorBlanco, 0);
        gl.glMateriali(cara, GL2.GL_SHININESS, 128);
        gl.glMaterialfv(cara, GL2.GL_EMISSION, colorGrisOsc, 0);
        
    }

    public void esferaLuz(GL2 gl, GLU glu, GLUT glut) {
        
        setAlgunMaterial(gl, GL.GL_FRONT);
        gl.glPushMatrix();
        {
            gl.glTranslatef(6.0f, 2.5f, -6.0f);

            gl.glScalef(esferaTamLuz, esferaTamLuz, esferaTamLuz);

            gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, colorAmarillo, 0);
            gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, colorAmarillo, 0);

            glut.glutSolidSphere(1.0f, 20, 20);
        }
        gl.glPopMatrix();
        
    }

    public void dibujaCasa(GL2 gl, GLUT glut) {
        
        this.setMaterialVerde(gl, GL.GL_FRONT);
        gl.glPushMatrix();
        {
            gl.glTranslatef(0.0f, -1.0f, 0.0f);

            gl.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);

            glut.glutSolidCube(3.0f);
        }
        gl.glPopMatrix();

        this.setMaterialRojo(gl, GL.GL_FRONT);
        gl.glPushMatrix();
        {
            gl.glTranslatef(0.0f, 0.5f, 0.0f);

            gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);

            glut.glutSolidCone(2.5f, 2.0f, 4, 1);
        }
        gl.glPopMatrix();
        
    }

    public void dibujaNube(GL2 gl, GLUT glut) {
        
        this.setMaterialBlanco(gl, GL.GL_FRONT);
        gl.glPushMatrix();
        {
            gl.glTranslatef(-8.0f, 3.0f, -5.0f);

            glut.glutSolidSphere(0.5, 20, 20);
            gl.glTranslatef(1.0f, 0.0f, 0.0f);
            glut.glutSolidSphere(0.7, 20, 20);
            gl.glTranslatef(1.0f, 0.2f, 0.0f);
            glut.glutSolidSphere(0.5, 20, 20);
        }
        gl.glPopMatrix();
        
    }

    @Override
    public void dispose(GLAutoDrawable glad) {}

    @Override
    public void display(GLAutoDrawable glad) {
        
        GL2 gl = glad.getGL().getGL2();

        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(fovy, aspecto, 0.1, 20.0);
        glu.gluLookAt(this.camaraX, this.camaraY, this.camaraZ, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0.53f, 0.81f, 0.98f, 1.0f);

        gl.glTranslatef(-2.0f, 0.0f, -2.0f);
        this.dibujaCasa(gl, glut);
        this.dibujaNube(gl, glut);
        this.animate(gl, this.glu, this.glut);

        gl.glFlush();
        
    }

    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int ancho, int alto) {
        
        GL2 gl = glad.getGL().getGL2();

        if (alto == 0) {
            alto = 1;
        }
        aspecto = (float) ancho / alto;

        gl.glViewport(0, 0, ancho, alto);

        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(fovy, aspecto, 0.1, 50.0);

        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    public static void main(String[] args) {
 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                GLJPanel canvas = new FiguraLuz();
                canvas.setPreferredSize(new Dimension(Canvas_Ancho, Canvas_Alto));

                final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

                final JFrame frame = new JFrame();

                FlowLayout fl = new FlowLayout();
                frame.setLayout(fl);

                frame.getContentPane().add(canvas);

                frame.addKeyListener((KeyListener) canvas);

                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        new Thread() {
                            @Override
                            public void run() {
                                if (animator.isStarted()) {
                                    animator.stop();
                                }
                                System.exit(0);
                            }
                        }.start();
                    }
                });

                frame.setTitle(Titulo);
                frame.pack();
                frame.setVisible(true);
                animator.start();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int codigo = e.getKeyCode();

        switch (codigo) {
            case KeyEvent.VK_DOWN:
                luzY -= 0.1f;
                break;
            case KeyEvent.VK_UP:
                luzY += 0.1f;
                break;
            case KeyEvent.VK_RIGHT:
                luzX += 0.1f;
                break;
            case KeyEvent.VK_LEFT:
                luzX -= 0.1f;
                break;
            case KeyEvent.VK_PAGE_UP:
                luzZ += 0.1f;
                break;
            case KeyEvent.VK_PAGE_DOWN:
                luzZ -= 0.1f;
                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}