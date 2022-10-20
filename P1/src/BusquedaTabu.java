public class BusquedaTabu {
    private final Randon rand;
    private StringBuilder log;

    public BusquedaTabu(long semilla) {
        rand = new Randon();
        rand.Set_random(semilla);
        log = new StringBuilder();
    }


    void busquedatabu(int iteracion, double probabilidad, float porcentajeAleatorio, int k, int dimension, Float valorMin, Float valorMax, String funcion, int elementosTabu,int divisiones) {

        log.append("INICIO EJECUCION: Algoritmo BUSQUEDA TABU\n");
        log.append(" - Funcion: " + funcion + "\n");
        long inicioBL = System.currentTimeMillis();
        double[] mejorSolucion = new double[dimension];
        double mejorCoste = 0.0;
        double[] solucionAntesdeReinicializar = new double[dimension];
        double costeAntesdeReinicializar = 0.0;
        double[] mejorSolucionGlobal = new double[dimension];
        double mejorCosteGlobal = 0.0;
        double[] mejorSolucionActual = new double[dimension];
        double mejorCosteActual = 0.0;
        double[] mejorOptimoActual = new double[dimension];
        double mejorcosteOptimoActual = 0.0;
        double[][] listaTabu = new double[elementosTabu][dimension];
        int[][] memoriaCortoPlazo = new int[elementosTabu][dimension];
        int[][] memoriaLargoPlazo = new int[dimension][dimension];
        int it = 0;
        int itSinMejora = (int) (iteracion * 0.05);
        int it2 = 0;
        int contTabu = 0;
        double[] solucion = new double[dimension];
        mejorSolucion = solucionInicialAleatoria(solucion, dimension, valorMin, valorMax);
        mejorCoste = evaluacion(mejorSolucion, funcion);
        mejorCosteGlobal = mejorCoste;
        mejorSolucionGlobal = mejorSolucion;
        listaTabu[contTabu%elementosTabu]=mejorSolucion;
        contTabu++;

        while (it < iteracion) {
            if (k == 0) {
                k = rand.Randint(4, 10);
            }
            double[][] vecinos = new double[k][dimension];
            creavecino(dimension,k,probabilidad,mejorSolucion,porcentajeAleatorio,valorMin,valorMax,vecinos);
            ordenaVecinos(vecinos, funcion);
            //cuando contamos la no mejora cuando todos los vecinos son tabu?
            int[] conversor = new int[dimension];
            boolean mejora= false;
            for (int i = 0; i < vecinos.length; i++) {
                if(!mejora) {
                    if (estabu(vecinos[i], mejorSolucion) == false) {
                        conversionSolMemoriaCortoPlazo(mejorSolucion, vecinos[i], conversor);
                        if (memoriaCortoplazo(conversor, memoriaCortoPlazo, dimension) == false) {
                            //actualizar mejor global, mejor del momento y rellenar lista tabu y memorias
                            mejorSolucionActual = vecinos[i];
                            mejorCosteActual = evaluacion(mejorSolucionActual, funcion);
                            listaTabu[contTabu % elementosTabu] = mejorSolucionActual;
                            conversionSolMemoriaCortoPlazo(mejorSolucion, mejorSolucionActual, memoriaCortoPlazo[contTabu % elementosTabu]);
                            memoriaLargoPlazo(valorMin, valorMax, memoriaLargoPlazo, divisiones, mejorSolucionActual);
                            contTabu++;
                            mejora=true;
                        }
                    }
                }
            }


            if (mejorCosteActual < mejorCoste) {
                it2=0;
                mejorCoste = mejorCosteActual;
                mejorSolucion = mejorSolucionActual;
            } else {
               // System.out.println(it2);
               it2++;
//                mejorcosteOptimoActual = mejorCoste;
//                mejorOptimoActual = mejorSolucion;
                mejorCoste = mejorCosteActual;
                mejorSolucion = mejorSolucionActual;
            }


            if (it2 == itSinMejora) {
                float entro=rand.Randfloat(0, 1) ;
                if (entro< 0.5) {
                    //diversificacion
                    costeAntesdeReinicializar = mejorCoste;
                    solucionAntesdeReinicializar = mejorSolucion;
                    diversificacion(memoriaLargoPlazo,mejorSolucion,valorMin,valorMax,divisiones);
                    limpieza(memoriaCortoPlazo,memoriaLargoPlazo,listaTabu);
                    it2=0;

                } else {
                    costeAntesdeReinicializar = mejorCoste;
                    solucionAntesdeReinicializar = mejorSolucion;
                    intensificacion(memoriaLargoPlazo,mejorSolucion,valorMin,valorMax,divisiones);
                    //intensificacion
                    limpieza(memoriaCortoPlazo,memoriaLargoPlazo,listaTabu);
                    it2=0;

                }
            }
            if (mejorCoste < mejorCosteGlobal) {
                mejorCosteGlobal = mejorCoste;
                mejorSolucionGlobal = mejorSolucion;
            }
            
            it++;
        }
        long finalBL = System.currentTimeMillis();
        log.append("Coste Final BUSQUEDA TABU: " + mejorCosteGlobal + "\n");

        log.append("Tiempo de Ejecucion: " + (finalBL - inicioBL) + " ms\n");
        log.append("Numero de iteraciones " + it + "\n");
        log.append("Solucion BUSQUEDA TABU:" + "\n");
        for (int s = 0; s < mejorSolucion.length; s++) {
            log.append(" - solucionBT[" + s + "] = " + mejorSolucionGlobal[s] + "\n");
        }
    }


    void creavecino(int dimension,int k,double probabilidad,double [] mejorSolucion,float porcentajeAleatorio,float valorMin,float valorMax,double [][] vecinos){
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < dimension; j++) {
                double muta = rand.Randfloat(0, 1);
                if (muta < probabilidad) {
                    float inferior = (float) (mejorSolucion[j] * (1 - porcentajeAleatorio));
                    float superior = (float) (mejorSolucion[j] * (1 + porcentajeAleatorio));
                    if (mejorSolucion[j]<0)  {
                        //solo para negativos el rango cambia
                        float aux= inferior;
                        inferior=superior;
                        superior=aux;
                    }

                    if (inferior<valorMin){
                        inferior=valorMin;
                    }
                    if (superior>valorMax){
                        superior=valorMax;

                    }
                    vecinos[i][j] = rand.Randfloat(inferior, superior);
                } else {
                    vecinos[i][j] = mejorSolucion[j];
                }
            }
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

    boolean estabu(double[] vecinos, double[] mejorSol) {

        float inf;
        float sup;

        boolean tabu = false;
        //primera comprobacion
        for (int i = 0; i < mejorSol.length; i++) {
                inf= (float) (mejorSol[i]*0.99);
                sup= (float) (mejorSol[i]*1.01);
                if(vecinos[i]<0) {
//                    if (vecinos[i] * 1.01 >= mejorSol[k] && vecinos[i] * 0.99 <= mejorSol[k]) {
//                        tabu = true;
//                    } else {
//                        return false;
//                    }
                   float aux=inf;
                   inf=sup;
                   sup=aux;
                }
                   if(vecinos[i]<inf || vecinos[i]>sup){
                       return false;
                   }else{
                       tabu=true;
                   }





        }
        return tabu;
    }

    boolean memoriaCortoplazo(int[] vecinos, int[][] memoria, int dimension) {
        boolean tabu = false;

        for (int i = 0; i < memoria.length; i++) {
            for (int j = 0; j < dimension; j++) {
                if (vecinos[j] == memoria[i][j]) {//quizas no funciones porque mira todos iguales y solo deba mirar los 1
                    tabu = true;
                } else {
                    tabu= false;
                    break;
                }
            }
        }

        return tabu;
    }

    void memoriaLargoPlazo(float valormin, float valormax, int[][] memoriaLplazo, int divisiones, double[] solucion) {
        float corte = (valormax - valormin) / divisiones;
        for (int i = 0; i < solucion.length; i++) {
            for (int j = 0; j < divisiones; j++) {
                if (solucion[i] < valormin + (j + 1) * corte && solucion[i] > valormin + (j) * corte) {
                    memoriaLplazo[i][j]++;
                }
            }
        }

    }


    void conversionSolMemoriaCortoPlazo(double[] solucion, double[] vecino, int[] conversion) {
        for (int i = 0; i < solucion.length; i++) {
            if (solucion[i] == vecino[i]) {
                conversion[i] = 0;
            } else {
                conversion[i] = 1;
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


    void intensificacion(int[][] memoria, double[] solucion,float valormin, float valormax,int divisiones) {
        float corte = (valormax - valormin) / divisiones;
        int max= 0;
        int posicion=0;
        for (int i = 0; i < memoria.length; i++) {
            for (int j = 0; j < memoria.length; j++) {
                for (int k = 0; k < memoria.length; k++) {
                    if(memoria[i][j]>max){
                       max=memoria[i][j];
                       posicion=j;
                    }
                }
            }
            double aux = rand.Randfloat(posicion*corte+valormin,(posicion+1)*corte+valormin);
            solucion[i]=aux;
        }
    }


    void diversificacion(int[][] memoria, double[] solucion,float valormin, float valormax,int divisiones){
        float corte = (valormax - valormin) / divisiones;
        int min= Integer.MIN_VALUE;
        int posicion=0;
        for (int i = 0; i < memoria.length; i++) {
            for (int j = 0; j < memoria.length; j++) {
                for (int k = 0; k < memoria.length; k++) {
                    if(memoria[i][j]<min){
                        min=memoria[i][j];
                        posicion=j;
                    }
                }
            }
            double aux = rand.Randfloat(posicion*corte+valormin,(posicion+1)*corte+valormin);
            solucion[i]=aux;
        }
    }


    void limpieza(int [][] memoriaCP,int [][] memoriaLP,double [][] listaTabu){

        for (int i = 0; i < memoriaCP.length; i++) {
            for (int j = 0; j < memoriaLP.length; j++) {
                memoriaCP[i][j]=0;
                listaTabu[i][j]=0;
            }
        }
        for (int i = 0; i < memoriaLP.length; i++) {
            for (int j = 0; j < memoriaLP.length; j++) {
                memoriaLP[i][j]=0;
            }
        }

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


