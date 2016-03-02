package no.emagnus.tsp;

public class TspDataPoint {

    public double x;
    public double y;

    public TspDataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TspDataPoint(" + x + "," + y + ")";
    }
}
