import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Configurador_Clase02_Grupo06 {

    private ArrayList<String> funcion;
    private ArrayList<String> algoritmos;
    private ArrayList<Long> semillas;
    private float minimos;
    private float maximos;
    private int dimension;
    private int evaluaciones;
    private int poblacion;
    private float probabilidadcruce;
    private float probabilidadmutacion;
    private int tamanotorneo;
    private float alfa;
    private ArrayList<String> selectorMAPE;
    private ArrayList<String> selectorcruce;
    private float factorRecombinacion;
    private String rutaficheros;


    public Configurador_Clase02_Grupo06(String ruta) {
        funcion = new ArrayList<>();
        algoritmos = new ArrayList<>();
        semillas = new ArrayList<>();
        selectorcruce = new ArrayList<>();
        selectorMAPE = new ArrayList<>();
        String linea;
        FileReader f = null;
        try {
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            while ((linea = b.readLine()) != null) {
                String[] split = linea.split("=");
                switch (split[0]) {
                    case "ruta":
                        rutaficheros = split[1];
                        break;
                    case "funcion":
                        String[] _funcion = split[1].split(",");
                        for (int i = 0; i < _funcion.length; i++) {
                            funcion.add(_funcion[i]);
                        }
                        break;
                    case "dimension":
                        dimension = Integer.parseInt(split[1]);
                        break;
                    case "evaluaciones":
                        evaluaciones = Integer.parseInt(split[1]);
                        break;
                    case "poblacion":
                        poblacion = Integer.parseInt(split[1]);
                        break;
                    case "probabilidadcruce":
                        probabilidadcruce = Float.parseFloat(split[1]);
                        break;
                    case "probabilidadmutacion":
                        probabilidadmutacion = Float.parseFloat(split[1]);
                        break;
                    case "tamanotorneo":
                        tamanotorneo = Integer.parseInt(split[1]);
                        break;
                    case "alfa":
                        alfa = Float.parseFloat((split[1]));
                        break;
                    case "selectorMAPE":
                        String[] _selectorMAPE = split[1].split(",");
                        for (int i = 0; i < _selectorMAPE.length; i++) {
                            selectorMAPE.add(_selectorMAPE[i]);
                        }
                        break;
                    case "selectorcruce":
                        String[] _selectorcruce = split[1].split(",");
                        for (int i = 0; i < _selectorcruce.length; i++) {
                            selectorcruce.add(_selectorcruce[i]);
                        }
                        break;
                    case "factorRecombinacion":
                        factorRecombinacion = Float.parseFloat((split[1]));
                        break;
                    case "semilla":
                        String[] _semillas = split[1].split(",");
                        for (int i = 0; i < _semillas.length; i++) {
                            semillas.add(Long.parseLong(_semillas[i]));
                        }
                        break;
                    case "algoritmos":
                        String[] _algoritmos = split[1].split(",");
                        for (int i = 0; i < _algoritmos.length; i++) {
                            algoritmos.add((_algoritmos[i]));
                        }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void leefuncion(int i) {
        String concatena = rutaficheros + funcion.get(i) + ".txt";
        String linea;
        FileReader f = null;
        try {
            f = new FileReader(concatena);
            BufferedReader b = new BufferedReader(f);
            while ((linea = b.readLine()) != null) {
                String[] split = linea.split("=");
                switch (split[0]) {
                    case "minimo":
                        minimos = Float.parseFloat(split[1]);
                        break;
                    case "maximo":
                        maximos = (Float.valueOf(split[1]));
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> getAlgoritmos() {
        return algoritmos;
    }

    public void setAlgoritmos(ArrayList<String> algoritmos) {
        this.algoritmos = algoritmos;
    }

    public ArrayList<Long> getSemillas() {
        return semillas;
    }

    public void setSemillas(ArrayList<Long> semillas) {
        this.semillas = semillas;
    }

    public ArrayList<String> getFuncion() {
        return funcion;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public float getAlfa() {
        return alfa;
    }

    public void setAlfa(float alfa) {
        this.alfa = alfa;
    }

    public ArrayList<String> getSelectorMAPE() {
        return selectorMAPE;
    }

    public void setSelectorMAPE(ArrayList<String> selectorMAPE) {
        this.selectorMAPE = selectorMAPE;
    }

    public ArrayList<String> getSelectorcruce() {
        return selectorcruce;
    }

    public void setSelectorcruce(ArrayList<String> selectorcruce) {
        this.selectorcruce = selectorcruce;
    }

    public float getFactorRecombinacion() {
        return factorRecombinacion;
    }

    public void setFactorRecombinacion(float factorRecombinacion) {
        this.factorRecombinacion = factorRecombinacion;
    }

    public void setFuncion(ArrayList<String> funcion) {
        this.funcion = funcion;
    }

    public float getMinimos() {
        return minimos;
    }

    public void setMinimos(float minimos) {
        this.minimos = minimos;
    }

    public float getMaximos() {
        return maximos;
    }

    public void setMaximos(float maximos) {
        this.maximos = maximos;
    }

    public String getRutaficheros() {
        return rutaficheros;
    }

    public void setRutaficheros(String rutaficheros) {
        this.rutaficheros = rutaficheros;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(int evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public float getProbabilidadcruce() {
        return probabilidadcruce;
    }

    public void setProbabilidadcruce(float probabilidadcruce) {
        this.probabilidadcruce = probabilidadcruce;
    }

    public float getProbabilidadmutacion() {
        return probabilidadmutacion;
    }

    public void setProbabilidadmutacion(float probabilidadmutacion) {
        this.probabilidadmutacion = probabilidadmutacion;
    }

    public int getTamanotorneo() {
        return tamanotorneo;
    }

    public void setTamanotorneo(int tamanotorneo) {
        this.tamanotorneo = tamanotorneo;
    }
}
