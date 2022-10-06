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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Configurador {

//    private ArrayList<String> archivos;
//    private ArrayList<String> algoritmos;
//    private ArrayList<Long> semillas;
//    private int numerocromosomas;
//    private int cromosomasaleatorios;
//    private float probabilidadmutacion;
//    private float probabilidadcruce;
//    private int numevaluaciones;
//    private int numiteraciones;
//    private int busquedalocal;
    private int dimension;
    private int rango_inferior;
    private int rango_superior;



    public Configurador(String ruta) {
//        archivos = new ArrayList<>();
//        algoritmos = new ArrayList<>();
//        semillas = new ArrayList<>();

        String linea;
        FileReader f = null;
        try {
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            while ((linea = b.readLine()) != null) {
                String[] split = linea.split("=");
                switch (split[0]) {
                    case "dimension":
                        dimension = Integer.parseInt(split[1]);
                        break;
                    case "rango_inferior":
                        rango_inferior = Integer.parseInt(split[1]);
                        break;
                    case "rango_superior":
                        rango_superior = Integer.parseInt(split[1]);
                        break;
//                    case "archivos":
//                        String[] _archivos = split[1].split(",");
//                        for (int i = 0; i < _archivos.length; i++) {
//                            archivos.add(_archivos[i]);
//                        }
//                        break;
//                    case "algoritmos":
//                        String[] _algoritmos = split[1].split(",");
//                        for (int i = 0; i < _algoritmos.length; i++) {
//                            algoritmos.add(_algoritmos[i]);
//                        }
//                        break;
//                    case "semillas":
//                        String[] _semillas = split[1].split(",");
//                        for (int i = 0; i < _semillas.length; i++) {
//                            semillas.add(Long.parseLong(_semillas[i]));
//                        }
//                        break;
//                    case "numerocromosomas":
//                        numerocromosomas = Integer.parseInt(split[1]);
//                        break;
//                    case "cromosomasaleatorios":
//                        cromosomasaleatorios = Integer.parseInt(split[1]);
//                        break;
//                    case "probabilidadmutacion":
//                        probabilidadmutacion = Float.parseFloat(split[1]);
//                        break;
//                    case "probabilidadcruce":
//                        probabilidadcruce = Float.parseFloat(split[1]);
//                        break;
//                    case "numevaluaciones":
//                        numevaluaciones = Integer.parseInt(split[1]);
//                        break;
//                    case "numiteraciones":
//                        numiteraciones = Integer.parseInt(split[1]);
//                        break;
//                    case "busquedalocal":
//                        busquedalocal = Integer.parseInt(split[1]);
//                        break;

                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getRango_inferior() {
        return rango_inferior;
    }

    public void setRango_inferior(int rango_inferior) {
        this.rango_inferior = rango_inferior;
    }

    public int getRango_superior() {
        return rango_superior;
    }

    public void setRango_superior(int rango_superior) {
        this.rango_superior = rango_superior;
    }
}
