import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Configurador config = new Configurador(args[0]);
        int dimension = config.getDimension();
        //   System.out.println("la dimension es " + dimension);
        int iteraciones = config.getIteraciones();
        //   System.out.println("las iteraciones son " +iteraciones);
        float probatilidad = config.getProbabilidad();
        //   System.out.println("la probabilidad es de " + probatilidad);
        float porcentaje = config.getPorcentajeAleatorio();
        //  System.out.println("el porcentajes es " + porcentaje);
//        int tam=config.getFuncion().size();
//      //  String [] funciones= config.getFuncion();
//        for (int i = 0; i < config.getFuncion().size(); i++) {
//            System.out.println(config.getFuncion().get(i));
//        }
//       // Integer [] minimo= config.getMinimos();
//        for (int i = 0; i < config.getMinimos().size(); i++) {
//            System.out.println(config.getMinimos().get(i));
//        }
//
//     //   Integer [] maximo= config.getMaximos();
//        for (int i = 0; i < config.getMaximos().size(); i++) {
//            System.out.println(config.getMaximos().get(i));
//        }
//
//      //  Long [] semilla= config.getSemillas();
//        for (int i = 0; i < config.getSemillas().size(); i++) {
//            System.out.println(config.getSemillas().get(i));
//        }
//
//       // Integer [] algoritmo=config.getAlgoritmos();
//        for (int i = 0; i < config.getAlgoritmos().size(); i++) {
//            System.out.println(config.getAlgoritmos().get(i));
//        }
//
//
//            BusquedaLocal bl=new BusquedaLocal(77367663);

        //  bl.busquedalocal(1000, 0.3,0.1f,-1,10,-33,33,"ackley");


//        for (int j = 0; j < config.getFuncion().size(); j++) {
//            for (int k = 0; k < config.getSemillas().size(); k++) {
//                for (int i = 0; i < config.getAlgoritmos().size(); i++) {
//                    String fichero = "logs/" + config.getAlgoritmos().get(i) + "_" + config.getFuncion().get(j) + "_" + config.getSemillas().get(k) + ".txt";
//                    BusquedaLocal bl = new BusquedaLocal(config.getSemillas().get(k));
//                    bl.busquedalocal(iteraciones, probatilidad, porcentaje, config.getAlgoritmos().get(i), dimension, config.getMinimos().get(j), config.getMaximos().get(j), config.getFuncion().get(j));
//                   System.out.println(bl.getLog());
//                    createLog(fichero, bl.getLog());
//                }
//            }
//
//        }
double [][] aa= new double[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
            aa[i][j]=100-j;
        }}
double [][] prueba= {{11,12,13,14,15,16,17,18,19,20},{21,22,23,24,25,26,27,28,29,30},{1,2,3,4,5,6,7,8,9,10},{31,32,33,34,35,36,37,38,39,40}};

        BusquedaTabu bt = new BusquedaTabu(77367663);
        System.out.println(bt.evaluacion(prueba[0],"ackley") + "\n");
        System.out.println(bt.evaluacion(prueba[1],"ackley") + "\n");
        System.out.println(bt.evaluacion(prueba[2],"ackley") + "\n");
        System.out.println(bt.evaluacion(prueba[3],"ackley"));

        bt.ordenaVecinos(prueba,"ackley");
        for (int j = 0; j < prueba.length; j++) {
            for (int i = 0; i < 10; i++) {
                System.out.println(prueba[j][i]);

            }
            System.out.println(bt.evaluacion(prueba[j], "ackley"));
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