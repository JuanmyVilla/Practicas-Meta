/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prac1_alex;

/**
 *
 * @author USUARIO
 */
public class Prac1_Alex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Configurador config = new Configurador(args[0]);
        int dimension = config.getDimension();
        int rango_inferior = config.getRango_inferior();
        int rango_superior = config.getRango_superior();
        BusquedaLocal bl = new BusquedaLocal(77367663);

        bl.busquedalocal(1000, 0.3, 0.1f, -1, 10, -33, 33, "ackley");

    }

}
