package krakernek.lib.point;


import krakernek.lib.DimData;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.Set;


public class Point implements Cloneable {
    public static Point ZERO = new Point(DimData.ZERO);

    private DimData cords;

    ////////////////////////////////
    //Constructors
    ////////////////////////////////

    public Point(DimData cords) {
        this.cords = cords;
    }

    public Point(Map<Integer, BigDecimal> cords) {
        this.cords = new DimData(cords);
    }

    public Point(Map<Integer, BigDecimal> cords, MathContext context) {
        this.cords = new DimData(cords, context);
    }

    ////////////////////////////////
    //Indexes getters Methods
    ////////////////////////////////

    public Set<Integer> getInitializedIndexes() {
        return cords.getInitializedIndexes();
    }

    ////////////////////////////////
    //Basic Coordinates Methods
    ////////////////////////////////

    public DimData getCoordinates() {
        return cords;
    }

    public BigDecimal getCoordinate(int index) {
        return cords.getCoordinate(index);
    }

    public void setCoordinate(int index, @NotNull BigDecimal value) {
        cords.setCoordinate(index, value);
    }

    public void offsetCoordinate(int index, @NotNull BigDecimal value) {
        cords.offsetCoordinate(index, value);
    }

    public void multiplyCoordinate(int index, @NotNull BigDecimal value) {
        cords.multiplyCoordinate(index, value);
    }

    public void divideCoordinate(int index, @NotNull BigDecimal value) {
        cords.divideCoordinate(index, value);
    }

    ////////////////////////////////
    //Basic Coordinates Methods
    ////////////////////////////////

    public BigDecimal minCoordinate(int index, @NotNull Point other) {
        return cords.minCoordinate(index, other.getCoordinates());
    }

    public BigDecimal maxCoordinate(int index, @NotNull Point other) {
        return cords.maxCoordinate(index, other.getCoordinates());
    }

    public DimData minCoordinates(@NotNull Point other) {
        return cords.minCoordinates(other.getCoordinates());
    }

    public DimData maxCoordinates(@NotNull Point other) {
        return cords.maxCoordinates(other.getCoordinates());
    }

    ////////////////////////////////
    //Compare methods
    ////////////////////////////////

    public int compareByIndex(int index, Point other) {
        return cords.compareByIndex(index, other.getCoordinates());
    }

    public DimData compareByIndexes(Set<Integer> indexes, Point other) {
        return cords.compareByIndexes(indexes, other.getCoordinates());
    }

    public DimData compare(Point other) {
        return cords.compare(other.getCoordinates());
    }

    ////////////////////////////////
    //Offset getters
    ////////////////////////////////

    public BigDecimal getOffset(int index, @NotNull Point other) {
        return cords.getOffset(index, other.getCoordinates());
    }

    public DimData getOffsets(@NotNull Set<Integer> indexes, @NotNull Point other) {
        return cords.getOffsets(indexes, other.getCoordinates());
    }

    public DimData getOffsets(@NotNull Point other) {
        return cords.getOffsets(other.getCoordinates());
    }

    ////////////////////////////////
    //New methods
    ////////////////////////////////

    public Point newByOffset(int index, @NotNull BigDecimal value) {
        return new Point(cords.newByOffset(index, value));
    }

    public Point newByOffset(@NotNull BigDecimal value) {
        return new Point(cords.newByOffset(value));
    }

    public Point newByOffset(@NotNull Map<Integer, BigDecimal> offsets) {
        return new Point(cords.newByOffset(offsets));
    }

    public Point newByMultiplier(int index, @NotNull BigDecimal value) {
        return new Point(cords.newByMultiplier(index, value));
    }

    public Point newByMultiplier(@NotNull BigDecimal value) {
        return new Point(cords.newByMultiplier(value));
    }

    public Point newByMultiplier(@NotNull Map<Integer, BigDecimal> offsets) {
        return new Point(cords.newByMultiplier(offsets));
    }

    public Point newByDivider(int index, @NotNull BigDecimal value) {
        return new Point(cords.newByDivider(index, value));
    }

    public Point newByDivider(@NotNull BigDecimal value) {
        return new Point(cords.newByDivider(value));
    }

    public Point newByDivider(@NotNull Map<Integer, BigDecimal> offsets) {
        return new Point(cords.newByDivider(offsets));
    }

    ////////////////////////////////
    //Overrides from object
    ////////////////////////////////

    @Override
    public Point clone() {
        return new Point(cords.clone());
    }

    @Override
    public String toString() {
        return "Point{" +
                "cords=" + cords +
                '}';
    }
}

