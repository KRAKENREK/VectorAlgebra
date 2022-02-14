package krakernek.lib.vector;

import krakernek.lib.DimData;
import krakernek.lib.Pair;
import krakernek.lib.point.Point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Vector {

    public static Vector ZERO = new Vector(Point.ZERO, Point.ZERO);

    private DimData cords;
    private Point start;
    private Point end;

    public Vector(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.cords = start.getOffsets(end);
    }

    public Vector(Point start, DimData cords) {
        this.start = start;
        this.cords = cords;
        this.end = start.newByOffset(cords.getMap());
    }

    public Vector(Point start, Map<Integer, BigDecimal> map) {
        this.start = start;
        this.cords = new DimData(map);
        this.end = start.newByOffset(map);
    }

    public void setStart(Point point) {
        start = point;
        end = point.newByOffset(cords.getMap());
    }

    public void setEnd(Point point) {
        end = point;
        start = point.newByOffset(cords.negate().getMap());
    }

    public Vector add(Vector other) {
        return new Vector(start, cords.newByOffset(other.cords.getMap()));
    }

    public Vector subtract(Vector other) {
        return new Vector(start, cords.newByOffset(other.cords.negate().getMap()));
    }

    public Vector multiply(BigDecimal num) {return new Vector(start, cords.newByMultiplier(num).getMap());}

    public Vector divide(BigDecimal num) {return new Vector(start, cords.newByDivider(num).getMap());}

    public boolean isCollinear(Vector other) {
        if (cords.getInitializedIndexes().size() == 0) {
            return true;
        }
        boolean ret = cords.getInitializedIndexes().equals(other.cords.getInitializedIndexes());
        if (cords.getInitializedIndexes().size() == 1) {
            return ret;
        } else {
            ArrayList<Pair<BigDecimal, BigDecimal>> list = new ArrayList<>();
            if (ret) {
                cords.getInitializedIndexes().stream().forEach(index -> list.add(index, new Pair<>(cords.getCoordinate(index).abs(), other.cords.getCoordinate(index).abs())));
                AtomicReference<BigDecimal> coef1 = new AtomicReference<>(null);
                AtomicReference<BigDecimal> coef2 = new AtomicReference<>(null);
                list.stream().forEach(pair -> {
                    coef1.set((coef1.get() == null) ? pair.getFirst() : coef1.get().divide(pair.getFirst(), cords.getContext()));
                    coef2.set((coef2.get() == null) ? pair.getSecond() : coef2.get().divide(pair.getSecond(), cords.getContext()));
                });
                return (coef1.get().compareTo(coef2.get()) == 0);
            }
            return false;
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "cords=" + cords +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
