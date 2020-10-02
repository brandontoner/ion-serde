package com.brandontoner.ion.serde.testtypes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DoubleTestClass {
    private final double aDouble;
    private final Double aBoxedDouble;
    private final Map<Double, Double> aDoubleDoubleMap;
    private final List<Double> aDoubleList;
    private final Double[] aBoxedDoubleArray;
    private final double[] aDoubleArray;
    private final BigDecimal aBigDecimal;

    public DoubleTestClass(final double aDouble,
                           final Double aBoxedDouble,
                           final Map<Double, Double> aDoubleDoubleMap,
                           final List<Double> aDoubleList,
                           final Double[] aBoxedDoubleArray,
                           final double[] aDoubleArray,
                           final BigDecimal aBigDecimal) {
        this.aDouble = aDouble;
        this.aBoxedDouble = aBoxedDouble;
        this.aDoubleDoubleMap = aDoubleDoubleMap;
        this.aDoubleList = aDoubleList;
        this.aBoxedDoubleArray = aBoxedDoubleArray;
        this.aDoubleArray = aDoubleArray;
        this.aBigDecimal = aBigDecimal;
    }

    public double getADouble() {
        return aDouble;
    }

    public Double getABoxedDouble() {
        return aBoxedDouble;
    }

    public Map<Double, Double> getADoubleDoubleMap() {
        return aDoubleDoubleMap;
    }

    public List<Double> getADoubleList() {
        return aDoubleList;
    }

    public Double[] getABoxedDoubleArray() {
        return aBoxedDoubleArray;
    }

    public double[] getADoubleArray() {
        return aDoubleArray;
    }

    public BigDecimal getABigDecimal() {
        return aBigDecimal;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoubleTestClass)) {
            return false;
        }
        DoubleTestClass that = (DoubleTestClass) o;
        return Double.compare(that.aDouble, aDouble) == 0
               && Objects.equals(aBoxedDouble, that.aBoxedDouble)
               && Objects.equals(aDoubleDoubleMap, that.aDoubleDoubleMap)
               && Objects.equals(aDoubleList, that.aDoubleList)
               && Arrays.equals(aBoxedDoubleArray, that.aBoxedDoubleArray)
               && Arrays.equals(aDoubleArray, that.aDoubleArray)
               && Objects.equals(aBigDecimal, that.aBigDecimal);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(aDouble, aBoxedDouble, aDoubleDoubleMap, aDoubleList, aBigDecimal);
        result = 31 * result + Arrays.hashCode(aBoxedDoubleArray);
        result = 31 * result + Arrays.hashCode(aDoubleArray);
        return result;
    }

    @Override
    public String toString() {
        return "DoubleTestClass{"
               + "aDouble="
               + aDouble
               + ", aBoxedDouble="
               + aBoxedDouble
               + ", aDoubleDoubleMap="
               + aDoubleDoubleMap
               + ", aDoubleList="
               + aDoubleList
               + ", aBoxedDoubleArray="
               + Arrays.toString(aBoxedDoubleArray)
               + ", aDoubleArray="
               + Arrays.toString(aDoubleArray)
               + ", aBigDecimal="
               + aBigDecimal
               + '}';
    }
}
