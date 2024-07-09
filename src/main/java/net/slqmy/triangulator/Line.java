package net.slqmy.triangulator;

import net.slqmy.triangulator.util.VectorUtil;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;

public class Line {

    private final Vector2d startingPoint;
    private final Vector2d directionVector;

    public Vector2d getStartingPoint() {
        return startingPoint;
    }

    public Vector2d getDirectionVector() {
        return directionVector;
    }

    public Line(Vector2d startingPoint, Vector2d direcitonVector) throws ZeroVectorException {
        if (VectorUtil.isZeroVector(direcitonVector)) {
            throw new ZeroVectorException();
        }

        this.startingPoint = startingPoint;
        this.directionVector = direcitonVector;
    }

    @NotNull public Vector2d getPointForParameterValue(double parameterValue) {
        Vector2d startingPointClone = VectorUtil.cloneVector(startingPoint);
        Vector2d directionVectorClone = VectorUtil.cloneVector(directionVector); 

        return startingPointClone.add(directionVectorClone.mul(parameterValue));
    }

    public boolean includesPoint(Vector2d point) {
        Vector2d clonedPoint = new Vector2d(point.x, point.y);

        return clonedPoint.equals(startingPoint) || VectorUtil.areLinearlyDependent(clonedPoint.sub(startingPoint), directionVector);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (super.equals(otherObject)) {
            return true;
        }

        if (otherObject instanceof Line otherLine) {
            if (startingPoint.equals(otherLine.startingPoint) && directionVector.equals(otherLine.directionVector) ) {
                return true;
            }

            return includesPoint(otherLine.startingPoint) && VectorUtil.areLinearlyDependent(directionVector, otherLine.directionVector);
        }

        return false;
    }

    @Override
    public String toString() {
        return "{(" + startingPoint.x + ", " + startingPoint.y + ") + t × (" + directionVector.x + ", " + directionVector.y + ") | t ∈ R}";
    }

    public class ZeroVectorException extends Exception {
        ZeroVectorException() {
            super("The direction vector must not be the zero vector (0, 0)!");
        }
    }
}
