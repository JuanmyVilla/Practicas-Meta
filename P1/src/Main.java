public class Main {
    public static void main(String[] args) {
        Configurador config = new Configurador(args[0]);
        int dimension= config.getDimension();
        System.out.println("la dimension es " + dimension);
        int iteraciones=config.getIteraciones();
        System.out.println("las iteraciones son " +iteraciones);
        float probatilidad= config.getProbabilidad();
        System.out.println("la probabilidad es de " + probatilidad);
        float porcentaje= config.getPorcentajeAleatorio();
        System.out.println("el porcentajes es " + porcentaje);
        int tam=config.getFuncion().size();
      //  String [] funciones= config.getFuncion();
        for (int i = 0; i < config.getFuncion().size(); i++) {
            System.out.println(config.getFuncion().get(i));
        }
       // Integer [] minimo= config.getMinimos();
        for (int i = 0; i < config.getMinimos().size(); i++) {
            System.out.println(config.getMinimos().get(i));
        }

     //   Integer [] maximo= config.getMaximos();
        for (int i = 0; i < config.getMaximos().size(); i++) {
            System.out.println(config.getMaximos().get(i));
        }

      //  Long [] semilla= config.getSemillas();
        for (int i = 0; i < config.getSemillas().size(); i++) {
            System.out.println(config.getSemillas().get(i));
        }

       // Integer [] algoritmo=config.getAlgoritmos();
        for (int i = 0; i < config.getAlgoritmos().size(); i++) {
            System.out.println(config.getAlgoritmos().get(i));
        }


            BusquedaLocal bl=new BusquedaLocal(77367663);

      //  bl.busquedalocal(1000, 0.3,0.1f,-1,10,-33,33,"ackley");


    }
}