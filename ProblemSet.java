public class ProblemSet {
    // Rango para el valor de las locaciones al generarlas aleatoriamente
//    public static final double LOC_X_LOW = 1;
//    public static final double LOC_X_HIGH = 4;
//    public static final double LOC_Y_LOW = -1;
//    public static final double LOC_Y_HIGH = 1;
    public static final double VEL_LOW = 0;
    public static final double VEL_HIGH = 1;

    public static final double ERR_TOLERANCE = 0; // Mientras mas baja tolerancia mas exacto el resultado
                                                    // Pero el numero de iteraciones incrementa

    public static double evaluate(Location location) {
        double result = 0;
        double x = location.getLoc()[0]; // x1
        double y = location.getLoc()[1]; // x2
        double z = location.getLoc()[2]; // x3

        //f(x) = 10*(x - 1)^2 + 20*(y-2)^2 + 30*(z-3)^2  --- Formula
        result = 10*(Math.pow(x - 1, 2)) + (20*(Math.pow(y - 2, 2))) + (30*(Math.pow(z-3,2)));


        return result;
    }
}
