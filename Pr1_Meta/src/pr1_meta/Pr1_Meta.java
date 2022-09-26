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
public class Pr1_Meta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Configurador config = new Configurador(args[0]);
        int dimension= config.getDimension();
        int rango_inferior= config.getRango_inferior();
        int rango_superior= config.getRango_superior();
        System.out.println(config.getArchivos());
        
    
    }
    
}
