package krakernek.lib;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class DimData implements Cloneable {

    public static final DimData ZERO = new DimData(new HashMap<>());

    private BigDecimal filler = BigDecimal.ZERO;

    private Map<Integer, BigDecimal> coordinates;
    private MathContext context = MathContext.DECIMAL128;


    ////////////////////////////////
    //Constructors
    ////////////////////////////////

    public DimData(@NotNull Map<Integer, BigDecimal> coordinates) {
        this.coordinates = coordinates;
    }

    public DimData(@NotNull Map<Integer, BigDecimal> coordinates, MathContext context) {
        this.coordinates = coordinates;
        this.context = context;
    }

    public Map<Integer, BigDecimal> getMap() {
        return coordinates;
    }

    public MathContext getContext() {
        return context;
    }

    public DimData negate() {
        DimData clone = clone();
        getInitializedIndexes().stream().forEach(index -> {
            clone.setCoordinate(index, getCoordinate(index).negate());
        });
        return clone;
    }

    ////////////////////////////////
    //Indexes getters method
    ////////////////////////////////

    public Set<Integer> getInitializedIndexes() {
        return coordinates.keySet();
    }

    private Stream<Integer> getDoubleIndexStream(@NotNull DimData other) {
        Set<Integer> indexes = new HashSet<>();
        indexes.addAll(this.getInitializedIndexes());
        indexes.addAll(other.getInitializedIndexes());
        return indexes.stream();
    }

    ////////////////////////////////
    //Basic Coordinates Methods
    ////////////////////////////////

    public BigDecimal getCoordinate(int index) {
        BigDecimal cord = coordinates.get(index);
        if (cord == null) {
            return filler;
        } else {
            return cord;
        }
    }

    public void setCoordinate(int index, @NotNull BigDecimal value) {
        coordinates.put(index, value);
    }

    public void offsetCoordinate(int index, @NotNull BigDecimal value) {
        BigDecimal cord = getCoordinate(index);
        setCoordinate(index, cord.add(value, context));
    }

    public void multiplyCoordinate(int index, @NotNull BigDecimal value) {
        BigDecimal cord = getCoordinate(index);
        setCoordinate(index, cord.multiply(value, context));
    }

    public void divideCoordinate(int index, @NotNull BigDecimal value) {
        BigDecimal cord = getCoordinate(index);
        setCoordinate(index, cord.divide(value, context));
    }

    ////////////////////////////////
    //Min/Max coordinates methods
    ////////////////////////////////

    public BigDecimal minCoordinate(int index, @NotNull DimData other) {
        return getCoordinate(index).min(other.getCoordinate(index));
    }

    public BigDecimal maxCoordinate(int index, @NotNull DimData other) {
        return getCoordinate(index).max(other.getCoordinate(index));
    }

    public DimData minCoordinates(@NotNull DimData other) {
        Map<Integer, BigDecimal> ret = new HashMap<>();
        getDoubleIndexStream(other).forEach(index -> {
            ret.put(index, getCoordinate(index).min(other.getCoordinate(index)));
        });
        return new DimData(ret);
    }

    public DimData maxCoordinates(@NotNull DimData other) {
        Map<Integer, BigDecimal> ret = new HashMap<>();
        getDoubleIndexStream(other).forEach(index -> {
            ret.put(index, getCoordinate(index).max(other.getCoordinate(index)));
        });
        return new DimData(ret);
    }

    ////////////////////////////////
    //Compare methods
    ////////////////////////////////

    public int compareByIndex(int index, @NotNull DimData other) {
        return getCoordinate(index).compareTo(other.getCoordinate(index));
    }

    public DimData compareByIndexes(@NotNull Set<Integer> indexes, @NotNull DimData other) {
        Map<Integer, BigDecimal> ret = new HashMap<>();
        indexes.stream().forEach(index -> {
            ret.put(index, BigDecimal.valueOf(compareByIndex(index, other)));
        });
        return new DimData(ret);
    }

    public DimData compare(@NotNull DimData other) {
        Map<Integer, BigDecimal> ret = new HashMap<>();
        getDoubleIndexStream(other).forEach(index -> {
            ret.put(index, BigDecimal.valueOf(compareByIndex(index, other)));
        });
        return new DimData(ret);
    }

    ////////////////////////////////
    //Offset getters
    ////////////////////////////////

    public BigDecimal getOffset(int index, @NotNull DimData other) {
        return other.getCoordinate(index).subtract(getCoordinate(index));
    }

    public DimData getOffsets(@NotNull Set<Integer> indexes, @NotNull DimData other) {
        Map<Integer, BigDecimal> ret = new HashMap<>();
        indexes.stream().forEach(index -> {
            ret.put(index, getOffset(index, other));
        });
        return new DimData(ret);
    }

    public DimData getOffsets(@NotNull DimData other) {
        Map<Integer, BigDecimal> ret = new HashMap<>();
        Set<Integer> indexes = new HashSet<>();
        indexes.addAll(this.getInitializedIndexes());
        indexes.addAll(other.getInitializedIndexes());
        indexes.stream().forEach(index -> {
            ret.put(index, getOffset(index, other));
        });
        return new DimData(ret);
    }

    ////////////////////////////////
    //New methods
    ////////////////////////////////

    public DimData newByOffset(int index, @NotNull BigDecimal value) {
        DimData ret = clone();
        ret.offsetCoordinate(index, value);
        return ret;
    }

    public DimData newByOffset(@NotNull BigDecimal value) {
        DimData ret = clone();
        for (Integer key : coordinates.keySet()) {
            ret.offsetCoordinate(key, value);
        }
        ret.filler = filler.add(value);
        return ret;
    }

    public DimData newByOffset(@NotNull Map<Integer, BigDecimal> offsets) {
        DimData ret = clone();
        for (Integer key: offsets.keySet()) {
            ret.offsetCoordinate(key, offsets.get(key));
        }
        return ret;
    }

    public DimData newByMultiplier(int index, @NotNull BigDecimal value) {
        DimData ret = clone();
        ret.multiplyCoordinate(index, value);
        return ret;
    }

    public DimData newByMultiplier(@NotNull BigDecimal value) {
        DimData ret = clone();
        for (Integer key : coordinates.keySet()) {
            ret.multiplyCoordinate(key, value);
        }
        ret.filler = filler.multiply(value);
        return ret;
    }

    public DimData newByMultiplier(@NotNull Map<Integer, BigDecimal> offsets) {
        DimData ret = clone();
        for (Integer key: offsets.keySet()) {
            ret.multiplyCoordinate(key, offsets.get(key));
        }
        return ret;
    }

    public DimData newByDivider(int index, @NotNull BigDecimal value) {
        DimData ret = clone();
        ret.divideCoordinate(index, value);
        return ret;
    }

    public DimData newByDivider(@NotNull BigDecimal value) {
        DimData ret = clone();
        for (Integer key : coordinates.keySet()) {
            ret.divideCoordinate(key, value);
        }
        ret.filler = filler.divide(value);
        return ret;
    }

    public DimData newByDivider(@NotNull Map<Integer, BigDecimal> offsets) {
        DimData ret = clone();
        for (Integer key: offsets.keySet()) {
            ret.divideCoordinate(key, offsets.get(key));
        }
        return ret;
    }

    ////////////////////////////////
    //Overrides from object
    ////////////////////////////////

    @Override
    public DimData clone() {
        return new DimData(new HashMap<>(coordinates));
    }

    @Override
    public String toString() {
        return "DimData{" +
                "coordinates=" + coordinates +
                '}';
    }
}
