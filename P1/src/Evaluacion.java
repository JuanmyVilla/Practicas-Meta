public class Evaluacion {

    private double pi= Math.PI;

    public double ackley(double [] solucion){
        int a=20;
        double b= 0.2;
        double c= 2*Math.PI;
        double score;
        double sum1=0.0;
        double sum2=0.0;
        int dimension= solucion.length;

        for (int i = 0; i < dimension; i++) {
            sum1 += Math.pow(solucion[i],2);
            sum2 += Math.cos(c*solucion[i]);
        }

        score= -20*Math.exp(-b * Math.sqrt((1.0/dimension)*sum1)) - Math.exp((1.0/dimension)*sum2) + a + Math.exp(1);

        return score;
    }
    public double perm(double [] solucion,int beta){
        double d = solucion.length;
        int b=10;
        double outer = 0;

        for (int i=0; i<d; i++){
            double inner=0;
            for (int j=0; j<d; j++){
                double xj = solucion[j];
                inner += (j+1+b)*(Math.pow(xj,i+1)-(1/Math.pow(j+1,i+1)));
            }
            outer += inner*inner;
        }

        return outer;
    }
    public double rastringin(double [] solucion){
        double d = solucion.length;
        double sum = 0;
        for (int i=0; i<d; i++){
            double xi = solucion[i];
            sum += ((xi*xi) - 10*Math.cos(2*pi*xi));
        }

        return 10*d + sum;
    }
    public double trid(double [] solucion){
        double d = solucion.length;
        double sum2 =0;
        double sum1 = Math.pow(solucion[1]-1,2);

        for (int ii=1; ii<d; ii++){
            double xi = solucion[ii];
            double xold = solucion[ii-1];
            sum1 += Math.pow((xi-1),2);
            sum2 += xi*xold;
        }

        return sum1 - sum2;
    }
    public double rosenbrock(double[] solucion) {
        double d = solucion.length;
        double sum =0;

        for (int ii=0; ii<(d-1); ii++){
            double xi = solucion[ii];
            double xnext = solucion[ii+1];
            double nuevo = 100* Math.pow(xnext-(xi*xi),2) + Math.pow((xi-1),2);
            sum = sum + nuevo;
        }

        return sum;
    }
    public double dixon(double[] solucion) {

        double d = solucion.length;
        double x1 = solucion[0];

        double term1 = Math.pow((x1-1),2);

        double sum = 0;
        for (int ii = 1; ii < d; ii++){
            double xi = solucion[ii];
            double xold = solucion[ii-1];
            double nuevo = ii * Math.pow((Math.pow (2*xi,2) - xold),2);
            sum = sum + nuevo;
        }

        return term1 + sum;
    }
    public double rotated(double [] solucion){
        double d = solucion.length;
        double outer = 1.0;

        for (int i=0; i<d; i++){
            double inner=0;
            for (int j=0; j<i; j++){
                double xj = solucion[j];
                inner += xj*xj;
            }
            outer += outer*inner;
        }

        return outer;
    }
    public double schewefel(double [] solucion){
        double d = solucion.length;

        double sum = 0;
        for (int i=0; i<d; i++){
            double xi = solucion[i];
            sum = sum + xi*Math.sin(Math.sqrt(Math.abs(xi)));
        }

        return  418.9829*d - sum;
    }
    public double michalewicz(double [] solucion,int m){
        double sum=0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            sum+= ((Math.sin(solucion[i]))*Math.pow(Math.sin(i*Math.pow(i*solucion[i],2)/Math.PI),2*m));
        }
        return -sum;
    }
    public double griewank(double [] solucion){
        double sum = 0;
        double prod = 1;

        int d = solucion.length;

        for (int i = 0; i < d; i++) {
            sum += ((solucion[i] * solucion[i]) /4000);
            prod *= Math.cos(solucion[i] / Math.sqrt(i+1));
        }
        return sum - prod + 1;
    }
}
