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
public class Rosenbrock {

    public double evaluacion(double[] solucion, int dimension) {
        double sum = 0.0;
        double sol = 0.0;
        for (int i = 0; i < dimension - 1; i++) {
            sum += (100 * Math.pow((solucion[i + 1] - (Math.pow(solucion[i], 2))), 2) + Math.pow((solucion[i] - 1), 2));
        }
        sol = sum;
        return sol;
    }
}
