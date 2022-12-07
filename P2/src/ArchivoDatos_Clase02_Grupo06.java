
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoDatos_Clase02_Grupo06 {
    private String nombreFichero;
    // private double[][] matriz;
    private ArrayList<double[]> matriz;


    public ArchivoDatos_Clase02_Grupo06(String ruta) {
        nombreFichero = ruta;
        String linea;
        FileReader f = null;
        try {
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            linea = b.readLine();
            String[] split = linea.split(",");
            // matriz = new double[6252][6];
            matriz = new ArrayList<>();
            boolean loop = true;
            int contador = 0;
            while (loop) {
                linea = b.readLine();
                if (linea != null) {
                    split = linea.split(",");
                    double[] aux = new double[split.length];
                    for (int i = 0; i < split.length; i++) {
                        //matriz[contador][i]= Double.parseDouble(split[i]);
                        aux[i] = Double.parseDouble(split[i]);
                    }
                    matriz.add(contador, aux);
                    contador++;
                } else {
                    loop = false;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public ArrayList<double[]> getMatriz() {
        return matriz;
    }

    public double getMatrizelemento(int fila, int columna) {
        return matriz.get(fila)[columna];
    }

    public void setMatriz(ArrayList<double[]> matriz) {
        this.matriz = matriz;
    }

}
