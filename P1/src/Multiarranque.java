public class Multiarranque {
    private final Randon rand;
    private StringBuilder log;

    public Multiarranque(long semilla) {
        rand = new Randon();
        rand.Set_random(semilla);
        log = new StringBuilder();
    }


    void multiarranque(int iteracion, double probabilidad, float porcentajeAleatorio, int k, int dimension, Float valorMin, Float valorMax, String funcion, int elementosTabu, int divisiones) {

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
        double[] mejorvecino = new double[dimension];
        double mejorcostevecino = 0.0;
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
        mejorcostevecino=Double.MAX_VALUE;
        mejorCosteGlobal = mejorCoste;
        mejorSolucionGlobal = mejorSolucion;
        listaTabu[contTabu % elementosTabu] = mejorSolucion;
        contTabu++;
        int multiarranque=0;
        int contador = 0;
        while (it < iteracion) {
            if (k == 0) {
                k = rand.Randint(4, 10);
            }

            for (int numvecinos = 0; numvecinos < k; numvecinos++) {
                double[] vecino = new double[dimension];
                creavecino(dimension, k, probabilidad, mejorSolucion, porcentajeAleatorio, valorMin, valorMax, vecino,multiarranque);
                int[] conversor = new int[dimension];
                if (estabu(listaTabu, vecino) == false) {
                    conversionSolMemoriaCortoPlazo(mejorSolucion, vecino, conversor);
                    if (memoriaCortoplazo(conversor, memoriaCortoPlazo, dimension) == false) {
                        //actualizar mejor global, mejor del momento y rellenar lista tabu y memorias
                        double nuevovecino = evaluacion(vecino, funcion);
                        if ( nuevovecino < mejorcostevecino) {
                            mejorvecino = vecino;
                            mejorcostevecino = nuevovecino;
                        }
                    }else{
                        // System.out.println("------------------------------");
                    }
                }else{
                    //System.out.println("tabuuuu");
                }
            }
            mejorSolucionActual = mejorvecino;
            mejorCosteActual = mejorcostevecino;
            listaTabu[contTabu % elementosTabu] = mejorSolucionActual;
            conversionSolMemoriaCortoPlazo(mejorSolucion, mejorSolucionActual, memoriaCortoPlazo[contTabu % elementosTabu]);
            memoriaLargoPlazo(valorMin, valorMax, memoriaLargoPlazo, divisiones, mejorSolucionActual);
            contTabu++;
            // mejora = true;


            //  }
            // }
//            }

            if (mejorCosteActual < mejorCoste) {
                it2 = 0;
//                mejorcosteOptimoActual = mejorCosteActual;
//                mejorOptimoActual = mejorSolucionActual;
                mejorCoste = mejorCosteActual;
                mejorSolucion = mejorSolucionActual;
            } else {
                it2++;
                multiarranque=(multiarranque+1)%3;
                //System.out.println(it2);
//                mejorcosteOptimoActual = mejorCoste;
//                mejorOptimoActual = mejorSolucion;
                mejorCoste = mejorCosteActual;
                mejorSolucion = mejorSolucionActual;
            }

            if (it == 1) {
//                System.out.println("voy pòr la 5");
            }


            if (it2 == itSinMejora) {
                contador++;
                float entro = rand.Randfloat(0, 1);
                if (entro < 0.5) {
                    //diversificacion
                    costeAntesdeReinicializar = mejorCoste;
                    solucionAntesdeReinicializar = mejorSolucion;
                    diversificacion(memoriaLargoPlazo, mejorSolucion, valorMin, valorMax, divisiones);
                    limpieza(memoriaCortoPlazo, memoriaLargoPlazo, listaTabu);
                    it2 = 0;

                } else {
                    costeAntesdeReinicializar = mejorCoste;
                    solucionAntesdeReinicializar = mejorSolucion;
                    //intensificacion(memoriaLargoPlazo, mejorSolucion, valorMin, valorMax, divisiones);
                    masVisitados(memoriaLargoPlazo,mejorSolucion,valorMin,valorMax);
                    //intensificacion
                    limpieza(memoriaCortoPlazo, memoriaLargoPlazo, listaTabu);
                    it2 = 0;

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
        System.out.println(contador);
        ;
    }


    void creavecino(int dimension, int k, double probabilidad, double[] mejorSolucion, float porcentajeAleatorio, float valorMin, float valorMax, double[] vecinos,int multiarranque) {
        // for (int i = 0; i < k; i++) {
        for (int j = 0; j < dimension; j++) {
            double muta = rand.Randfloat(0, 1);
            if (muta < probabilidad) {
                if(multiarranque==0) {

                    float inferior = (float) (mejorSolucion[j] * (1 - porcentajeAleatorio));
                    float superior = (float) (mejorSolucion[j] * (1 + porcentajeAleatorio));
                    if (mejorSolucion[j] < 0) {
                        //solo para negativos el rango cambia
                        float aux = inferior;
                        inferior = superior;
                        superior = aux;
                    }

                    if (inferior < valorMin) {
                        inferior = valorMin;
                    }
                    if (superior > valorMax) {
                        superior = valorMax;

                    }
                    vecinos[j] = rand.Randfloat(inferior, superior);
                }else{
                    if (multiarranque==2){
                        //VNS caso 2
                        vecinos[j] = rand.Randfloat(valorMin, valorMax);
                    } else{
                        //VNS caso 3
                        vecinos[j] = mejorSolucion[j]*-1;
                    }
                }
            } else {
                vecinos[j] = mejorSolucion[j];
            }
        }
        // }
    }

    void mejorvecino(double[] vecino, double[] mejorvecino, String funcion) {
        if (evaluacion(vecino, funcion) < evaluacion(mejorvecino, funcion)) {
            mejorvecino = vecino;
        }
    }

    double[] solucionInicialAleatoria(double[] sol, int dimension, float valorMin, float valorMax) {

        for (int i = 0; i < dimension; i++) {
            sol[i] = rand.Randfloat(valorMin, valorMax);
        }
        return sol;
    }

//    void ordenaVecinos(double[][] vecinos, String funcion) {
//
//        double aux1;
//        double aux2;
//        System.out.println("Coste vecinos sin ordenar");
//        for (int x = 0; x < vecinos.length; x++) {
//            System.out.println(evaluacion(vecinos[x],funcion));
//            for (int i = 0; i < vecinos.length - x - 1; i++) {
//                aux1 = evaluacion(vecinos[i], funcion);
//                aux2 = evaluacion(vecinos[i + 1], funcion);
//                if (aux1 > aux2) {
//                    double[] tmp = vecinos[i + 1];
//                    vecinos[i + 1] = vecinos[i];
//                    vecinos[i] = tmp;
//                }
//            }
//        }
//        System.out.println("vecinos ordenados");
//        for (int i = 0; i < vecinos.length; i++) {
//            System.out.println(evaluacion(vecinos[i],funcion));
//        }
//
//    }

    boolean estabu(double[][] ltabu, double[] mejorSol) {
        float inf;
        float sup;
        boolean tabu = false;
        //primera comprobacion
        for (int j = 0; j < ltabu.length; j++) {
            for (int i = 0; i < mejorSol.length; i++) {
                double valor = ltabu[j][i];
                inf = (float) (valor * 0.99);
                sup = (float) (valor * 1.01);
                if (valor < 0) {
                    //solo para negativos en rango cambia
                    float aux = inf;
                    inf = sup;
                    sup = aux;
                }
                if (mejorSol[i] < inf || mejorSol[i] > sup) {
                    return false;
                } else {
                    tabu = true;
                }


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
                    tabu = false;
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
    void masVisitados( int mat[][] , double[] nuevaSol, double rmin, double rmax ){
        int tam=nuevaSol.length;
        double mayor;
        int pc=0;
        int []columnas =new int[3];

        for (int i=0; i<tam; i++){
            for (int k=0; k<3; k++){
                mayor=-1;
                for (int j=0; j<10; j++){
                    if (mat[i][j]>=mayor){
                        mayor=mat[i][j];
                        pc=j;
                    }
                }
                columnas[k]=pc;
                mat[i][pc]=-1;
            }
            int aleatorio= rand.Randint(0, 2);
            int col=columnas[aleatorio];
            double ancho=(rmax-rmin+1)/10;
            /*double ini=rmin, fin=ini+ancho;
            for (int k=0; k<col; k++){
                ini = fin;
                fin = fin +ancho;
            }
            */
            double ini=rmin+(col*ancho);
            double fin=ini+ancho;
            nuevaSol[i]= rand.Randfloat((float) ini, (float) fin);
        }
    }

    void intensificacion(int[][] memoria, double[] solucion, float valormin, float valormax, int divisiones) {
        float corte = (valormax - valormin) / divisiones;
        int max = 0;
        int posicion = 0;
        for (int i = 0; i < memoria.length; i++) {
            for (int j = 0; j < memoria.length; j++) {
                for (int k = 0; k < memoria.length; k++) {
                    if (memoria[i][j] > max) {
                        max = memoria[i][j];
                        posicion = j;
                    }
                }
            }
            double aux = rand.Randfloat(posicion * corte + valormin, (posicion + 1) * corte + valormin);
            solucion[i] = aux;
        }
    }


    void diversificacion(int[][] memoria, double[] solucion, float valormin, float valormax, int divisiones) {
        float corte = (valormax - valormin) / divisiones;
        int min = Integer.MIN_VALUE;
        int posicion = 0;
        for (int i = 0; i < memoria.length; i++) {
            for (int j = 0; j < memoria.length; j++) {
                for (int k = 0; k < memoria.length; k++) {
                    if (memoria[i][j] < min) {
                        min = memoria[i][j];
                        posicion = j;
                    }
                }
            }
            double aux = rand.Randfloat(posicion * corte + valormin, (posicion + 1) * corte + valormin);
            solucion[i] = aux;
        }
    }


    void limpieza(int[][] memoriaCP, int[][] memoriaLP, double[][] listaTabu) {

        for (int i = 0; i < memoriaCP.length; i++) {
            for (int j = 0; j < memoriaLP.length; j++) {
                memoriaCP[i][j] = 0;
                listaTabu[i][j] = 0;
            }
        }
        for (int i = 0; i < memoriaLP.length; i++) {
            for (int j = 0; j < memoriaLP.length; j++) {
                memoriaLP[i][j] = 0;
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


