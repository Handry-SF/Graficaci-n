package pizarra2d;

/**
 *
 * @author LENOVO
 */

public class MatricesTransformacion {
    double[][] matrizGenerada = new double[3][3];
    double puntoX, puntoY, puntoH;

    MatricesTransformacion() {
        // Matriz Identidad
        matrizIdentidad();
    }

    double[] multiGenerada(double[] resMultiplica) {
        double[] resul = new double[3];

        puntoX = resMultiplica[0] * matrizGenerada[0][0] + resMultiplica[1] * matrizGenerada[0][1] + resMultiplica[2] * matrizGenerada[0][2];
        puntoY = resMultiplica[0] * matrizGenerada[1][0] + resMultiplica[1] * matrizGenerada[1][1] + resMultiplica[2] * matrizGenerada[1][2];
        puntoH = resMultiplica[0] * matrizGenerada[2][0] + resMultiplica[1] * matrizGenerada[2][1] + resMultiplica[2] * matrizGenerada[2][2];

        resul[0] = puntoX;
        resul[1] = puntoY;
        resul[2] = puntoH;

        return resul;
    }

    double[][] resMultiplicaMatrices(double[][] matrizMulti, double[][] matrizN) {
        double[][] matrizResultante = new double[3][3];

        matrizResultante[0][0] = matrizN[0][0] * matrizMulti[0][0] + matrizN[1][0] * matrizMulti[0][1] + matrizN[2][0] * matrizMulti[0][2];
        matrizResultante[1][0] = matrizN[0][0] * matrizMulti[1][0] + matrizN[1][0] * matrizMulti[1][1] + matrizN[2][0] * matrizMulti[1][2];
        matrizResultante[2][0] = matrizN[0][0] * matrizMulti[2][0] + matrizN[1][0] * matrizMulti[2][1] + matrizN[2][0] * matrizMulti[2][2];

        matrizResultante[0][1] = matrizN[0][1] * matrizMulti[0][0] + matrizN[1][1] * matrizMulti[0][1] + matrizN[2][1] * matrizMulti[0][2];
        matrizResultante[1][1] = matrizN[0][1] * matrizMulti[1][0] + matrizN[1][1] * matrizMulti[1][1] + matrizN[2][1] * matrizMulti[1][2];
        matrizResultante[2][1] = matrizN[0][1] * matrizMulti[2][0] + matrizN[1][1] * matrizMulti[2][1] + matrizN[2][1] * matrizMulti[2][2];

        matrizResultante[0][2] = matrizN[0][2] * matrizMulti[0][0] + matrizN[1][2] * matrizMulti[0][1] + matrizN[2][2] * matrizMulti[0][2];
        matrizResultante[1][2] = matrizN[0][2] * matrizMulti[1][0] + matrizN[1][2] * matrizMulti[1][1] + matrizN[2][2] * matrizMulti[1][2];
        matrizResultante[2][2] = matrizN[0][2] * matrizMulti[2][0] + matrizN[1][2] * matrizMulti[2][1] + matrizN[2][2] * matrizMulti[2][2];

        return matrizResultante;
    }

    void matrizIdentidad() {
        matrizGenerada[0][0] = 1;
        matrizGenerada[0][1] = 0;
        matrizGenerada[0][2] = 0;
        matrizGenerada[1][0] = 0;
        matrizGenerada[1][1] = 1;
        matrizGenerada[1][2] = 0;
        matrizGenerada[2][0] = 0;
        matrizGenerada[2][1] = 0;
        matrizGenerada[2][2] = 1;
    }
    
    void matrizTraslacion(double tx, double ty) {
        double[][] matrizTraslacion = {
            {1, 0, tx},
            {0, 1, ty},
            {0, 0, 1}
        };
        matrizGenerada = resMultiplicaMatrices(matrizTraslacion, matrizGenerada);
    }
    
    void matrizRotacion(double angulo) {
        double radianes = Math.toRadians(angulo);
        double coseno = Math.cos(radianes);
        double seno = Math.sin(radianes);

        double[][] matrizRotacion = {
            {coseno, -seno, 0},
            {seno, coseno, 0},
            {0, 0, 1}
        };
        matrizGenerada = resMultiplicaMatrices(matrizRotacion, matrizGenerada);
    }

    void matrizEscalamiento(double escala) {
        double[][] matrizEscala = {
            {escala, 0, 0},
            {0, escala, 0},
            {0, 0, 1}
        };
        matrizGenerada = resMultiplicaMatrices(matrizEscala, matrizGenerada);
    }

    void matrizEscalamientoXY(double escalaX, double escalaY) {
        double[][] matrizEscala = {
            {escalaX, 0, 0},
            {0, escalaY, 0},
            {0, 0, 1}
        };
        matrizGenerada = resMultiplicaMatrices(matrizEscala, matrizGenerada);
    }
}