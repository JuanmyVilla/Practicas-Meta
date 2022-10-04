public class Evaluacion {

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
        double sum1=0.0;
        double sum2=0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sum2+= ((j+beta)*(Math.pow(solucion[j],i)-(1/Math.pow(j,i))));
            }
            sum1+= Math.pow(sum2,2);
        }
        return sum1;
    }
    public double rastringin(double [] solucion){
        double sum=0.0;
        double sol=0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            sum+= ((Math.pow(solucion[i],2))-10*Math.cos(2*Math.PI*solucion[i]));
        }
        sol= 10*dimension + sum;
        return sol;
    }
    public double trid(double [] solucion){
        double sum=0.0;
        double sum1=0.0;
        double sol=0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            sum+= Math.pow(solucion[i] -1 ,2);
            sum1 += solucion[i]*solucion[i-1];

        }
        sol=sum - sum1;
        return sol;
    }
    public double rotatedHH(double[] solucion) {
        double sum1 = 0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < solucion.length; j++) {
                sum1 += Math.pow(solucion[j], 2);
            }
        }
        return sum1;
    }
    public double rosenbrock(double[] solucion) {
        double sum = 0.0;
        double sol = 0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension - 1; i++) {
            sum += (100 * Math.pow((solucion[i + 1] - (Math.pow(solucion[i], 2))), 2) + Math.pow((solucion[i] - 1), 2));
        }
        sol = sum;
        return sol;
    }
    public double dixon(double[] solucion) {
        double sum = 0.0;
        double sol = 0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            sum += i * Math.pow((Math.pow(2*solucion[i],2)-solucion[i-1]),2);
        }
        sol = Math.pow((solucion[1] - 1),2) + sum ;
        return sol;
    }
    public double rotatedHE(double [] solucion){
        double sum1=0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < solucion.length; j++) {
                sum1+= Math.pow(solucion[j],2);
            }
        }
        return sum1;
    }
    public double schewefel(double [] solucion){
        double sum=0.0;
        double sol=0.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            sum+=(solucion[i]*Math.sin(Math.sqrt(Math.abs(solucion[i]))));
        }
        sol=418.9829*dimension-sum;
        return sol;
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
        double sum=0.0;
        double sum2=1.0;
        int dimension= solucion.length;
        for (int i = 0; i < dimension; i++) {
            sum+= (Math.pow(solucion[i],2))/4000;
            sum2*= (Math.cos((solucion[i])/Math.sqrt(i)))+1;
        }
        return (sum-sum2);
    }
}
