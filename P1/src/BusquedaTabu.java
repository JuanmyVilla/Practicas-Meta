public class BusquedaTabu {
    private final Randon rand;
    private StringBuilder log;

    public BusquedaTabu(long semilla) {
        rand = new Randon();
        rand.Set_random(semilla);
        log = new StringBuilder();
    }


    void busquedatabu(int iteracion, double probabilidad, float porcentajeAleatorio, int k, int dimension, Float valorMin, Float valorMax, String funcion, int elementosTabu) {

        log.append("INICIO EJECUCION: Algoritmo BUSQUEDA LOCAL\n");
        log.append(" - Funcion: " + funcion + "\n");
        long inicioBL = System.currentTimeMillis();
        double[] mejorSolucion = new double[dimension];
        double mejorCoste = 0.0;
        double[] mejorSolucionActual = new double[dimension];
        double mejorCosteActual = 0.0;
        double[][] listaTabu = new double[elementosTabu][dimension];
        int[][] memoriaCortoPlazo = new int[elementosTabu][dimension];
        int[][] memoriaLargoPlazo = new int[dimension][dimension];
        int it = 0;
        int itSinMejora = (int) (iteracion * 0.05);
        int it2 = 0;
        double[] solucion = new double[dimension];
        mejorSolucion = solucionInicialAleatoria(solucion, dimension, valorMin, valorMax);
        mejorCoste = evaluacion(mejorSolucion, funcion);

        while (it < iteracion && it2 < itSinMejora) {
            if (k == 0) {
                k = rand.Randint(4, 10);
            }
            double[][] vecinos = new double[k][dimension];
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < dimension; j++) {
                    double muta = rand.Randfloat(0, 1);
                    if (muta < probabilidad) {
                        float inferior = (float) (mejorSolucion[j] * (1 - porcentajeAleatorio));
                        float superior = (float) (mejorSolucion[j] * (1 + porcentajeAleatorio));
                        vecinos[i][j] = rand.Randfloat(inferior, superior);
                    } else {
                        vecinos[i][j] = mejorSolucion[j];
                    }
                }
            }
            ordenaVecinos(vecinos, funcion);
            //cuando contamos la no mejora cuando todos los vecinos son tabu?
            int [] conversor=new int[dimension];
            for (int i = 0; i < vecinos.length; i++) {
                if(estabu(vecinos[i],listaTabu,dimension)==false){
                    conversionSolMemoriaCortoPlazo(mejorSolucion,vecinos[i],conversor);
                    if(memoriaCortoplazo(conversor,memoriaCortoPlazo,dimension)==false){
                        //actualizar mejor global, mejor del momento y rellenar lista tabu y memorias
                    }
                }
            }

//            int nuevomej;
//            nuevomej = nuevoMejor(vecinos, funcion);
//
//            if (nuevomej == -1) {
//                it2++;
//            } else {
//                mejorSolucion = vecinos[nuevomej];
//                mejorCoste = evaluacion(mejorSolucion, funcion);
//                it2 = 0;
//            }
            it++;
        }
        long finalBL = System.currentTimeMillis();
        log.append("Coste Final BUSQUEDA LOCAL: " + mejorCoste + "\n");

        log.append("Tiempo de Ejecucion: " + (finalBL - inicioBL) + " ms\n");
        log.append("Numero de iteraciones " + it);
        log.append("Solucion BUSQUEDA LOCAL:" + "\n");
        for (int s = 0; s < mejorSolucion.length; s++) {
            log.append(" - solucionBL[" + s + "] = " + mejorSolucion[s] + "\n");
        }
    }

    double[] solucionInicialAleatoria(double[] sol, int dimension, float valorMin, float valorMax) {

        for (int i = 0; i < dimension; i++) {
            sol[i] = rand.Randfloat(valorMin, valorMax);
        }
        return sol;
    }

    void ordenaVecinos(double[][] vecinos, String funcion) {

        double aux1;
        double aux2;
        for (int x = 0; x < vecinos.length; x++) {
            for (int i = 0; i < vecinos.length - x - 1; i++) {
                aux1 = evaluacion(vecinos[i], funcion);
                aux2 = evaluacion(vecinos[i + 1], funcion);
                if (aux1 > aux2) {
                    double[] tmp = vecinos[i + 1];
                    vecinos[i + 1] = vecinos[i];
                    vecinos[i] = tmp;
                }
            }
        }

    }

    boolean estabu(double[] vecinos, double[][] listaTabu, int dimension) {

        boolean tabu = false;
        //primera comprobacion
        for (int i = 0; i < dimension; i++) {
//los +-1% son respecto a la solucion de partida o respecto a las de la lista tabu lo hare suponiendo que es con los de la lista tabu
            for (int k = 0; k < listaTabu.length; k++) {
                for (int l = 0; l < dimension; l++) {
                    if (vecinos[i] * 1.01 == listaTabu[k][l] || vecinos[i] * 0.99 == listaTabu[k][l]) {
                        tabu = true;
                    } else {
                        return false;
                    }
                }
            }

        }
        return tabu;
    }

    boolean memoriaCortoplazo (int []vecinos, int[][] memoria,int dimension){
        boolean tabu=false;

        for (int i = 0; i < memoria.length; i++) {
            for (int j = 0; j < dimension; j++) {
                if(vecinos[j]==memoria[i][j]){//quizas no funciones porque mira todos iguales y solo deba mirar los 1
                    tabu=true;
                }else{
                    return false;
                }
            }
        }

        return tabu;
    }

    void conversionSolMemoriaCortoPlazo(double []solucion,double[] vecino,int []conversion){
        for (int i = 0; i < solucion.length; i++) {
            if(solucion[i]==vecino[i]){
                conversion[i]=0;
            }else{
                conversion[i]=1;
            }
        }
    }

    int nuevoMejor(double[][] vecinos, String funcion) {
        int posicion = -1;
        double aux = Double.MAX_VALUE;

        for (int i = 0; i < vecinos.length; i++) {
            aux = evaluacion(vecinos[i], funcion);
            for (int j = i; j < vecinos.length; j++) {
                double aux2 = evaluacion(vecinos[j], funcion);
                if (aux < aux2) {
                    posicion = i;
                } else {
                    posicion = j;
                }
            }
        }
        return posicion;
    }

    double evaluacion(double[] solucion, String funcion) {

        double coste = 0.0;
        Evaluacion eva = new Evaluacion();

        switch (funcion) {
            case "ackley":
                coste = eva.ackley(solucion);
                break;
            case "perm":
                coste = eva.perm(solucion, 1);
                break;
            case "rastringin":
                coste = eva.rastringin(solucion);
                break;
            case "trid":
                coste = eva.trid(solucion);
                break;
            case "rotated":
                coste = eva.rotated(solucion);
                break;
            case "rosenbrock":
                coste = eva.rosenbrock(solucion);
                break;
            case "dixon":
                coste = eva.dixon(solucion);
                break;
            case "schewefel":
                coste = eva.schewefel(solucion);
                break;
            case "michalewicz":
                coste = eva.michalewicz(solucion, 1);
                break;
            case "griewank":
                coste = eva.griewank(solucion);
                break;

        }
        return coste;
    }

    public String getLog() {
        return log.toString();
    }
}


