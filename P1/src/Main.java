import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Configurador config = new Configurador(args[0]);
        int dimension = config.getDimension();
        int iteraciones = config.getIteraciones();
        float probatilidad = config.getProbabilidad();
        float porcentaje = config.getPorcentajeAleatorio();
        int selector= config.getSelector();
        for (int j = 0; j < config.getFuncion().size(); j++) {
            for (int k = 0; k < config.getSemillas().size(); k++) {
                for (int i = 0; i < config.getAlgoritmos().size(); i++) {
                    String fichero = "logs/" + config.getAlgoritmos().get(i) + "_" + config.getFuncion().get(j) + "_" + config.getSemillas().get(k) + ".txt";

                    switch ((config.getAlgoritmos().get(i))) {
                        case "bl3":
                            BusquedaLocal bl = new BusquedaLocal(config.getSemillas().get(k));
                            bl.busquedalocal(iteraciones, probatilidad, porcentaje, 3, dimension, config.getMinimos().get(j), config.getMaximos().get(j), config.getFuncion().get(j));
                            System.out.println(bl.getLog());
                            createLog(fichero, bl.getLog());
                            break;
                        case "blk":
                            BusquedaLocal blk = new BusquedaLocal(config.getSemillas().get(k));
                            blk.busquedalocal(iteraciones, probatilidad, porcentaje, 0, dimension, config.getMinimos().get(j), config.getMaximos().get(j), config.getFuncion().get(j));
                            System.out.println(blk.getLog());
                            createLog(fichero, blk.getLog());
                            break;
                        case "tabu":
                            BusquedaTabu bt = new BusquedaTabu(config.getSemillas().get(k));
                            bt.busquedatabu(iteraciones, probatilidad, porcentaje, selector, dimension, config.getMinimos().get(j), config.getMaximos().get(j), config.getFuncion().get(j), 5, 10);
                            System.out.println(bt.getLog());
                            createLog(fichero, bt.getLog());
                            break;
                        case "vns":
                            Multiarranque mt = new Multiarranque(config.getSemillas().get(k));
                            mt.multiarranque(iteraciones, probatilidad, porcentaje, selector, dimension, config.getMinimos().get(j), config.getMaximos().get(j), config.getFuncion().get(j), 5, 10);
                            System.out.println(mt.getLog());
                            createLog(fichero, mt.getLog());
                            break;
                    }
                }
            }
        }
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