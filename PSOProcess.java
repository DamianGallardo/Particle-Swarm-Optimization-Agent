import java.util.Random;
import java.util.Vector;

public class PSOProcess implements PSOConstants {
    private Vector<Particle> swarm = new Vector<Particle>();
    private double[] pBest = new double[SWARM_SIZE];
    private Vector<Location> pBestLocation = new Vector<Location>();
    private double gBest;
    private Location gBestLocation;
    private double[] fitnessValueList = new double[SWARM_SIZE];

    Random generator = new Random();

    public void execute() {
        initializeSwarm();
        // Imprimir las locaciones de cada particula antes del algoritmo
        for (int j = 0; j < swarm.size(); j++)
            System.out.println( j + ": " + swarm.get(j).toString());
        updateFitnessList();

        for(int i=0; i<SWARM_SIZE; i++) {
            pBest[i] = fitnessValueList[i];
            pBestLocation.add(swarm.get(i).getLocation());
        }

        int t = 0;
        double w;
        double err = 999;

        while(t < MAX_ITERATION && err > ProblemSet.ERR_TOLERANCE) {
            // step 1 - Actualizar pBest
            for(int i=0; i<SWARM_SIZE; i++) {
                if(fitnessValueList[i] < pBest[i]) {
                    pBest[i] = fitnessValueList[i];
                    pBestLocation.set(i, swarm.get(i).getLocation());
                }
            }

            // step 2 - Actualizar gBest
            int bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
            if(t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
                gBest = fitnessValueList[bestParticleIndex];
                gBestLocation = swarm.get(bestParticleIndex).getLocation();
            }

            w = W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);

            for(int i=0; i<SWARM_SIZE; i++) {
                double r1 = generator.nextDouble();
                double r2 = generator.nextDouble();

                Particle p = swarm.get(i);


                // step 3 - Actualizar velocity
                double[] newVel = new double[PROBLEM_DIMENSION];
                for (int j=0; j < PROBLEM_DIMENSION; j++) {
                    newVel[j] = (w * p.getVelocity().getPos()[j]) +
                            (r1 * C1) * (pBestLocation.get(i).getLoc()[j] - p.getLocation().getLoc()[j]) +
                            (r2 * C2) * (gBestLocation.getLoc()[j] - p.getLocation().getLoc()[j]);
                }
                Velocity vel = new Velocity(newVel);
                p.setVelocity(vel);

                // step 4 - Actualizar location
                double[] newLoc = new double[PROBLEM_DIMENSION];
                for (int j=0; j < PROBLEM_DIMENSION; j++) {
                    newLoc[j] = p.getLocation().getLoc()[j] + newVel[j];
                }
                Location loc = new Location(newLoc);
                p.setLocation(loc);
            }

            err = ProblemSet.evaluate(gBestLocation) - 0; // Minimizar la funcion significa que se esta acercando a 0


            System.out.println("ITERATION " + t + ": ");

            // Imprimir fitness value o locaiones de cada particula
            for (int j = 0; j < swarm.size(); j++)
                System.out.println( j + ": " + swarm.get(j).toString());

            System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
            System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
            System.out.println("     Best Z: " + gBestLocation.getLoc()[2]);
            System.out.println("     Value: " + ProblemSet.evaluate(gBestLocation));

            t++;
            updateFitnessList();
        }

        System.out.println("\nSolution found at iteration " + (t - 1) + ", the solutions is:");
        System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
        System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
        System.out.println("     Best Z: " + gBestLocation.getLoc()[2]);
    }

    public void initializeSwarm() {
        Particle p;

        double [][] dataSet = {{41.9,  43.4,  43.9,  44.5,  47.3,  47.5,  47.9,  50.2,  52.8,  53.2,  56.7,  57.0,  63.5,  65.3,  71.1,  77.0,  77.8 },  //x1
                                {29.1,  29.3,  29.5,  29.7,  29.9,  30.3,  30.5,  30.7,  30.8,  30.9,  31.5,  31.7,  31.9,  32.0,  32.1,  32.5,  32.9 },  //x2
                                {251.3, 251.3, 248.3, 267.5, 273.0, 276.5, 270.3, 274.9, 285.0, 290.0, 297.0, 302.5, 304.5, 309.3, 321.7, 330.7, 349.0}}; //x3

        for(int i=0; i<SWARM_SIZE; i++) {
            p = new Particle();

            double[] loc = new double[PROBLEM_DIMENSION];
            for (int j=0; j < PROBLEM_DIMENSION; j++) {
                loc[j] = dataSet[j][i]; // Dataset definido
//                loc[j] = ProblemSet.LOC_X_LOW + generator.nextDouble() * (ProblemSet.LOC_X_HIGH - ProblemSet.LOC_X_LOW);  // Dataset con valores random
            }
            Location location = new Location(loc);

            // Velocidades definidas aleatoriamente en el rango definido en Problem set
            double[] vel = new double[PROBLEM_DIMENSION];
            for (int j=0; j < PROBLEM_DIMENSION; j++)
                vel[j] = ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);
            Velocity velocity = new Velocity(vel);

            p.setLocation(location);
            p.setVelocity(velocity);
            swarm.add(p);
        }
    }

    public void updateFitnessList() {
        for(int i=0; i<SWARM_SIZE; i++) {
            fitnessValueList[i] = swarm.get(i).getFitnessValue();
        }
    }
}