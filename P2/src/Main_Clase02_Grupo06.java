import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main_Clase02_Grupo06 {
    public static void main(String[] args) {
        ArchivoDatos_Clase02_Grupo06 datos = new ArchivoDatos_Clase02_Grupo06("daido-tra.dat");
        Configurador_Clase02_Grupo06 config = new Configurador_Clase02_Grupo06(args[0]);
       // for (int j = 0; j < config.getFuncion().size(); j++) {
            config.leefuncion(10);
            for (int k = 0; k < config.getSemillas().size(); k++) {
                for (int i = 0; i < config.getAlgoritmos().size(); i++) {
                    for (int l = 0; l < config.getSelectorMAPE().size(); l++) {
                        String fichero = nombrelogs(config, i, 10, k, l);
                        switch ((config.getAlgoritmos().get(i))) {
                            case "evolutivoMedia":
                                AlgoritmoEvolutivo_Clase02_Grupo06 algoritmoEvolutivoMedia = new AlgoritmoEvolutivo_Clase02_Grupo06(config.getSemillas().get(k));
                                algoritmoEvolutivoMedia.Evolutivo(config.getEvaluaciones(), config.getPoblacion(), config.getProbabilidadcruce(), config.getProbabilidadmutacion(), config.getDimension(), config.getTamanotorneo(), config.getMinimos(), config.getMaximos(), config.getFuncion().get(10), config.getSelectorcruce().get(0), config.getAlfa(), config.getSelectorMAPE().get(l), datos.getMatriz());
                                System.out.println(algoritmoEvolutivoMedia.getLog());
                                createLog(fichero, algoritmoEvolutivoMedia.getLog());
                                break;
                            case "evolutivoBLX":
                                AlgoritmoEvolutivo_Clase02_Grupo06 algoritmoEvolutivo = new AlgoritmoEvolutivo_Clase02_Grupo06(config.getSemillas().get(k));
                                algoritmoEvolutivo.Evolutivo(config.getEvaluaciones(), config.getPoblacion(), config.getProbabilidadcruce(), config.getProbabilidadmutacion(), config.getDimension(), config.getTamanotorneo(), config.getMinimos(), config.getMaximos(), config.getFuncion().get(10), config.getSelectorcruce().get(1), config.getAlfa(), config.getSelectorMAPE().get(l), datos.getMatriz());
                                System.out.println(algoritmoEvolutivo.getLog());
                                createLog(fichero, algoritmoEvolutivo.getLog());
                                break;
                            case "diferencial":
                                AlgoritmoDiferencial_Clase02_Grupo06 algoritmoDiferencial = new AlgoritmoDiferencial_Clase02_Grupo06(config.getSemillas().get(k));
                                algoritmoDiferencial.EvolucionDiferencial(config.getEvaluaciones(), config.getPoblacion(), config.getDimension(), config.getMinimos(), config.getMaximos(), config.getFuncion().get(10), config.getFactorRecombinacion(), config.getSelectorMAPE().get(l), datos.getMatriz());
                                System.out.println(algoritmoDiferencial.getLog());
                                createLog(fichero, algoritmoDiferencial.getLog());
                                break;
                        }
                    }
                }
            }
        //}
    }

    public static String nombrelogs(Configurador_Clase02_Grupo06 config, int algoritmo, int funcion, int semilla, int mape) {
        String solucion = "";
        if (config.getFuncion().get(funcion).equals("potencia")) {
            solucion = "logs/" + config.getSelectorMAPE().get(mape) + "_" + config.getAlgoritmos().get(algoritmo) + "_" + config.getSemillas().get(semilla) + ".txt";
            return solucion;
        }
        solucion = "logs/" + config.getAlgoritmos().get(algoritmo) + "_" + config.getFuncion().get(funcion) + "_" + config.getSemillas().get(semilla) + ".txt";

        return solucion;
    }

    public static void createLog(String fichero, String texto) {
        try {
            File file = new File(fichero);
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            pw.println(texto);
            pw.close();
            System.out.println("Log escrito con exito.");
        } catch (IOException e) {
            System.out.println("No se pudo escribir el log.");
        }
    }
}