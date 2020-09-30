package com.brandontoner.ion.serde.testtypes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LongTestClass {
    private final long aLong;
    private final Long aBoxedLong;
    private final Map<Long, Long> aLongLongMap;
    private final List<Long> aLongList;
    private final Long[] aBoxedLongArray;
    private final long[] aLongArray;

    public LongTestClass(final long aLong,
                         final Long aBoxedLong,
                         final Map<Long, Long> aLongLongMap,
                         final List<Long> aLongList,
                         final Long[] aBoxedLongArray,
                         final long[] aLongArray) {
        this.aLong = aLong;
        this.aBoxedLong = aBoxedLong;
        this.aLongLongMap = aLongLongMap;
        this.aLongList = aLongList;
        this.aBoxedLongArray = aBoxedLongArray;
        this.aLongArray = aLongArray;
    }

    public long getALong() {
        return aLong;
    }

    public Long getABoxedLong() {
        return aBoxedLong;
    }

    public Map<Long, Long> getALongLongMap() {
        return aLongLongMap;
    }

    public List<Long> getALongList() {
        return aLongList;
    }

    public Long[] getABoxedLongArray() {
        return aBoxedLongArray;
    }

    public long[] getALongArray() {
        return aLongArray;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongTestClass)) {
            return false;
        }
        LongTestClass that = (LongTestClass) o;
        return aLong == that.aLong
               && Objects.equals(aBoxedLong, that.aBoxedLong)
               && Objects.equals(aLongLongMap,
                                 that.aLongLongMap)
               && Objects.equals(aLongList, that.aLongList)
               && Arrays.equals(aBoxedLongArray, that.aBoxedLongArray)
               && Arrays.equals(aLongArray, that.aLongArray);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(aLong, aBoxedLong, aLongLongMap, aLongList);
        result = 31 * result + Arrays.hashCode(aBoxedLongArray);
        result = 31 * result + Arrays.hashCode(aLongArray);
        return result;
    }

    @Override
    public String toString() {
        return "LongTestClass{"
               + "aLong="
               + aLong
               + ", aBoxedLong="
               + aBoxedLong
               + ", aLongLongMap="
               + aLongLongMap
               + ", aLongList="
               + aLongList
               + ", aBoxedLongArray="
               + Arrays.toString(aBoxedLongArray)
               + ", aLongArray="
               + Arrays.toString(aLongArray)
               + '}';
    }
}
