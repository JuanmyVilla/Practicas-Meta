
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoDatos {

    private String nombreFichero;
    private double[][] matriz;

    private ArrayList<Double> matriz2;
    public ArchivoDatos(String ruta) {
        nombreFichero = ruta;
        String linea;
        FileReader f = null;
        try {
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            linea = b.readLine();
            String[] split = linea.split(",");
            matriz = new double[6252][6];
            boolean loop = true;
            int contador=0;
            while (loop) {
                linea = b.readLine();
                if (linea != null) {
                    split = linea.split(",");
                    for (int i = 0; i < split.length; i++) {
                        matriz[contador][i]= Double.parseDouble(split[i]);
                    }
                    contador++;
                } else {
                    loop = false;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String getNombreFichero() { return nombreFichero; }
    public double[][] getMatriz() { return matriz; }

    public void setNombreFichero(String nombreFichero) { this.nombreFichero = nombreFichero; }
    public void setMatriz(double[][] matriz) { this.matriz = matriz; }

}
