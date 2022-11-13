public class AlgoritmoDiferencial {
    private final Randon_Clase02_Grupo06 rand;
    //  private StringBuilder log;

    public AlgoritmoDiferencial(long semilla) {//, StringBuilder log) {
        rand = new Randon_Clase02_Grupo06();
        rand.Set_random(semilla);
    }

    public void Evolutivo(int evaluaciones, int poblacion, double probabilidadCruce, double probabilidadMutacion, int k, int dimension, int tamTorneo, Float valorMax, Float valorMin, String funcion, String selectorCruce,float alfa) {

        Cromosoma mejorCromosomaGlobal = new Cromosoma();
        Cromosoma mejorGeneracion = new Cromosoma();
        mejorGeneracion.setCoste(Double.MAX_VALUE);
        Cromosoma[] poblacionInicial = new Cromosoma[poblacion];
        Cromosoma[] nuevaPoblacion = new Cromosoma[poblacion];
        generaPoblacionInicial(poblacionInicial, poblacion, dimension, valorMin, valorMax, funcion);
        mejorCromosomaGlobal = new Cromosoma(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
       // mejorGeneracion = new Cromosoma(poblacionInicial[mejorCromosoma(poblacionInicial, dimension)]);
        int it = 0;
        while (it < evaluaciones) {
            Cromosoma[] mutados = new Cromosoma[poblacion];
            for (int i = 0; i < poblacion; i++) {
               mutados[i]=new Cromosoma(mutacion(poblacionInicial[i], (float) probabilidadMutacion,dimension,valorMin,valorMax,funcion));
            }
            int nuevosHijos = 0;
            while (nuevosHijos < poblacion) {
                int aleatorio = rand.Randint(0, poblacion - 1);
                int aleatorio2 = rand.Randint(0, poblacion - 1);
                int aleatorio3 = rand.Randint(0, poblacion - 1);
                if (distintos(nuevosHijos,aleatorio,aleatorio2,aleatorio3)) {
                    Cromosoma objetivo= new Cromosoma(mutados[encuentraObjetivo(mutados,nuevosHijos,aleatorio,aleatorio2,aleatorio3)]);
                    float F=rand.Randfloat((float) 0, (float) 1.01);
                    double [] cromfinal= new double[dimension];
                    for (int i = 0; i < dimension; i++) {
                        float eleccion = rand.Randfloat((float) 0, (float) 1.01);
                        if(eleccion<alfa){
                            cromfinal[i]=objetivo.getIndividuosIndice(i);
                        }else{
                            cromfinal[i]=recombinacion(mutados[nuevosHijos].getIndividuosIndice(i),F,mutados[aleatorio].getIndividuosIndice(i),mutados[aleatorio2].getIndividuosIndice(i),valorMax,valorMin);
                        }
                    }


                   nuevosHijos++;
                }
            }
            boolean encontrado = encuentraElite(nuevaPoblacion, mejorGeneracion, poblacion, dimension);
            if (!encontrado) {
                //hacer el reemplazo
                int ele = torneo(nuevaPoblacion, 4, poblacion, "peor");
                nuevaPoblacion[ele] = new Cromosoma(mejorGeneracion);
            }
            int nuevomejor = mejorCromosoma(nuevaPoblacion, poblacion);
            mejorGeneracion = new Cromosoma(nuevaPoblacion[nuevomejor]);
            if (mejorGeneracion.getCoste() < mejorCromosomaGlobal.getCoste()) {
                mejorCromosomaGlobal = new Cromosoma(mejorGeneracion);
            }
            it++;
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
        int[] vector = new int[poblacion];
        for (int i = 0; i < poblacion; i++) {
            vector[i] = i;
        }
        int[] elegidos = new int[tamTorneo];
        for (int i = 0; i < tamTorneo; i++) {
            int aleatorio1 = rand.Randint(0, poblacion - 1);
            elegidos[i] = aleatorio1;
        }
        boolean norepetidos = true;
        while (norepetidos) {
            int repetido = comprueba(elegidos);
            if (repetido == -1) {
                norepetidos = false;
                break;
            }
            elegidos[repetido] = rand.Randint(0, poblacion - 1);
        }
        for (int i = 0; i < tamTorneo; i++) {
            torneoCromosomas[i] = cromosomas[elegidos[i]];
        }
        int ele;
        if (selector == "mejor") {
            ele = mejorCromosoma(torneoCromosomas, tamTorneo);
        } else {
            ele = peorCromosoma(torneoCromosomas, tamTorneo);
        }

        return ele;
    }

    int comprueba(int[] elementos) {
        for (int i = 0; i < elementos.length; i++) {
            for (int j = i + 1; j < elementos.length; j++) {
                if (elementos[i] == elementos[j]) {
                    return j;
                }
            }
        }
        return -1;
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
        hijo = new Cromosoma(aux, evaluacion(aux, funcion));
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
