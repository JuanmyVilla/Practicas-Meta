public class Cromosoma {

    private double[] individuos;
    private double coste;

    public Cromosoma() {

    }

    public Cromosoma(double[] individuos, double coste) {
        this.individuos = individuos;
        this.coste = coste;
    }

    public Cromosoma(Cromosoma cromosoma) {
        this.individuos = cromosoma.individuos;
        this.coste = cromosoma.coste;
    }

    public double[] getIndividuos() {
        return individuos;
    }

    public double getIndividuosIndice(int i) {
        return individuos[i];
    }

    public void setIndividuos(double[] individuos) {
        this.individuos = individuos;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public void setIndividuos(int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
    }

}
