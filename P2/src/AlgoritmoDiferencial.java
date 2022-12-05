public class AlgoritmoDiferencial {
    private final Randon_Clase02_Grupo06 rand;
    //  private StringBuilder log;

    public AlgoritmoDiferencial(long semilla) {//, StringBuilder log) {
        rand = new Randon_Clase02_Grupo06();
        rand.Set_random(semilla);
    }

    public void EvolucionDiferencial(int evaluaciones, int poblacion,int dimension, Float valorMin, Float valorMax, String funcion,float factorrecombinacion,int tipo,double [][] observaciones) {

        Cromosoma mejorCromosomaGlobal = new Cromosoma();
        Cromosoma mejorGeneracion = new Cromosoma();
        mejorGeneracion.setCoste(Double.MAX_VALUE);
        Cromosoma[] poblacionInicial = new Cromosoma[poblacion];
        Cromosoma[] nuevaPoblacion = new Cromosoma[poblacion];
        generaPoblacionInicial(poblacionInicial, poblacion, dimension, valorMin, valorMax, funcion,tipo,observaciones);
        //evaluar en el bucle
        mejorCromosomaGlobal = new Cromosoma(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
        int[] barajados = new int[poblacion];
        int it = poblacion;
        while (it < evaluaciones) {
              for (int k = 0; k < poblacion; k++) {
                int indiceobjetivo = torneo(poblacionInicial, 3, poblacion, "mejor");
                barajados=baraja(poblacion);
                // while (distintos(k, indiceobjetivo, aleatorio2 = rand.Randint(0, poblacion - 1), aleatorio = rand.Randint(0, poblacion - 1))) {
                Cromosoma objetivo = new Cromosoma(poblacionInicial[indiceobjetivo]);
                float F = rand.Randfloat((float) 0, (float) 1.01);
                double[] cromfinal = new double[dimension];
                for (int j = 0; j < dimension; j++) {
                    float eleccion = rand.Randfloat((float) 0, (float) 1.01);
                    if (eleccion < factorrecombinacion) {
                        cromfinal[j] = objetivo.getIndividuosIndice(j);
                    } else {
                        cromfinal[j] = recombinacion(poblacionInicial[j].getIndividuosIndice(j), F, poblacionInicial[barajados[j%poblacion]].getIndividuosIndice(j),poblacionInicial[barajados[((j+1)%poblacion)]].getIndividuosIndice(j), valorMax, valorMin);
                    }
                }
                double posiblemejor = evaluacion(cromfinal, funcion,tipo,observaciones);
                 it++;
                if (posiblemejor < poblacionInicial[k].getCoste()) {
                    nuevaPoblacion[k] = new Cromosoma(cromfinal, posiblemejor);
                } else {
                    nuevaPoblacion[k] = new Cromosoma(poblacionInicial[k]);
                }
            }
            int mejorpoblacion= mejorCromosoma(nuevaPoblacion,poblacion);
            if(nuevaPoblacion[mejorpoblacion].getCoste()<mejorCromosomaGlobal.getCoste()){
                mejorCromosomaGlobal=new Cromosoma(nuevaPoblacion[mejorpoblacion]);
            }
            poblacionInicial=nuevaPoblacion.clone();
        }
        System.out.println("El coste es: " + mejorCromosomaGlobal.getCoste());
        System.out.println("El mejor cromosoma es : ");
        for (int i = 0; i < dimension; i++) {
            System.out.println(mejorCromosomaGlobal.getIndividuosIndice(i));
        }
    }

    void generaPoblacionInicial(Cromosoma[] poblacion, int tampoblacion, int dimension, Float valorMin, Float valorMax, String funcion,int tipo, double [][] observaciones) {
        for (int i = 0; i < tampoblacion; i++) {
            double[] vinicio = new double[dimension];
            for (int j = 0; j < dimension; j++) {
                double aux = rand.Randfloat(valorMin, valorMax);
                vinicio[j] = aux;
            }
            poblacion[i] = new Cromosoma(vinicio, evaluacion(vinicio, funcion,tipo,observaciones));
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

    int [] baraja(int poblacion){
        int [] barajados =new int[poblacion];
        for (int i = 0; i < poblacion; i++) {
            barajados[i]=i;
        }
        for (int i = 0; i < poblacion; i++) {
            int primero = rand.Randint(0,poblacion-1);
            int segundo;
            while (primero== (segundo=rand.Randint(0,poblacion-1))){
            }
            int aux=barajados[primero];
            barajados[primero]=barajados[segundo];
            barajados[segundo]=aux;
        }
        return barajados;
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

    double evaluacion(double[] solucion, String funcion,int tipo,double [][] observaciones) {

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
                coste = eva.Potencia(solucion,observaciones,tipo);
                break;
        }
        return coste;
    }


}
