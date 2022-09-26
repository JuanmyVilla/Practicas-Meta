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
public class Trid {
    public double evaluacion(double [] solucion,int dimension){
        double sum=0.0;
        double sum1=0.0;
        double sol=0.0;
        for (int i = 0; i < dimension; i++) {
            sum+= Math.pow(solucion[i] -1 ,2);
            sum1 += solucion[i]*solucion[i-1];
            
        }
        sol=sum - sum1;
        return sol;
    }
}
