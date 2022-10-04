public class Main {
    public static void main(String[] args) {
        Configurador config = new Configurador(args[0]);
        int dimension= config.getDimension();
        int rango_inferior= config.getRango_inferior();
        int rango_superior= config.getRango_superior();
        BusquedaLocal bl=new BusquedaLocal(77367663);

        bl.busquedalocal(1000, 0.3,0.1f,-1,10,-33,33,"ackley");


    }
}