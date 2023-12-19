package pizarra2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pizarra2d.MatricesTransformacion;

/**
 *
 * @author LENOVO
 */
public class Pizarra2D extends JFrame {
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Pizarra2D pizarra = new Pizarra2D();
                pizarra.setSize(1350, 525);
                pizarra.setVisible(true);
            }
        });
    }

    static final int LINEA = 0;
    static final int TRIANGULO = 1;
    static final int CUADRADO = 2;
    static final int CIRCULO = 3;
    private boolean modoDibujoActivado = true;
    MatricesTransformacion matrizTransformacion; // Agrega una instancia de la clase Matrix

    JTextArea textareaPizarra;
    JPanel panelControlesOeste;
    JPanel panelControlesEste;

    JButton btnLinea;
    JButton btnTriangulo;
    JButton btnCuadrado;
    JButton btnCirculo;
    JButton btnFinModoDibujo; // Nuevo botón para finalizar el modo dibujo

    JButton btnTrasladar;
    JButton btnRotar;
    JButton btnEscalar;

    Color color;
    boolean dibujandoLinea = false;
    boolean dibujandoTriangulo = false;
    boolean dibujandoCuadrado = false;
    boolean dibujandoCirculo = false;
    Point puntoInicial;
    List<Point> puntosInicio;
    List<Point> puntosFin;
    List<Figura> figuras; // Lista para almacenar las figuras

    public Pizarra2D() {
        matrizTransformacion = new MatricesTransformacion(); // Inicializa la instancia de Matrix
        textareaPizarra = new JTextArea();
        textareaPizarra.setEditable(false);
        textareaPizarra.setBackground(Color.white);

        panelControlesOeste = new JPanel();
        panelControlesOeste.setLayout(new BoxLayout(panelControlesOeste, BoxLayout.Y_AXIS));

        panelControlesEste = new JPanel();
        panelControlesEste.setLayout(new BoxLayout(panelControlesEste, BoxLayout.Y_AXIS));

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        color = Color.black;
        puntosInicio = new ArrayList<>();
        puntosFin = new ArrayList<>();
        // Inicializar la lista de figuras
        figuras = new ArrayList<>();

        btnLinea = new JButton("Linea");
        btnTriangulo = new JButton("Triangulo");
        btnCuadrado = new JButton("Cuadrado");
        btnCirculo = new JButton("Circulo");
        btnFinModoDibujo = new JButton("Seleccionar Figura");

        btnTrasladar = new JButton("Trasladar");
        btnRotar = new JButton("Rotar");
        btnEscalar = new JButton("Escalar");

        // Configuración de los botones
        configurarBotones();

        this.panelControlesOeste.add(Box.createVerticalStrut(110)); // Espacio vertical entre botones
        this.panelControlesOeste.add(btnLinea);
        this.panelControlesOeste.add(Box.createVerticalStrut(30)); // Espacio vertical entre botones
        this.panelControlesOeste.add(btnTriangulo);
        this.panelControlesOeste.add(Box.createVerticalStrut(30)); // Espacio vertical entre botones
        this.panelControlesOeste.add(btnCuadrado);
        this.panelControlesOeste.add(Box.createVerticalStrut(30)); // Espacio vertical entre botones
        this.panelControlesOeste.add(btnCirculo);
        this.panelControlesOeste.add(Box.createVerticalStrut(140)); // Espacio vertical entre botones
        this.panelControlesOeste.add(btnFinModoDibujo);
        this.panelControlesOeste.add(Box.createVerticalStrut(30)); // Espacio vertical entre botones
        this.panelControlesEste.add(Box.createVerticalStrut(140)); // Espacio vertical entre botones
        this.panelControlesEste.add(btnTrasladar);
        this.panelControlesEste.add(Box.createVerticalStrut(30)); // Espacio vertical entre botones
        this.panelControlesEste.add(btnRotar);
        this.panelControlesEste.add(Box.createVerticalStrut(30)); // Espacio vertical entre botones
        this.panelControlesEste.add(btnEscalar);
        this.panelControlesEste.add(Box.createVerticalStrut(30)); // Espacio vertical entre botones

        this.add(textareaPizarra, BorderLayout.CENTER);
        this.add(panelControlesOeste, BorderLayout.WEST);
        this.add(panelControlesEste, BorderLayout.EAST);

        // Agregar listeners para los botones de dibujo
        agregarListeners();

        // Agregar MouseListener para manejar los clics en la pizarra
        agregarMouseListener();

        this.setVisible(true);
        this.pack();
    }

    private void configurarBotones() {
        // Configurar el tamaño común para los botones
        Dimension btnSize = new Dimension(120, 30);
        Dimension btnSizeF = new Dimension(140, 30);
        btnLinea.setPreferredSize(btnSize);
        btnTriangulo.setPreferredSize(btnSize);
        btnCuadrado.setPreferredSize(btnSize);
        btnCirculo.setPreferredSize(btnSize);
        btnTrasladar.setPreferredSize(btnSize);
        btnRotar.setPreferredSize(btnSize);
        btnEscalar.setPreferredSize(btnSize);
        btnFinModoDibujo.setPreferredSize(btnSizeF);
    }
    
    private void restablecerAparienciaBotones() {
        // Restaura la apariencia visual de todos los botones al estado normal
        btnLinea.setBorder(null);
        btnTriangulo.setBorder(null);
        btnCuadrado.setBorder(null);
        btnCirculo.setBorder(null);
    }
    
    // Agrega un método para cambiar el estado de los botones
    private void cambiarEstadoBotonesDibujo() {
        modoDibujoActivado = !modoDibujoActivado; // Invierte el estado
        btnLinea.setEnabled(modoDibujoActivado);
        btnTriangulo.setEnabled(modoDibujoActivado);
        btnCuadrado.setEnabled(modoDibujoActivado);
        btnCirculo.setEnabled(modoDibujoActivado);
    }

    private void agregarListeners() {
        // Botones de Dibujo
        btnLinea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restaura la apariencia visual del botón "Triángulo" al estado normal
                restablecerAparienciaBotones();
                // Cambia la apariencia visual del botón para indicar que está seleccionado
                btnLinea.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                // Resetea el indicador de dibujo
                dibujandoLinea = true;
                dibujandoTriangulo = false;
                // Resetea los puntos para una nueva línea
                puntosInicio.clear();
                puntosFin.clear();
            }
        });

        btnTriangulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restaura la apariencia visual del botón "Línea" al estado normal
                restablecerAparienciaBotones();
                // Cambia la apariencia visual del botón para indicar que está seleccionado
                btnTriangulo.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                // Resetea el indicador de dibujo
                dibujandoLinea = false;
                dibujandoTriangulo = true;
                // Resetea los puntos para un nuevo triángulo
                puntosInicio.clear();
                puntosFin.clear();
            }
        });

        btnCuadrado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restaura la apariencia visual de todos los botones al estado normal
                restablecerAparienciaBotones();
                // Cambia la apariencia visual del botón para indicar que está seleccionado
                btnCuadrado.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                // Resetea los indicadores de dibujo
                dibujandoLinea = false;
                dibujandoTriangulo = false;
                dibujandoCuadrado = true;
                dibujandoCirculo = false;
                // Resetea los puntos para un nuevo cuadrado
                puntosInicio.clear();
                puntosFin.clear();
            }
        });

        btnCirculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restaura la apariencia visual de todos los botones al estado normal
                restablecerAparienciaBotones();
                // Cambia la apariencia visual del botón para indicar que está seleccionado
                btnCirculo.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                // Resetea los indicadores de dibujo
                dibujandoLinea = false;
                dibujandoTriangulo = false;
                dibujandoCuadrado = false;
                dibujandoCirculo = true;
                // Resetea los puntos para un nuevo círculo
                puntosInicio.clear();
                puntosFin.clear();
            }
        });

        // Agrega un ActionListener al botón "Fin del modo dibujo"
        btnFinModoDibujo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Desactivar los botones de dibujo
                cambiarEstadoBotonesDibujo();
                // Permitir la selección de figuras
                permitirSeleccionFiguras();
            }
        });

        // Botones de Transformación
        btnTrasladar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene las distancias de traslación del usuario
                int deltaX = obtenerValorNumerico("Ingrese la distancia de traslación en X:");
                int deltaY = obtenerValorNumerico("Ingrese la distancia de traslación en Y:");

                // Aplica traslación a la última figura seleccionada
                aplicarTransformacion("Traslacion", deltaX, deltaY);
            }
        });

        btnRotar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene el ángulo de rotación del usuario
                double angulo = obtenerValorNumericoDecimal("Ingrese el ángulo de rotación en grados:");

                // Aplica rotación a la última figura seleccionada
                aplicarTransformacion("Rotacion", angulo);
            }
        });

        btnEscalar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene los factores de escala del usuario
                double factorX = obtenerValorNumericoDecimal("Ingrese el factor de escala en X:");
                double factorY = obtenerValorNumericoDecimal("Ingrese el factor de escala en Y:");

                // Aplica escala a la última figura seleccionada
                aplicarTransformacion("Escala", factorX, factorY);
            }
        });
    }

    private void agregarMouseListener() {
        textareaPizarra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!dibujandoLinea && !dibujandoTriangulo && !dibujandoCuadrado && !dibujandoCirculo) {
                    // No se está dibujando ninguna figura, simplemente registra el punto inicial
                    puntoInicial = e.getPoint();
                    return;
                }
                if (dibujandoLinea) {
                    manejarClickLinea(e.getPoint());
                } else if (dibujandoTriangulo) {
                    manejarClickTriangulo(e.getPoint());
                } else if (dibujandoCuadrado) {
                    manejarClickCuadrado(e.getPoint());
                } else if (dibujandoCirculo) {
                    manejarClickCirculo(e.getPoint());
                }
            }
        });
    }

    private void manejarClickLinea(Point punto) {
        if (puntoInicial == null) {
            // Primer clic, establece el punto inicial
            puntoInicial = punto;
        } else {
            // Dibuja la línea desde el punto inicial hasta el punto actual
            dibujarLinea(puntoInicial, punto, color);
            // Guarda la figura en la lista de figuras
            List<Point> puntos = new ArrayList<>();
            puntos.add(puntoInicial);
            puntos.add(punto);
            figuras.add(new Figura(LINEA, puntos));
            // Reinicia el punto inicial para la próxima línea
            puntoInicial = null;
        }
    }

    private void manejarClickTriangulo(Point punto) {
        if (puntosInicio.size() < 3) {
            // Agrega el punto inicial
            puntosInicio.add(punto);
        } else {
            // Dibuja el triángulo desde el último punto inicial hasta el punto actual
            dibujarTriangulo(puntosInicio.get(0), puntosInicio.get(1), punto, color);
            // Guarda la figura en la lista de figuras
            List<Point> puntos = new ArrayList<>(puntosInicio);
            puntos.add(punto);
            figuras.add(new Figura(TRIANGULO, puntos));
            // Reinicia la lista de puntos para un nuevo triángulo
            puntosInicio.clear();
        }
    }

    private void manejarClickCuadrado(Point punto) {
        if (puntosInicio.size() < 4) {
            // Agrega el punto inicial
            puntosInicio.add(punto);
        } else {
            // Dibuja el cuadrado desde el último punto inicial hasta el punto actual
            dibujarCuadrado(puntosInicio.get(0), puntosInicio.get(1), puntosInicio.get(2), punto, color);
            // Guarda la figura en la lista de figuras
            List<Point> puntos = new ArrayList<>(puntosInicio);
            puntos.add(punto);
            figuras.add(new Figura(CUADRADO, puntos));
            // Reinicia la lista de puntos para un nuevo cuadrado
            puntosInicio.clear();
        }
    }

    private void manejarClickCirculo(Point punto) {
        if (puntosInicio.size() == 0) {
            // Primer clic, establece el centro del círculo
            puntosInicio.add(punto);
        } else if (puntosInicio.size() == 1) {
            // Segundo clic, establece el punto para determinar el radio
            puntosFin.add(punto);
            // Dibuja el círculo desde el centro hasta el punto del radio
            dibujarCirculo(puntosInicio.get(0), puntosFin.get(0), color);
            // Guarda la figura en la lista de figuras
            List<Point> puntos = new ArrayList<>(puntosInicio);
            puntos.add(puntosFin.get(0));
            figuras.add(new Figura(CIRCULO, puntos));
            // Reinicia la lista de puntos para un nuevo círculo
            puntosInicio.clear();
            puntosFin.clear();
        }
    }

    private void dibujarLinea(Point inicio, Point fin, Color color) {
        Graphics g = textareaPizarra.getGraphics();
        g.setColor(color);
        g.drawLine(inicio.x, inicio.y, fin.x, fin.y);
    }

    private void dibujarTriangulo(Point p1, Point p2, Point p3, Color color) {
        Graphics g = textareaPizarra.getGraphics();
        g.setColor(color);
        int[] xPoints = {p1.x, p2.x, p3.x};
        int[] yPoints = {p1.y, p2.y, p3.y};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    private void dibujarCuadrado(Point p1, Point p2, Point p3, Point p4, Color color) {
        Graphics g = textareaPizarra.getGraphics();
        g.setColor(color);
        int[] xPoints = {p1.x, p2.x, p3.x, p4.x};
        int[] yPoints = {p1.y, p2.y, p3.y, p4.y};
        g.fillPolygon(xPoints, yPoints, 4);
    }

    private void dibujarCirculo(Point centro, Point puntoEnRadio, Color color) {
        Graphics g = textareaPizarra.getGraphics();
        g.setColor(color);

        int radio = (int) centro.distance(puntoEnRadio);

        int x = centro.x - radio;
        int y = centro.y - radio;
        int diameter = radio * 2;

        g.fillOval(x, y, diameter, diameter);
    }

    private void limpiarPizarra() {
        // Limpia la pizarra dibujando un rectángulo del tamaño de la pizarra con el color de fondo
        Graphics g = textareaPizarra.getGraphics();
        g.setColor(textareaPizarra.getBackground());
        g.fillRect(0, 0, textareaPizarra.getWidth(), textareaPizarra.getHeight());
    }

    private void permitirSeleccionFiguras() {
        // Agregar MouseListener para seleccionar figuras
        textareaPizarra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFigura(e.getPoint());
            }
        });
    }
    
    private void seleccionarFigura(Point punto) {
        for (Figura figura : figuras) {
            if (puntoEstaDentroFigura(punto, figura.puntos, figura.tipo)) {
                // Imprimir coordenadas de la figura en la consola
                imprimirCoordenadas(figura);
                break; // No es necesario seguir buscando después de encontrar una figura
            }
        }
    }
    
    private List<Figura> obtenerFigurasSeleccionadas() {
        List<Figura> figurasSeleccionadas = new ArrayList<>();

        for (Figura figura : figuras) {
            if (puntoEstaDentroFigura(figura.puntos.get(0), figura.puntos, figura.tipo)) {
                figurasSeleccionadas.add(figura);
            }
        }

        return figurasSeleccionadas;
    }

    private boolean puntoEstaDentroFigura(Point punto, List<Point> puntosFigura, int tipoFigura) {
        switch (tipoFigura) {
            case LINEA:
                return puntoEstaEnLinea(punto, puntosFigura.get(0), puntosFigura.get(1));
            case TRIANGULO:
                return puntoEstaEnTriangulo(punto, puntosFigura.get(0), puntosFigura.get(1), puntosFigura.get(2));
            case CUADRADO:
                return puntoEstaEnCuadrado(punto, puntosFigura.get(0), puntosFigura.get(1),
                        puntosFigura.get(2), puntosFigura.get(3));
            case CIRCULO:
                return puntoEstaEnCirculo(punto, puntosFigura.get(0), puntosFigura.get(1));
            default:
                return false;
        }
    }

    private boolean puntoEstaEnLinea(Point punto, Point inicio, Point fin) {
        // Calcula la distancia del punto a la línea utilizando la fórmula de distancia punto a línea
        double distancia = Math.abs((fin.y - inicio.y) * punto.x - (fin.x - inicio.x) * punto.y
                + fin.x * inicio.y - fin.y * inicio.x) / Math.sqrt(Math.pow(fin.y - inicio.y, 2) + Math.pow(fin.x - inicio.x, 2));
        // Define un umbral para la proximidad del punto a la línea
        double umbral = 5.0; // Puedes ajustar este valor según tus necesidades
        return distancia < umbral;
    }

    private boolean puntoEstaEnTriangulo(Point punto, Point p1, Point p2, Point p3) {
        // Calcula las áreas de tres subtriángulos formados por el punto y los vértices del triángulo
        double areaTotal = calcularAreaTriangulo(p1, p2, p3);
        double area1 = calcularAreaTriangulo(punto, p2, p3);
        double area2 = calcularAreaTriangulo(p1, punto, p3);
        double area3 = calcularAreaTriangulo(p1, p2, punto);
        // Si la suma de las áreas de los subtriángulos es igual al área total, el punto está dentro del triángulo
        return areaTotal == area1 + area2 + area3;
    }
    
    private double calcularAreaTriangulo(Point p1, Point p2, Point p3) {
        return 0.5 * Math.abs(p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y));
    }

    private boolean puntoEstaEnCuadrado(Point punto, Point p1, Point p2, Point p3, Point p4) {
        // Verifica si el punto está dentro del rectángulo delimitado por los cuatro vértices
        return punto.x >= Math.min(p1.x, Math.min(p2.x, Math.min(p3.x, p4.x)))
                && punto.x <= Math.max(p1.x, Math.max(p2.x, Math.max(p3.x, p4.x)))
                && punto.y >= Math.min(p1.y, Math.min(p2.y, Math.min(p3.y, p4.y)))
                && punto.y <= Math.max(p1.y, Math.max(p2.y, Math.max(p3.y, p4.y)));
    }

    private boolean puntoEstaEnCirculo(Point punto, Point centro, Point puntoEnRadio) {
        // Calcula la distancia entre el punto y el centro del círculo
        double distancia = punto.distance(centro);
        // Calcula la distancia entre el punto en el radio y el centro del círculo
        double radio = centro.distance(puntoEnRadio);
        // Verifica si la distancia al punto es menor o igual al radio del círculo
        return distancia <= radio;
    }
    
    private Point obtenerCentroFigura(Figura figura) {
        int sumX = 0;
        int sumY = 0;

        for (Point punto : figura.puntos) {
            sumX += punto.x;
            sumY += punto.y;
        }

        int centroX = sumX / figura.puntos.size();
        int centroY = sumY / figura.puntos.size();

        return new Point(centroX, centroY);
    }

    private void aplicarTransformacion(String tipoTransformacion, double... parametros) {
        // Obtiene las figuras seleccionadas
        List<Figura> figurasSeleccionadas = obtenerFigurasSeleccionadas();

        // Verifica que haya al menos una figura seleccionada
        if (figurasSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay figuras seleccionadas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtiene el centro de la última figura seleccionada
        Figura ultimaFigura = figurasSeleccionadas.get(figurasSeleccionadas.size() - 1);
        Point centro = obtenerCentroFigura(ultimaFigura);

        // Aplica la transformación a la última figura seleccionada
        for (Point punto : ultimaFigura.puntos) {
            switch (tipoTransformacion) {
                case "Traslacion":
                    // Aplica traslación
                    punto.x += (int) parametros[0];
                    punto.y += (int) parametros[1];
                    break;
                case "Rotacion":
                    // Aplica rotación alrededor del centro
                    double anguloRad = Math.toRadians(parametros[0]);
                    double x = punto.x - centro.x;
                    double y = punto.y - centro.y;
                    punto.x = (int) (centro.x + x * Math.cos(anguloRad) - y * Math.sin(anguloRad));
                    punto.y = (int) (centro.y + x * Math.sin(anguloRad) + y * Math.cos(anguloRad));
                    break;
                case "Escala":
                    // Aplica escalamiento desde el centro
                    punto.x = (int) (centro.x + (punto.x - centro.x) * parametros[0]);
                    punto.y = (int) (centro.y + (punto.y - centro.y) * parametros[1]);
                    break;
            }
        }

        // Vuelve a dibujar las figuras con las coordenadas transformadas
        redibujarFiguras();

        // Reinicia la matriz de transformación
        matrizTransformacion.matrizIdentidad();
    }

    private Point obtenerCentroFiguraDespuesTransformacion(Figura figura) {
        int sumX = 0;
        int sumY = 0;

        for (Point punto : figura.puntos) {
            sumX += punto.x;
            sumY += punto.y;
        }

        int centroX = sumX / figura.puntos.size();
        int centroY = sumY / figura.puntos.size();

        return new Point(centroX, centroY);
    }

    private void dibujarFigura(Figura figura) {
        Color figuraColor;

        if (puntoEstaDentroFigura(figura.puntos.get(0), figura.puntos, figura.tipo)) {
            figuraColor = Color.red;
        } else {
            figuraColor = Color.black;
        }

        switch (figura.tipo) {
            case LINEA:
                dibujarLinea(figura.puntos.get(0), figura.puntos.get(1), figuraColor);
                break;
            case TRIANGULO:
                dibujarTriangulo(figura.puntos.get(0), figura.puntos.get(1), figura.puntos.get(2), figuraColor);
                break;
            case CUADRADO:
                dibujarCuadrado(figura.puntos.get(0), figura.puntos.get(1), figura.puntos.get(2), figura.puntos.get(3), figuraColor);
                break;
            case CIRCULO:
                dibujarCirculo(figura.puntos.get(0), figura.puntos.get(1), figuraColor);
                break;
        }
    }

    private void redibujarFiguras() {
        limpiarPizarra();

        for (Figura figura : figuras) {
            dibujarFigura(figura);
        }
    }

    // Método para obtener un valor entero del usuario
    private int obtenerValorNumerico(String mensaje) {
        String valor = JOptionPane.showInputDialog(null, mensaje);
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return 0; // Valor por defecto
        }
    }

// Método para obtener un valor decimal del usuario
    private double obtenerValorNumericoDecimal(String mensaje) {
        String valor = JOptionPane.showInputDialog(null, mensaje);
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return 1.0; // Valor por defecto
        }
    }
    
    private void imprimirCoordenadas(Figura figura) {
        System.out.println("Coordenadas de la figura seleccionada:");
        for (Point punto : figura.puntos) {
            System.out.println("(" + punto.x + ", " + punto.y + ")");
        }
    }
    
    // Clase para representar una figura
    class Figura {

        int tipo;
        List<Point> puntos;

        public Figura(int tipo, List<Point> puntos) {
            this.tipo = tipo;
            this.puntos = puntos;
        }
    }
}