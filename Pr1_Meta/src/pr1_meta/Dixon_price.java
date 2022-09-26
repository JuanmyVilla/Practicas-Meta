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
public class Dixon_price {

    public double evaluacion(double[] solucion, int dimension) {
        double sum = 0.0;
        double sol = 0.0;
        for (int i = 0; i < dimension; i++) {
            sum += i * Math.pow((Math.pow(2*solucion[i],2)-solucion[i-1]),2);
        }
        sol = Math.pow((solucion[1] - 1),2) + sum ;
        return sol;
    }
}
