import java.lang.reflect.Array;
import java.util.Arrays;

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
        mejorcostevecino = Double.MAX_VALUE;
        mejorCosteGlobal = mejorCoste;
        mejorSolucionGlobal = Arrays.copyOf(mejorSolucion, mejorSolucion.length);
        listaTabu[contTabu % elementosTabu] = Arrays.copyOf(mejorSolucion, mejorSolucion.length);
        contTabu++;
        int multiarranque = 0;
        int reinicioDiversificando = 0;
        int reinicioIntensificando = 0;
        while (it < iteracion) {
            if (k == 0) {
                k = rand.Randint(4, 10);
            }
            log.append("Iteracion: " + it + ". Numero NO mejoras: " + it2 + ". Coste MEJOR SOLUCION: " + mejorCosteGlobal + ". Coste SOLUCION ACTUAL: " + mejorCoste + "Modo de generar el vecindario"+ multiarranque + "\n");
            for (int numvecinos = 0; numvecinos < k; numvecinos++) {
                double[] vecino = new double[dimension];
                creavecino(dimension, k, probabilidad, mejorSolucion, porcentajeAleatorio, valorMin, valorMax, vecino, multiarranque);
                int[] conversor = new int[dimension];
                if (estabu(listaTabu, vecino) == false) {
                    conversionSolMemoriaCortoPlazo(mejorSolucion, vecino, conversor);
                    if (memoriaCortoplazo(conversor, memoriaCortoPlazo, dimension) == false) {
                        double nuevovecino = evaluacion(vecino, funcion);
                        if (nuevovecino < mejorcostevecino) {
                            mejorvecino = vecino;
                            mejorcostevecino = nuevovecino;
                        }
                    }
                }
            }
            mejorSolucionActual = mejorvecino;
            mejorCosteActual = mejorcostevecino;
            listaTabu[contTabu % elementosTabu] = mejorSolucionActual;
            conversionSolMemoriaCortoPlazo(mejorSolucion, mejorSolucionActual, memoriaCortoPlazo[contTabu % elementosTabu]);
            memoriaLargoPlazo(valorMin, valorMax, memoriaLargoPlazo, divisiones, mejorSolucionActual);
            contTabu++;
            if (mejorCosteActual < mejorCoste) {
                it2 = 0;
                mejorCoste = mejorCosteActual;
                mejorSolucion = mejorSolucionActual;
            } else {
                it2++;
                multiarranque = (multiarranque + 1) % 3;
                mejorCoste = mejorCosteActual;
                mejorSolucion = mejorSolucionActual;
            }
            if (it2 == itSinMejora) {
                float entro = rand.Randfloat(0, 1);
                if (entro < 0.5) {
                    //diversificacion
                    log.append("REINICIO!! EXPLORACION: Usamos los elementos menos frecuentes.\n");
                    reinicioDiversificando++;
                    double costeAntesdeReinicializarDiver = 0.0;
                    costeAntesdeReinicializarDiver = mejorCoste;
                    double[] solucionDespuesdiversificacion = new double[dimension];
                    diversificacion(memoriaLargoPlazo, solucionDespuesdiversificacion, valorMin, valorMax, divisiones);
                    limpieza(memoriaCortoPlazo, memoriaLargoPlazo, listaTabu);
                    mejorcostevecino=Double.MAX_VALUE;
                    it2 = 0;
                    mejorSolucion = solucionDespuesdiversificacion;
                    mejorCoste = evaluacion(mejorSolucion, funcion);
                    if(costeAntesdeReinicializarDiver>mejorCoste){
                        log.append("MEJORO EL COSTE DIVERSIFICANDO\n");
                    }
                } else {
                    //intensificacion
                    double costeAntesdeReinicializarInter = 0.0;
                    reinicioIntensificando++;
                    costeAntesdeReinicializarInter = mejorCoste;
                    double[] solucionDespuesintensificacion = new double[dimension];
                    intensificacion(memoriaLargoPlazo, solucionDespuesintensificacion, valorMin, valorMax);
                    log.append("REINICIO!! EXPLOTACION: Usamos los elementos mas frecuentes.\n");
                    limpieza(memoriaCortoPlazo, memoriaLargoPlazo, listaTabu);
                    mejorcostevecino=Double.MAX_VALUE;
                    it2 = 0;
                    mejorSolucion = solucionDespuesintensificacion;
                    mejorCoste = evaluacion(mejorSolucion, funcion);
                    if(costeAntesdeReinicializarInter>mejorCoste){
                        log.append("MEJORO EL COSTE INTENSIFICANDO\n");
                    }
                }
            }
            if (mejorCoste < mejorCosteGlobal) {
                mejorCosteGlobal = mejorCoste;
                mejorSolucionGlobal = Arrays.copyOf(mejorSolucion, mejorSolucion.length);
            }
            it++;
        }
        long finalBL = System.currentTimeMillis();
        log.append("\n"+"Coste Final BUSQUEDA TABU CON VNS: " + mejorCosteGlobal + "\n");

        log.append("Tiempo de Ejecucion: " + (finalBL - inicioBL) + " ms\n");
        log.append("Numero de iteraciones " + it + "\n");
        log.append("Solucion BUSQUEDA TABU CON VNS:" + "\n");
        for (int s = 0; s < mejorSolucion.length; s++) {
            log.append(" - solucionBTVNS[" + s + "] = " + mejorSolucionGlobal[s] + "\n");
        }
        log.append("Veces que intensifico " + reinicioIntensificando + "\n");
        log.append("Veces que diversifico " + reinicioDiversificando + "\n");
    }


    void creavecino(int dimension, int k, double probabilidad, double[] mejorSolucion, float porcentajeAleatorio, float valorMin, float valorMax, double[] vecinos, int multiarranque) {
        for (int j = 0; j < dimension; j++) {
            double muta = rand.Randfloat(0, 1);
            if (muta < probabilidad) {
                if (multiarranque == 0) {
                    float inferior = (float) (mejorSolucion[j] * (1 - porcentajeAleatorio));
                    float superior = (float) (mejorSolucion[j] * (1 + porcentajeAleatorio));
                    if (mejorSolucion[j] < 0) {
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
                } else {
                    if (multiarranque == 1) {
                        //VNS caso 2
                        vecinos[j] = rand.Randfloat(valorMin, valorMax);
                    } else {
                        //VNS caso 3
                        vecinos[j] = mejorSolucion[j] * -1;
                    }
                }
            } else {
                vecinos[j] = mejorSolucion[j];
            }
        }
    }

    double[] solucionInicialAleatoria(double[] sol, int dimension, float valorMin, float valorMax) {

        for (int i = 0; i < dimension; i++) {
            sol[i] = rand.Randfloat(valorMin, valorMax);
        }
        return sol;
    }

    boolean estabu(double[][] ltabu, double[] mejorSol) {
        float inf;
        float sup;
        boolean tabu = false;
        for (int j = 0; j < ltabu.length; j++) {
            for (int i = 0; i < mejorSol.length; i++) {
                double valor = ltabu[j][i];
                inf = (float) (valor * 0.99);
                sup = (float) (valor * 1.01);
                if (valor < 0) {
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
                if (vecinos[j] == memoria[i][j]) {
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
    void intensificacion(int mat[][], double[] nuevaSol, double rmin, double rmax) {
        int tam = nuevaSol.length;
        double mayor;
        int pc = 0;
        int[] columnas = new int[3];

        for (int i = 0; i < tam; i++) {
            for (int k = 0; k < 3; k++) {
                mayor = -1;
                for (int j = 0; j < 10; j++) {
                    if (mat[i][j] >= mayor) {
                        mayor = mat[i][j];
                        pc = j;
                    }
                }
                columnas[k] = pc;
                mat[i][pc] = -1;
            }
            int aleatorio = rand.Randint(0, 2);
            int col = columnas[aleatorio];
            double ancho = (rmax - rmin + 1) / 10;
            double ini = rmin + (col * ancho);
            double fin = ini + ancho;
            nuevaSol[i] = rand.Randfloat((float) ini, (float) fin);
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



