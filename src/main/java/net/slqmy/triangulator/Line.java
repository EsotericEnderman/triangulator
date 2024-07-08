package net.slqmy.triangulator;

public class Line {

    private double slope;
    private double yIntercept;

    private String dependentVariable;
    private String independentVariable;

    public Line(double slope, double yIntercept, String dependentVariable, String independentVariable) {
        this.slope = slope;
        this.yIntercept = yIntercept;

        this.dependentVariable = dependentVariable;
        this.independentVariable = independentVariable;
    }

    public Line(double slope, double yIntercept, String dependentVariable) {
        this(slope, yIntercept, dependentVariable, "x");
    }

    public Line(double slope, double yIntercept) {
        this(slope, yIntercept, "y");
    }

    public double getSlope() {
        return slope;
    }

    public double getyIntercept() {
        return yIntercept;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public void setyIntercept(double yIntercept) {
        this.yIntercept = yIntercept;
    }

    public String toString() {
        return dependentVariable + " = " + slope + independentVariable + " + " + yIntercept;
    }
}
