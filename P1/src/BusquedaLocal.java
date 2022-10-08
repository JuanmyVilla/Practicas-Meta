public class BusquedaLocal {

    private final Randon rand;

    public BusquedaLocal(long semilla) {
        rand=new Randon();
        rand.Set_random(semilla);
    }

    void busquedalocal(int iteracion,double probabilidad,float porcentajeAleatorio,int k,int dimension,int valorMin,int valorMax,String funcion){
        double [] mejorSolucion = new double[dimension];
        double mejorCoste=0.0;
        int it=0;
        boolean mejora = true;
        double[] solucion=new double[dimension];
        mejorSolucion=solucionInicialAleatoria(solucion,dimension,valorMin,valorMax);
        mejorCoste=evaluacion(mejorSolucion,funcion);

        while(it<iteracion && mejora){
            if(k==-1){
                k= rand.Randint(4,10);
            }
            double [][] vecinos = new double[k][dimension];
            for(int i =0;i<k;i++){
                for (int j = 0; j < dimension; j++) {
                    double muta = rand.Randfloat(0,1);
                    if (muta < probabilidad){
                        float inferior= (float) (mejorSolucion[j]*(1-porcentajeAleatorio));
                        float superior= (float) (mejorSolucion[j]*(1+porcentajeAleatorio));
                        vecinos[i][j]=rand.Randfloat(inferior,superior);
                    }else {
                        vecinos[i][j]=mejorSolucion[j];
                    }
                }
            }
            int nuevomej;
            nuevomej=nuevoMejor(vecinos,mejorSolucion,mejorCoste,k,funcion);

            if(nuevomej==-1){
                mejora=false;
            }else {
                mejorSolucion=vecinos[nuevomej];
                mejorCoste=evaluacion(mejorSolucion,funcion);
            }
            it++;
        }

        System.out.println("El mejor coste es " + mejorCoste);
        for (int i = 0; i < dimension; i++) {
            System.out.println(mejorSolucion[i]);
        }
        System.out.println("La iteracion es " + it);
    }

    double[] solucionInicialAleatoria(double [] sol,int dimension,int valorMin,int valorMax){

        for (int i = 0; i < dimension; i++) {
            sol[i]=rand.Randint(valorMin,valorMax);
        }
        return sol;
    }

    int nuevoMejor (double [][] vecinos,double[] mejorSolucion,double mejorCoste,int k,String funcion){
        int posicion=-1;
        for (int i = 0; i <k ; i++) {
            double aux = 9999999999999999999999999999999.0;
            aux = evaluacion(vecinos[i],funcion);
            if (aux < mejorCoste) {
                posicion=i;
                mejorCoste=aux;
            }
        }
        return posicion;
    }

    double evaluacion(double[] solucion,String funcion){

        double coste=0.0;
        Evaluacion eva=new Evaluacion();

        switch (funcion){
            case "ackley":
                coste=eva.ackley(solucion);
                break;
            case "perm":
                coste=eva.perm(solucion,1);
                break;
            case "rastringin":
                coste=eva.rastringin(solucion);
                break;
            case "trid":
                coste=eva.trid(solucion);
                break;
            case "rotatedHH":
                coste=eva.rotated(solucion);
                break;
            case "rosenbrock":
                coste=eva.rosenbrock(solucion);
                break;
            case "dixon":
                coste=eva.dixon(solucion);
                break;
            case "schewefel":
                coste=eva.schewefel(solucion);
                break;
            case "michalewicz":
                coste=eva.michalewicz(solucion,1);
                break;
            case "griewank":
                coste=eva.griewank(solucion);
                break;

        }
        return coste;
    }

}
