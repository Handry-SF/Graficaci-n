package pizarra2d;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author LaboratorioU005_11
 */
// Nueva clase para representar una figura
abstract class Figura {

    Color color;

    Figura(Color color) {
        this.color = color;
    }
}

// Clases específicas para cada tipo de figura
class Linea extends Figura {

    Point inicio;
    Point fin;

    Linea(Point inicio, Point fin, Color color) {
        super(color);
        this.inicio = inicio;
        this.fin = fin;
    }
}

class Triangulo extends Figura {

    Point p1;
    Point p2;
    Point p3;

    Triangulo(Point p1, Point p2, Point p3, Color color) {
        super(color);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
}

class Cuadrado extends Figura {

    Point p1;
    Point p2;
    Point p3;
    Point p4;

    Cuadrado(Point p1, Point p2, Point p3, Point p4, Color color) {
        super(color);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }
}

class Circulo extends Figura {

    Point centro;
    Point puntoEnRadio;

    Circulo(Point centro, Point puntoEnRadio, Color color) {
        super(color);
        this.centro = centro;
        this.puntoEnRadio = puntoEnRadio;
    }
}
