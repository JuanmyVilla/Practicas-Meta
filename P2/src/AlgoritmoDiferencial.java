public class AlgoritmoDiferencial {
    private final Randon_Clase02_Grupo06 rand;
    //  private StringBuilder log;

    public AlgoritmoDiferencial(long semilla) {//, StringBuilder log) {
        rand = new Randon_Clase02_Grupo06();
        rand.Set_random(semilla);
    }

    public void EvolucionDiferencial(int evaluaciones, int poblacion, double probabilidadCruce, double probabilidadMutacion, int algo, int dimension, int tamTorneo, Float valorMax, Float valorMin, String funcion, String selectorCruce,float factorrecombinacion) {

        Cromosoma mejorCromosomaGlobal = new Cromosoma();
        Cromosoma mejorGeneracion = new Cromosoma();
        mejorGeneracion.setCoste(Double.MAX_VALUE);
        Cromosoma[] poblacionInicial = new Cromosoma[poblacion];
        Cromosoma[] nuevaPoblacion = new Cromosoma[poblacion];
        generaPoblacionInicial(poblacionInicial, poblacion, dimension, valorMin, valorMax, funcion);
        //evaluar en el bucle
        mejorCromosomaGlobal = new Cromosoma(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
       // mejorGeneracion = new Cromosoma(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
        int it = poblacion;
        while (it < evaluaciones) {
            Cromosoma[] mutados = new Cromosoma[poblacion];
            for (int i = 0; i < poblacion; i++) {
                mutados[i] = new Cromosoma(mutacion(poblacionInicial[i], (float) probabilidadMutacion, dimension, valorMin, valorMax, funcion));
            }
            for (int k = 0; k < poblacion; k++) {
                int indiceobjetivo = torneo(mutados, 3, poblacion, "mejor");
                int aleatorio;
                int aleatorio2;
                while (distintos(k, indiceobjetivo, aleatorio2 = rand.Randint(0, poblacion - 1), aleatorio = rand.Randint(0, poblacion - 1))) {
                    Cromosoma objetivo = new Cromosoma(mutados[indiceobjetivo]);
                    float F = rand.Randfloat((float) 0, (float) 1.01);
                    double[] cromfinal = new double[dimension];
                    for (int j = 0; j < dimension; j++) {
                        float eleccion = rand.Randfloat((float) 0, (float) 1.01);
                        if (eleccion < factorrecombinacion) {
                            cromfinal[j] = objetivo.getIndividuosIndice(j);
                        } else {
                            cromfinal[j] = recombinacion(mutados[j].getIndividuosIndice(j), F, mutados[aleatorio].getIndividuosIndice(j), mutados[aleatorio2].getIndividuosIndice(j), valorMax, valorMin);
                        }
                    }
                    double posiblemejor = evaluacion(cromfinal, funcion);
                    it++;
                    if (posiblemejor < mutados[k].getCoste()) {
                        nuevaPoblacion[k] = new Cromosoma(cromfinal, posiblemejor);
                    } else {
                        nuevaPoblacion[k] = new Cromosoma(mutados[k]);
                    }
                }
            }
        }
            int mejorpoblacion= mejorCromosoma(nuevaPoblacion,poblacion);
            if(nuevaPoblacion[mejorpoblacion].getCoste()<mejorCromosomaGlobal.getCoste()){
                mejorCromosomaGlobal=new Cromosoma(nuevaPoblacion[mejorpoblacion]);
            }


        System.out.println("El coste es: " + mejorCromosomaGlobal.getCoste());
        System.out.println("El mejor cromosoma es : ");
        for (int i = 0; i < dimension; i++) {
            System.out.println(mejorCromosomaGlobal.getIndividuosIndice(i));
        }
    }

    void generaPoblacionInicial(Cromosoma[] poblacion, int tampoblacion, int dimension, Float valorMin, Float valorMax, String funcion) {
        for (int i = 0; i < tampoblacion; i++) {
            double[] vinicio = new double[dimension];
            for (int j = 0; j < dimension; j++) {
                double aux = rand.Randfloat(valorMin, valorMax);
                vinicio[j] = aux;
            }
            poblacion[i] = new Cromosoma(vinicio, evaluacion(vinicio, funcion));
        }
    }

    int mejorCromosoma(Cromosoma[] cromosoma, int poblacion) {
        int mejorcromosoma = -1;
        double aux = Double.MAX_VALUE;
        for (int i = 0; i < poblacion; i++) {
            if (cromosoma[i].getCoste() < aux) {
                aux = cromosoma[i].getCoste();
                mejorcromosoma = i;
            }
        }
        return mejorcromosoma;
    }

    int peorCromosoma(Cromosoma[] cromosoma, int poblacion) {
        int mejorcromosoma = -1;
        double aux = 0;
        for (int i = 0; i < poblacion; i++) {
            if (cromosoma[i].getCoste() > aux) {
                aux = cromosoma[i].getCoste();
                mejorcromosoma = i;
            }
        }
        return mejorcromosoma;
    }

    int encuentraObjetivo (Cromosoma[] cromosomas,int a1,int a2,int a3,int a4){
        int mejor=-1;
        double mejorcoste=Double.MAX_VALUE;
        if(cromosomas[a1].getCoste()<mejorcoste){
            mejor=a1;
            mejorcoste=cromosomas[a1].getCoste();
        }
        if(cromosomas[a2].getCoste()<mejorcoste){
            mejor=a2;
            mejorcoste=cromosomas[a2].getCoste();
        }
        if(cromosomas[a3].getCoste()<mejorcoste){
            mejor=a3;
            mejorcoste=cromosomas[a3].getCoste();
        }
        if(cromosomas[a4].getCoste()<mejorcoste){
            mejor=a4;
            mejorcoste=cromosomas[a4].getCoste();
        }
        return mejor;
    }

    int torneo(Cromosoma[] cromosomas, int tamTorneo, int poblacion, String selector) {
        Cromosoma[] torneoCromosomas = new Cromosoma[tamTorneo];
//        int[] vector = new int[poblacion];
//        for (int i = 0; i < poblacion; i++) {
//            vector[i] = i;
//        }
        int[] elegidos = new int[tamTorneo];
       // for (int i = 0; i < tamTorneo; i++) {
        int tam=0;
        while(tam<tamTorneo){
            int aleatorio1 = rand.Randint(0, poblacion - 1);
            if(!comprueba(elegidos,aleatorio1)){
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

    boolean comprueba(int[] elementos,int posible) {
        for (int i = 0; i < elementos.length; i++) {
                if (elementos[i] == posible) {
                    return true;
                }
            }
        return false;
    }

    double recombinacion (double padre,float f,double ale1,double ale2,double rmax,double rmin){
        double nuevo;
        nuevo= padre+ (f*(ale1-ale2));
        if (nuevo>rmax) {
            nuevo = rmax;
        }
        else {
            if (nuevo < rmin) {
                nuevo = rmin;
            }
        }
        return nuevo;

    }
    boolean distintos (int a1,int a2,int a3,int a4){
        if(a1!=a2 && a1!=a3 && a1 != a4 && a2 != a3 && a2!= a4 && a3!=a4){
            return true;
        }
        return false;
    }
    Cromosoma mutacion(Cromosoma hijo, float probabilidadMutacion, int dimension, float min, float max, String funcion) {
        double[] aux = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            float muta = rand.Randfloat((float) 0, (float) 1.01);
            if (muta < probabilidadMutacion) {
                aux[i] = rand.Randfloat(min, max);
            } else {
                aux[i] = hijo.getIndividuosIndice(i);
            }
        }
        //hijo = new Cromosoma(aux, evaluacion(aux, funcion));
        return hijo;
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

    double evaluacion(double[] solucion, String funcion) {

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

        }
        return coste;
    }


}
