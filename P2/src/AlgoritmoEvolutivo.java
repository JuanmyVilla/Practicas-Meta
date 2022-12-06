public class AlgoritmoEvolutivo {
    private final Randon_Clase02_Grupo06 rand;
    private StringBuilder log;

    public AlgoritmoEvolutivo(long semilla) {
        rand = new Randon_Clase02_Grupo06();
        rand.Set_random(semilla);
        log = new StringBuilder();
    }

    public void Evolutivo(int evaluaciones, int poblacion, double probabilidadCruce, double probabilidadMutacion, int dimension, int tamTorneo, Float valorMin, Float valorMax, String funcion, String selectorCruce, float alfa, String tipoMape, double[][] matriz) {
        log.append("INICIO EJECUCION: Algoritmo Evolutivo  \n");
        log.append(" - Cruce usado : " + selectorCruce + "\n");
        long inicio2E2P = System.currentTimeMillis();
        Cromosoma mejorCromosomaGlobal = new Cromosoma();
        Cromosoma elite = new Cromosoma();
        elite.setCoste(Double.MAX_VALUE);
        Cromosoma[] poblacionInicial = new Cromosoma[poblacion];
        Cromosoma[] nuevaPoblacion = new Cromosoma[poblacion];
        generaPoblacionInicial(poblacionInicial, poblacion, dimension, valorMin, valorMax, funcion, tipoMape, matriz);
        mejorCromosomaGlobal = new Cromosoma(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
        elite = new Cromosoma(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
        boolean[] marcados = new boolean[poblacion];
        for (int i = 0; i < poblacion; i++) {
            marcados[i] = false;
        }
        int it = poblacion;
        while (it < evaluaciones) {
            Cromosoma cromosomaTorneo = new Cromosoma();
            Cromosoma[] ganadoresTorneo = new Cromosoma[poblacion];
            //selecciono a los padres
            for (int i = 0; i < poblacion; i++) {
                //mirar repetidos
                int elementoElegido = torneo(poblacionInicial, tamTorneo, poblacion, "mejor");
                cromosomaTorneo = new Cromosoma(poblacionInicial[elementoElegido]);
                ganadoresTorneo[i] = new Cromosoma(cromosomaTorneo);

            }
            int nuevosHijos = 0;
            while (nuevosHijos < poblacion) {
                int aleatorio = rand.Randint(0, poblacion - 1);
                int aleatorio2 = rand.Randint(0, poblacion - 1);
                if (aleatorio != aleatorio2) {
                    Cromosoma cruzado;
                    if (selectorCruce == "MEDIA") {
                        cruzado = new Cromosoma(cruceMedia(ganadoresTorneo[aleatorio], ganadoresTorneo[aleatorio2], dimension, funcion, probabilidadCruce, marcados, nuevosHijos));
                    } else {
                        cruzado = new Cromosoma(cruceBlx(ganadoresTorneo[aleatorio], ganadoresTorneo[aleatorio2], dimension, funcion, valorMin, valorMax, alfa, probabilidadCruce, marcados, nuevosHijos));
                    }
                    nuevaPoblacion[nuevosHijos] = new Cromosoma(mutacion(cruzado, (float) probabilidadMutacion, dimension, valorMin, valorMax, funcion, marcados, nuevosHijos));
                    it = evaluados(nuevaPoblacion[nuevosHijos], marcados, nuevosHijos, funcion, it, tipoMape, matriz);
                    nuevosHijos++;

                }

            }
            boolean encontrado = encuentraElite(nuevaPoblacion, elite, poblacion, dimension);
            if (!encontrado) {
                //hacer el reemplazo
                int ele = torneo(nuevaPoblacion, 4, poblacion, "peor");
                nuevaPoblacion[ele] = new Cromosoma(elite);
            }
            int nuevomejor = mejorCromosoma(nuevaPoblacion, poblacion);
            elite = new Cromosoma(nuevaPoblacion[nuevomejor]);
            if (elite.getCoste() < mejorCromosomaGlobal.getCoste()) {
                mejorCromosomaGlobal = new Cromosoma(elite);
            }

        }
        long final2E2P = System.currentTimeMillis();
        // Imprimimos por pantalla el mejor coste y el mejor cromosoma que hemos encontrado.
        log.append("MEJOR COSTE: " + mejorCromosomaGlobal.getCoste()+"\n");
        log.append("MEJOR CROMOSOMA:" + "\n");
        for (int i = 0; i < dimension; i++) {
            log.append("- cromosoma[" + i + "] = " + mejorCromosomaGlobal.getIndividuosIndice(i) + "\n");
        }
        log.append("Tiempo de Ejecucion: " + (final2E2P - inicio2E2P) + " ms\n");
    }

    void generaPoblacionInicial(Cromosoma[] poblacion, int tampoblacion, int dimension, Float valorMin, Float valorMax, String funcion, String tipoMape, double[][] matriz) {
        for (int i = 0; i < tampoblacion; i++) {
            double[] vinicio = new double[dimension];
            for (int j = 0; j < dimension; j++) {
                double aux = rand.Randfloat(valorMin, valorMax);
                vinicio[j] = aux;
            }
            poblacion[i] = new Cromosoma(vinicio, evaluacion(vinicio, funcion, tipoMape, matriz));
        }
    }

    int mejorCromosoma(Cromosoma[] cromosoma, int poblacion) {
        int mejorcromosoma = 0;
        double aux =cromosoma[0].getCoste();
        for (int i = 1; i < poblacion; i++) {
            if (cromosoma[i].getCoste() < aux) {
                aux = cromosoma[i].getCoste();
                mejorcromosoma = i;
            }
        }

        return mejorcromosoma;
    }

    int peorCromosoma(Cromosoma[] cromosoma, int poblacion) {
        int mejorcromosoma = 0;
        double aux = cromosoma[0].getCoste();
        for (int i = 1; i < poblacion; i++) {
            if (cromosoma[i].getCoste() > aux) {
                aux = cromosoma[i].getCoste();
                mejorcromosoma = i;
            }
        }
        return mejorcromosoma;
    }

    int torneo(Cromosoma[] cromosomas, int tamTorneo, int poblacion, String selector) {
        Cromosoma[] torneoCromosomas = new Cromosoma[tamTorneo];
//        int[] vector = new int[poblacion];
//        for (int i = 0; i < poblacion; i++) {
//            vector[i] = i;
//        }
        int[] elegidos = new int[tamTorneo];
        // for (int i = 0; i < tamTorneo; i++) {
        int tam = 0;
        while (tam < tamTorneo) {
            int aleatorio1 = rand.Randint(0, poblacion - 1);
            if (!comprueba(elegidos, aleatorio1)) {
                elegidos[tam] = aleatorio1;
                tam++;
            }
        }

        for (int i = 0; i < tamTorneo; i++) {
            torneoCromosomas[i] = new Cromosoma(cromosomas[elegidos[i]]);
        }
        int ele;
        if (selector == "mejor") {
            ele = mejorCromosoma(torneoCromosomas, tamTorneo);
        } else {
            ele = peorCromosoma(torneoCromosomas, tamTorneo);
        }

        return ele;
    }

    boolean comprueba(int[] elementos, int posible) {
        for (int i = 0; i < elementos.length; i++) {
            if (elementos[i] == posible) {
                return true;
            }
        }
        return false;
    }

    Cromosoma cruceMedia(Cromosoma padre1, Cromosoma padre2, int dimension, String funcion, double probabilidad, boolean[] marcados, int pos) {
        double[] aux = new double[dimension];
        double[] aux2 = new double[dimension];
        aux = padre1.getIndividuos();
        aux2 = padre2.getIndividuos();
        double[] hijo1 = new double[dimension];
        Cromosoma hijo;
        float cruzo = rand.Randfloat(0, (float) 1.01);
        if (cruzo < probabilidad) {
            for (int i = 0; i < dimension; i++) {
                hijo1[i] = (aux[i] + aux2[i]) / 2;
            }

            hijo = new Cromosoma(hijo1);
            marcados[pos] = true;
        } else {
            hijo = new Cromosoma(padre1);
        }
        return hijo;
    }

    Cromosoma cruceBlx(Cromosoma padre1, Cromosoma padre2, int dimension, String funcion, float min, float max, double alfa, double probabilidad, boolean[] marcados, int pos) {
        double[] hijo1 = new double[dimension];
        Cromosoma hijo;
        double maximo;
        double minimo;
        double Int;
        float cruzo = rand.Randfloat(0, (float) 1.01);
        if (cruzo < probabilidad) {
            for (int i = 0; i < dimension; i++) {
                if (padre1.getIndividuosIndice(i) < padre2.getIndividuosIndice(i)) {
                    minimo = padre1.getIndividuosIndice(i);
                    maximo = padre2.getIndividuosIndice(i);
                } else {
                    maximo = padre1.getIndividuosIndice(i);
                    minimo = padre2.getIndividuosIndice(i);
                }
                Int = maximo - minimo;

                double r1 = minimo - (Int * alfa);
                double r2 = maximo + (Int * alfa);
                if (r1 < min) {
                    r1 = min;
                }
                if (r2 > max) {
                    r2 = max;
                }
                hijo1[i] = rand.Randfloat((float) r1, (float) r2);
            }

            hijo = new Cromosoma(hijo1);
            marcados[pos] = true;
        } else {

            hijo = new Cromosoma(padre1);
        }
        return hijo;
    }

    Cromosoma mutacion(Cromosoma hijo, float probabilidadMutacion, int dimension, float min, float max, String funcion, boolean[] marcados, int pos) {
        double[] aux = new double[dimension];
        boolean mutado = false;
        for (int i = 0; i < dimension; i++) {
            float muta = rand.Randfloat((float) 0, (float) 1.01);
            if (muta < probabilidadMutacion) {
                aux[i] = rand.Randfloat(min, max);
                mutado = true;
            } else {
                aux[i] = hijo.getIndividuosIndice(i);
            }
        }
        if (mutado) {
            hijo = new Cromosoma(aux);
            marcados[pos] = true;
        } else {
            return hijo;
        }
        return hijo;
    }

    int evaluados(Cromosoma hijo, boolean[] marcados, int pos, String funcion, int contador, String tipo, double[][] matriz) {
        if (marcados[pos]) {
            hijo.setCoste(evaluacion(hijo.getIndividuos(), funcion, tipo, matriz));
            contador++;
        }
        return contador;
    }

    boolean encuentraElite(Cromosoma[] nuevaPoblacion, Cromosoma elite, int poblacion, int dimension) {
        boolean encontrado = true;
        for (int i = 0; i < poblacion; i++) {
            for (int j = 0; j < dimension; j++) {
                if (nuevaPoblacion[i].getIndividuosIndice(j) == elite.getIndividuosIndice(j)) {
                    encontrado = true;
                } else {
                    encontrado = false;
                    break;
                }
            }
            if (encontrado) {
                return true;
            }
        }
        return false;
    }

    double evaluacion(double[] solucion, String funcion, String tipo, double[][] observaciones) {

        double coste = 0.0;
        Evaluacion_Clase02_Grupo06 eva = new Evaluacion_Clase02_Grupo06();

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
            case "potencia":
                coste = eva.Potencia(solucion, observaciones, tipo);
                break;

        }
        return coste;
    }

    public String getLog() {
        return log.toString();
    }

}
