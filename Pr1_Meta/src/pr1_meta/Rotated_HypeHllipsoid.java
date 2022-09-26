/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pr1_meta;

/**
 *
 * @author USUARIO
 */
public class Rotated_HypeHllipsoid {

    public double evaluacion(double[] solucion, int dimension) {
        double sum1 = 0.0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < solucion.length; j++) {
                sum1 += Math.pow(solucion[j], 2);
            }
        }
        return sum1;
    }
}
