public class Particle {
    private double fitnessValue;
    private Velocity velocity;
    private Location location;

    public Particle() {
        super();
    }

    public Particle(double fitnessValue, Velocity velocity, Location location) {
        super();
        this.fitnessValue = fitnessValue;
        this.velocity = velocity;
        this.location = location;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getFitnessValue() {
        fitnessValue = ProblemSet.evaluate(location);
        return fitnessValue;
    }

    public String toString() {
        String result = "";
        //Fitness value
        //result = fitnessValue + "";
        //Location
        for ( int i = 0; i < location.getLoc().length; i++) {
            result += location.getLoc()[i] + " | ";
        }
        return result;
    }
}