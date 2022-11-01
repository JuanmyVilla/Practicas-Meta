import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Configurador {

    private ArrayList<String> funcion;
    private ArrayList<String> algoritmos;

    private ArrayList<Long> semillas;
    private ArrayList<Float> minimos;
    private ArrayList<Float> maximos;
    private int dimension;
    private int iteraciones;

    private float probabilidad;

    private float porcentajeAleatorio;



    public Configurador(String ruta) {
        funcion = new ArrayList<>();
        algoritmos=new ArrayList<>();
        minimos=new ArrayList<>();
        maximos=new ArrayList<>();
        semillas=new ArrayList<>();


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
                    case "iteraciones":
                        iteraciones = Integer.parseInt(split[1]);
                        break;
                    case "probabilidad":
                        probabilidad = Float.parseFloat(split[1]);
                        break;
                    case "porcentajealeatorio":
                        porcentajeAleatorio = Float.parseFloat(split[1]);
                        break;
                    case "funcion":
                        String[] _funcion = split[1].split(",");
                        for (int i = 0; i < _funcion.length; i++) {
                            funcion.add(_funcion[i]);
                        }
                        break;
                    case "minimo":
                        String[] _minimo = split[1].split(",");
                        for (int i = 0; i < _minimo.length; i++) {
                            minimos.add(Float.valueOf(_minimo[i]));
                        }
                        break;
                    case "maximo":
                        String[] _maximo = split[1].split(",");
                        for (int i = 0; i < _maximo.length; i++) {
                            maximos.add(Float.valueOf(_maximo[i]));
                        }
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
                        break;


                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> getFuncion() {
        return funcion;
    }

    public void setFuncion(ArrayList<String> funcion) {
        this.funcion = funcion;
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

    public ArrayList<Float> getMinimos() {
        return minimos;
    }

    public void setMinimos(ArrayList<Float> minimos) {
        this.minimos = minimos;
    }

    public ArrayList<Float> getMaximos() {
        return maximos;
    }

    public void setMaximos(ArrayList<Float> maximos) {
        this.maximos = maximos;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

    public float getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(float probabilidad) {
        this.probabilidad = probabilidad;
    }

    public float getPorcentajeAleatorio() {
        return porcentajeAleatorio;
    }

    public void setPorcentajeAleatorio(float porcentajeAleatorio) {
        this.porcentajeAleatorio = porcentajeAleatorio;
    }

}
