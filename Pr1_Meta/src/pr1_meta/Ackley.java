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
public class Ackley {

    private int a=20;
    private double b= 0.2;
    private double c= 2*Math.PI;

    public double evaluacion(double [] solucion,int dimension){
        double score;
        double sum1=0.0;
        double sum2=0.0;

        for (int i = 0; i < dimension; i++) {
            sum1 += Math.pow(solucion[i],2);
            sum2 += Math.cos(c*solucion[i]);
        }

        score= -20*Math.exp(-b * Math.sqrt((1.0/dimension)*sum1)) - Math.exp((1.0/dimension)*sum2) + a + Math.exp(1);

        return score;
    }
}