package com.brandontoner.ion.serde.testtypes;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IntTestClass {
    private final int anInt;
    private final Integer anInteger;
    private final Map<Integer, Integer> anIntegerIntegerMap;
    private final List<Integer> anIntegerList;
    private final Integer[] anIntegerArray;
    private final int[] anIntArray;
    private final BigInteger aBigInteger;

    public IntTestClass(final int anInt,
                        final Integer anInteger,
                        final Map<Integer, Integer> anIntegerIntegerMap,
                        final List<Integer> anIntegerList,
                        final Integer[] anIntegerArray,
                        final int[] anIntArray,
                        final BigInteger aBigInteger) {
        this.anInt = anInt;
        this.anInteger = anInteger;
        this.anIntegerIntegerMap = anIntegerIntegerMap;
        this.anIntegerList = anIntegerList;
        this.anIntegerArray = anIntegerArray;
        this.anIntArray = anIntArray;
        this.aBigInteger = aBigInteger;
    }

    public int getAnInt() {
        return anInt;
    }

    public Integer getAnInteger() {
        return anInteger;
    }

    public Map<Integer, Integer> getAnIntegerIntegerMap() {
        return anIntegerIntegerMap;
    }

    public List<Integer> getAnIntegerList() {
        return anIntegerList;
    }

    public Integer[] getAnIntegerArray() {
        return anIntegerArray;
    }

    public int[] getAnIntArray() {
        return anIntArray;
    }

    public BigInteger getABigInteger() {
        return aBigInteger;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntTestClass)) {
            return false;
        }
        IntTestClass that = (IntTestClass) o;
        return anInt == that.anInt
               && Objects.equals(anInteger, that.anInteger)
               && Objects.equals(anIntegerIntegerMap,
                                 that.anIntegerIntegerMap)
               && Objects.equals(anIntegerList, that.anIntegerList)
               && Arrays.equals(anIntegerArray, that.anIntegerArray)
               && Arrays.equals(anIntArray, that.anIntArray)
               && Objects.equals(aBigInteger, that.aBigInteger);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(anInt, anInteger, anIntegerIntegerMap, anIntegerList, aBigInteger);
        result = 31 * result + Arrays.hashCode(anIntegerArray);
        result = 31 * result + Arrays.hashCode(anIntArray);
        return result;
    }

    @Override
    public String toString() {
        return "IntTestClass{"
               + "anInt="
               + anInt
               + ", anInteger="
               + anInteger
               + ", anIntegerIntegerMap="
               + anIntegerIntegerMap
               + ", anIntegerList="
               + anIntegerList
               + ", anIntegerArray="
               + Arrays.toString(anIntegerArray)
               + ", anIntArray="
               + Arrays.toString(anIntArray)
               + ", aBigInteger="
               + aBigInteger
               + '}';
    }
}
