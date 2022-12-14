import java.util.ArrayList;

public class AlgoritmoEvolutivo_Clase02_Grupo06 {
    private final Randon_Clase02_Grupo06 rand;
    private StringBuilder log;

    public AlgoritmoEvolutivo_Clase02_Grupo06(long semilla) {
        rand = new Randon_Clase02_Grupo06();
        rand.Set_random(semilla);
        log = new StringBuilder();
    }

    public void Evolutivo(int evaluaciones, int poblacion, double probabilidadCruce, double probabilidadMutacion, int dimension, int tamTorneo, Float valorMin, Float valorMax, String funcion, String selectorCruce, float alfa, String tipoMape, ArrayList<double[]> matriz) {
        log.append("INICIO EJECUCION: Algoritmo Evolutivo  \n");
        if (funcion.equals("potencia")) {
            log.append("Funcion: " + tipoMape + "\n");
        } else {
            log.append("Funcion: " + funcion + "\n");
        }
        log.append(" - Cruce usado : " + selectorCruce + "\n");
        long inicio2E2P = System.currentTimeMillis();
        Cromosoma_Clase02_Grupo06 mejorCromosomaGlobal = new Cromosoma_Clase02_Grupo06();
        Cromosoma_Clase02_Grupo06 elite = new Cromosoma_Clase02_Grupo06();
        elite.setCoste(Double.MAX_VALUE);
        Cromosoma_Clase02_Grupo06[] poblacionInicial = new Cromosoma_Clase02_Grupo06[poblacion];
        Cromosoma_Clase02_Grupo06[] nuevaPoblacion = new Cromosoma_Clase02_Grupo06[poblacion];
        generaPoblacionInicial(poblacionInicial, poblacion, dimension, valorMin, valorMax, funcion, tipoMape, matriz);
        mejorCromosomaGlobal = new Cromosoma_Clase02_Grupo06(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
        elite = new Cromosoma_Clase02_Grupo06(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
        boolean[] marcados = new boolean[poblacion];
        for (int i = 0; i < poblacion; i++) {
            marcados[i] = false;
        }
        int it = poblacion;
        while (it < evaluaciones) {
            Cromosoma_Clase02_Grupo06 cromosomaTorneo = new Cromosoma_Clase02_Grupo06();
            Cromosoma_Clase02_Grupo06[] ganadoresTorneo = new Cromosoma_Clase02_Grupo06[poblacion];
            //selecciono a los padres
            for (int i = 0; i < poblacion; i++) {
                //mirar repetidos
                int elementoElegido = torneo(poblacionInicial, tamTorneo, poblacion, "mejor");
                cromosomaTorneo = new Cromosoma_Clase02_Grupo06(poblacionInicial[elementoElegido]);
                ganadoresTorneo[i] = new Cromosoma_Clase02_Grupo06(cromosomaTorneo);

            }
            int nuevosHijos = 0;
            while (nuevosHijos < poblacion) {
                int aleatorio = rand.Randint(0, poblacion - 1);
                int aleatorio2 = rand.Randint(0, poblacion - 1);
                if (aleatorio != aleatorio2) {
                    Cromosoma_Clase02_Grupo06 cruzado;
                    if (selectorCruce.equals("MEDIA")) {
                        cruzado = new Cromosoma_Clase02_Grupo06(cruceMedia(ganadoresTorneo[aleatorio], ganadoresTorneo[aleatorio2], dimension, funcion, probabilidadCruce, marcados, nuevosHijos));
                    } else {
                        cruzado = new Cromosoma_Clase02_Grupo06(cruceBlx(ganadoresTorneo[aleatorio], ganadoresTorneo[aleatorio2], dimension, funcion, valorMin, valorMax, alfa, probabilidadCruce, marcados, nuevosHijos));
                    }
                    nuevaPoblacion[nuevosHijos] = new Cromosoma_Clase02_Grupo06(mutacion(cruzado, (float) probabilidadMutacion, dimension, valorMin, valorMax, funcion, marcados, nuevosHijos));
                    it = evaluados(nuevaPoblacion[nuevosHijos], marcados, nuevosHijos, funcion, it, tipoMape, matriz);
                    nuevosHijos++;
                }
            }
            boolean encontrado = encuentraElite(nuevaPoblacion, elite, poblacion, dimension);
            if (!encontrado) {
                //hacer el reemplazo
                int ele = torneo(nuevaPoblacion, 4, poblacion, "peor");
                nuevaPoblacion[ele] = new Cromosoma_Clase02_Grupo06(elite);
            }
            int nuevomejor = mejorCromosoma(nuevaPoblacion, poblacion);
            elite = new Cromosoma_Clase02_Grupo06(nuevaPoblacion[nuevomejor]);
            if (elite.getCoste() < mejorCromosomaGlobal.getCoste()) {
                mejorCromosomaGlobal = new Cromosoma_Clase02_Grupo06(elite);
            }

        }
        long final2E2P = System.currentTimeMillis();
        // Imprimimos por pantalla el mejor coste y el mejor cromosoma que hemos encontrado.
        log.append("ERROR OBTENIDO: " + mejorCromosomaGlobal.getCoste() + "\n");
        log.append("MEJOR CROMOSOMA:" + "\n");
        for (int i = 0; i < dimension; i++) {
            log.append("- cromosoma[" + i + "] = " + mejorCromosomaGlobal.getIndividuosIndice(i) + "\n");
        }
        log.append("Tiempo de Ejecucion: " + (final2E2P - inicio2E2P) + " ms\n");
    }

    void generaPoblacionInicial(Cromosoma_Clase02_Grupo06[] poblacion, int tampoblacion, int dimension, Float valorMin, Float valorMax, String funcion, String tipoMape, ArrayList<double[]> matriz) {
        for (int i = 0; i < tampoblacion; i++) {
            double[] vinicio = new double[dimension];
            for (int j = 0; j < dimension; j++) {
                double aux = rand.Randfloat(valorMin, valorMax);
                vinicio[j] = aux;
            }
            poblacion[i] = new Cromosoma_Clase02_Grupo06(vinicio, evaluacion(vinicio, funcion, tipoMape, matriz));
        }
    }

    int mejorCromosoma(Cromosoma_Clase02_Grupo06[] cromosoma, int poblacion) {
        int mejorcromosoma = 0;
        double aux = cromosoma[0].getCoste();
        for (int i = 1; i < poblacion; i++) {
            if (cromosoma[i].getCoste() < aux) {
                aux = cromosoma[i].getCoste();
                mejorcromosoma = i;
            }
        }
        return mejorcromosoma;
    }

    int peorCromosoma(Cromosoma_Clase02_Grupo06[] cromosoma, int poblacion) {
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

    int torneo(Cromosoma_Clase02_Grupo06[] cromosomas, int tamTorneo, int poblacion, String selector) {
        Cromosoma_Clase02_Grupo06[] torneoCromosomas = new Cromosoma_Clase02_Grupo06[tamTorneo];
        int[] elegidos = new int[tamTorneo];
        int tam = 0;
        while (tam < tamTorneo) {
            int aleatorio1 = rand.Randint(0, poblacion - 1);
            if (!comprueba(elegidos, aleatorio1)) {
                elegidos[tam] = aleatorio1;
                tam++;
            }
        }
        for (int i = 0; i < tamTorneo; i++) {
            torneoCromosomas[i] = new Cromosoma_Clase02_Grupo06(cromosomas[elegidos[i]]);
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

    Cromosoma_Clase02_Grupo06 cruceMedia(Cromosoma_Clase02_Grupo06 padre1, Cromosoma_Clase02_Grupo06 padre2, int dimension, String funcion, double probabilidad, boolean[] marcados, int pos) {
        double[] aux = new double[dimension];
        double[] aux2 = new double[dimension];
        aux = padre1.getIndividuos();
        aux2 = padre2.getIndividuos();
        double[] hijo1 = new double[dimension];
        Cromosoma_Clase02_Grupo06 hijo;
        float cruzo = rand.Randfloat(0, (float) 1.01);
        if (cruzo < probabilidad) {
            for (int i = 0; i < dimension; i++) {
                hijo1[i] = (aux[i] + aux2[i]) / 2;
            }
            hijo = new Cromosoma_Clase02_Grupo06(hijo1);
            marcados[pos] = true;
        } else {
            hijo = new Cromosoma_Clase02_Grupo06(padre1);
        }
        return hijo;
    }

    Cromosoma_Clase02_Grupo06 cruceBlx(Cromosoma_Clase02_Grupo06 padre1, Cromosoma_Clase02_Grupo06 padre2, int dimension, String funcion, float min, float max, double alfa, double probabilidad, boolean[] marcados, int pos) {
        double[] hijo1 = new double[dimension];
        Cromosoma_Clase02_Grupo06 hijo;
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
            hijo = new Cromosoma_Clase02_Grupo06(hijo1);
            marcados[pos] = true;
        } else {
            hijo = new Cromosoma_Clase02_Grupo06(padre1);
        }
        return hijo;
    }

    Cromosoma_Clase02_Grupo06 mutacion(Cromosoma_Clase02_Grupo06 hijo, float probabilidadMutacion, int dimension, float min, float max, String funcion, boolean[] marcados, int pos) {
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
            hijo = new Cromosoma_Clase02_Grupo06(aux);
            marcados[pos] = true;
        } else {
            return hijo;
        }
        return hijo;
    }

    int evaluados(Cromosoma_Clase02_Grupo06 hijo, boolean[] marcados, int pos, String funcion, int contador, String tipo, ArrayList<double[]> matriz) {
        if (marcados[pos]) {
            hijo.setCoste(evaluacion(hijo.getIndividuos(), funcion, tipo, matriz));
            contador++;
        }
        return contador;
    }

    boolean encuentraElite(Cromosoma_Clase02_Grupo06[] nuevaPoblacion, Cromosoma_Clase02_Grupo06 elite, int poblacion, int dimension) {
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

    double evaluacion(double[] solucion, String funcion, String tipo, ArrayList<double[]> observaciones) {
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
