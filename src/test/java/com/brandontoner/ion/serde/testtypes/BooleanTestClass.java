package com.brandontoner.ion.serde.testtypes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BooleanTestClass {
    private final boolean aBoolean;
    private final Boolean aBoxedBoolean;
    private final Map<Boolean, Boolean> aBooleanBooleanMap;
    private final List<Boolean> aBooleanList;
    private final Boolean[] aBoxedBooleanArray;
    private final boolean[] aBooleanArray;

    public BooleanTestClass(final boolean aBoolean,
                            final Boolean aBoxedBoolean,
                            final Map<Boolean, Boolean> aBooleanBooleanMap,
                            final List<Boolean> aBooleanList,
                            final Boolean[] aBoxedBooleanArray,
                            final boolean[] aBooleanArray) {
        this.aBoolean = aBoolean;
        this.aBoxedBoolean = aBoxedBoolean;
        this.aBooleanBooleanMap = aBooleanBooleanMap;
        this.aBooleanList = aBooleanList;
        this.aBoxedBooleanArray = aBoxedBooleanArray;
        this.aBooleanArray = aBooleanArray;
    }

    public boolean getABoolean() {
        return aBoolean;
    }

    public Boolean getABoxedBoolean() {
        return aBoxedBoolean;
    }

    public Map<Boolean, Boolean> getABooleanBooleanMap() {
        return aBooleanBooleanMap;
    }

    public List<Boolean> getABooleanList() {
        return aBooleanList;
    }

    public Boolean[] getABoxedBooleanArray() {
        return aBoxedBooleanArray;
    }

    public boolean[] getABooleanArray() {
        return aBooleanArray;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BooleanTestClass)) {
            return false;
        }
        BooleanTestClass that = (BooleanTestClass) o;
        return aBoolean == that.aBoolean && Objects.equals(aBoxedBoolean, that.aBoxedBoolean) && Objects.equals(
                aBooleanBooleanMap,
                that.aBooleanBooleanMap) && Objects.equals(aBooleanList, that.aBooleanList) && Arrays.equals(
                aBoxedBooleanArray,
                that.aBoxedBooleanArray) && Arrays.equals(aBooleanArray, that.aBooleanArray);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(aBoolean, aBoxedBoolean, aBooleanBooleanMap, aBooleanList);
        result = 31 * result + Arrays.hashCode(aBoxedBooleanArray);
        result = 31 * result + Arrays.hashCode(aBooleanArray);
        return result;
    }

    @Override
    public String toString() {
        return "BooleanTestClass{"
               + "aBoolean="
               + aBoolean
               + ", aBoxedBoolean="
               + aBoxedBoolean
               + ", aBooleanBooleanMap="
               + aBooleanBooleanMap
               + ", aBooleanList="
               + aBooleanList
               + ", aBoxedBooleanArray="
               + Arrays.toString(aBoxedBooleanArray)
               + ", aBooleanArray="
               + Arrays.toString(aBooleanArray)
               + '}';
    }
}
