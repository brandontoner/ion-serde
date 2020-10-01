package com.brandontoner.ion.serde;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.Timestamp;
import com.brandontoner.ion.serde.testtypes.BlobTestClass;
import com.brandontoner.ion.serde.testtypes.BooleanTestClass;
import com.brandontoner.ion.serde.testtypes.DoubleTestClass;
import com.brandontoner.ion.serde.testtypes.IntTestClass;
import com.brandontoner.ion.serde.testtypes.LongTestClass;
import com.brandontoner.ion.serde.testtypes.StringTestClass;
import com.brandontoner.ion.serde.testtypes.TemporalTestClass;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.CharSequence;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Serializers {
    public static void serializeIntTestClass(IntTestClass v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.STRUCT);
            return;
        }
        ionWriter.stepIn(IonType.STRUCT);
        ionWriter.setFieldName("anInt");
        ionWriter.writeInt(v.getAnInt());
        ionWriter.setFieldName("anInteger");
        serializeBoxedInt(v.getAnInteger(), ionWriter);
        ionWriter.setFieldName("anIntegerIntegerMap");
        serializeIntegerIntegerMap(v.getAnIntegerIntegerMap(), ionWriter);
        ionWriter.setFieldName("anIntegerList");
        serializeIntegerList(v.getAnIntegerList(), ionWriter);
        ionWriter.setFieldName("anIntegerArray");
        serializeBoxedIntArray(v.getAnIntegerArray(), ionWriter);
        ionWriter.setFieldName("anIntArray");
        serializeIntArray(v.getAnIntArray(), ionWriter);
        ionWriter.stepOut();
    }

    public static IntTestClass deserializeIntTestClass(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        int anInt = 0;
        boolean hasAnInt = false;
        Integer anInteger = null;
        boolean hasAnInteger = false;
        Map<Integer, Integer> anIntegerIntegerMap = null;
        boolean hasAnIntegerIntegerMap = false;
        List<Integer> anIntegerList = null;
        boolean hasAnIntegerList = false;
        Integer[] anIntegerArray = null;
        boolean hasAnIntegerArray = false;
        int[] anIntArray = null;
        boolean hasAnIntArray = false;
        while (ionReader.next() != null) {
            switch (ionReader.getFieldName()) {
                case "anInt":
                    anInt = ionReader.intValue();
                    hasAnInt = true;
                    break;
                case "anInteger":
                    anInteger = deserializeBoxedInt(ionReader);
                    hasAnInteger = true;
                    break;
                case "anIntegerIntegerMap":
                    anIntegerIntegerMap = deserializeIntegerIntegerMap(ionReader);
                    hasAnIntegerIntegerMap = true;
                    break;
                case "anIntegerList":
                    anIntegerList = deserializeIntegerList(ionReader);
                    hasAnIntegerList = true;
                    break;
                case "anIntegerArray":
                    anIntegerArray = deserializeBoxedIntArray(ionReader);
                    hasAnIntegerArray = true;
                    break;
                case "anIntArray":
                    anIntArray = deserializeIntArray(ionReader);
                    hasAnIntArray = true;
                    break;
            }
        }
        ionReader.stepOut();
        return new IntTestClass(anInt, anInteger, anIntegerIntegerMap, anIntegerList, anIntegerArray, anIntArray);
    }

    public static void serializeLongTestClass(LongTestClass v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.STRUCT);
            return;
        }
        ionWriter.stepIn(IonType.STRUCT);
        ionWriter.setFieldName("aLong");
        ionWriter.writeInt(v.getALong());
        ionWriter.setFieldName("aBoxedLong");
        serializeBoxedLong(v.getABoxedLong(), ionWriter);
        ionWriter.setFieldName("aLongLongMap");
        serializeLongLongMap(v.getALongLongMap(), ionWriter);
        ionWriter.setFieldName("aLongList");
        serializeLongList(v.getALongList(), ionWriter);
        ionWriter.setFieldName("aBoxedLongArray");
        serializeBoxedLongArray(v.getABoxedLongArray(), ionWriter);
        ionWriter.setFieldName("aLongArray");
        serializeLongArray(v.getALongArray(), ionWriter);
        ionWriter.stepOut();
    }

    public static LongTestClass deserializeLongTestClass(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        long aLong = 0L;
        boolean hasALong = false;
        Long aBoxedLong = null;
        boolean hasABoxedLong = false;
        Map<Long, Long> aLongLongMap = null;
        boolean hasALongLongMap = false;
        List<Long> aLongList = null;
        boolean hasALongList = false;
        Long[] aBoxedLongArray = null;
        boolean hasABoxedLongArray = false;
        long[] aLongArray = null;
        boolean hasALongArray = false;
        while (ionReader.next() != null) {
            switch (ionReader.getFieldName()) {
                case "aLong":
                    aLong = ionReader.longValue();
                    hasALong = true;
                    break;
                case "aBoxedLong":
                    aBoxedLong = deserializeBoxedLong(ionReader);
                    hasABoxedLong = true;
                    break;
                case "aLongLongMap":
                    aLongLongMap = deserializeLongLongMap(ionReader);
                    hasALongLongMap = true;
                    break;
                case "aLongList":
                    aLongList = deserializeLongList(ionReader);
                    hasALongList = true;
                    break;
                case "aBoxedLongArray":
                    aBoxedLongArray = deserializeBoxedLongArray(ionReader);
                    hasABoxedLongArray = true;
                    break;
                case "aLongArray":
                    aLongArray = deserializeLongArray(ionReader);
                    hasALongArray = true;
                    break;
            }
        }
        ionReader.stepOut();
        return new LongTestClass(aLong, aBoxedLong, aLongLongMap, aLongList, aBoxedLongArray, aLongArray);
    }

    public static void serializeBooleanTestClass(BooleanTestClass v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.STRUCT);
            return;
        }
        ionWriter.stepIn(IonType.STRUCT);
        ionWriter.setFieldName("aBoolean");
        ionWriter.writeBool(v.getABoolean());
        ionWriter.setFieldName("aBoxedBoolean");
        serializeBoxedBoolean(v.getABoxedBoolean(), ionWriter);
        ionWriter.setFieldName("aBooleanBooleanMap");
        serializeBooleanBooleanMap(v.getABooleanBooleanMap(), ionWriter);
        ionWriter.setFieldName("aBooleanList");
        serializeBooleanList(v.getABooleanList(), ionWriter);
        ionWriter.setFieldName("aBoxedBooleanArray");
        serializeBoxedBooleanArray(v.getABoxedBooleanArray(), ionWriter);
        ionWriter.setFieldName("aBooleanArray");
        serializeBooleanArray(v.getABooleanArray(), ionWriter);
        ionWriter.stepOut();
    }

    public static BooleanTestClass deserializeBooleanTestClass(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        boolean aBoolean = false;
        boolean hasABoolean = false;
        Boolean aBoxedBoolean = null;
        boolean hasABoxedBoolean = false;
        Map<Boolean, Boolean> aBooleanBooleanMap = null;
        boolean hasABooleanBooleanMap = false;
        List<Boolean> aBooleanList = null;
        boolean hasABooleanList = false;
        Boolean[] aBoxedBooleanArray = null;
        boolean hasABoxedBooleanArray = false;
        boolean[] aBooleanArray = null;
        boolean hasABooleanArray = false;
        while (ionReader.next() != null) {
            switch (ionReader.getFieldName()) {
                case "aBoolean":
                    aBoolean = ionReader.booleanValue();
                    hasABoolean = true;
                    break;
                case "aBoxedBoolean":
                    aBoxedBoolean = deserializeBoxedBoolean(ionReader);
                    hasABoxedBoolean = true;
                    break;
                case "aBooleanBooleanMap":
                    aBooleanBooleanMap = deserializeBooleanBooleanMap(ionReader);
                    hasABooleanBooleanMap = true;
                    break;
                case "aBooleanList":
                    aBooleanList = deserializeBooleanList(ionReader);
                    hasABooleanList = true;
                    break;
                case "aBoxedBooleanArray":
                    aBoxedBooleanArray = deserializeBoxedBooleanArray(ionReader);
                    hasABoxedBooleanArray = true;
                    break;
                case "aBooleanArray":
                    aBooleanArray = deserializeBooleanArray(ionReader);
                    hasABooleanArray = true;
                    break;
            }
        }
        ionReader.stepOut();
        return new BooleanTestClass(aBoolean, aBoxedBoolean, aBooleanBooleanMap, aBooleanList, aBoxedBooleanArray, aBooleanArray);
    }

    public static void serializeDoubleTestClass(DoubleTestClass v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.STRUCT);
            return;
        }
        ionWriter.stepIn(IonType.STRUCT);
        ionWriter.setFieldName("aDouble");
        ionWriter.writeFloat(v.getADouble());
        ionWriter.setFieldName("aBoxedDouble");
        serializeBoxedDouble(v.getABoxedDouble(), ionWriter);
        ionWriter.setFieldName("aDoubleDoubleMap");
        serializeDoubleDoubleMap(v.getADoubleDoubleMap(), ionWriter);
        ionWriter.setFieldName("aDoubleList");
        serializeDoubleList(v.getADoubleList(), ionWriter);
        ionWriter.setFieldName("aBoxedDoubleArray");
        serializeBoxedDoubleArray(v.getABoxedDoubleArray(), ionWriter);
        ionWriter.setFieldName("aDoubleArray");
        serializeDoubleArray(v.getADoubleArray(), ionWriter);
        ionWriter.stepOut();
    }

    public static DoubleTestClass deserializeDoubleTestClass(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        double aDouble = 0.0;
        boolean hasADouble = false;
        Double aBoxedDouble = null;
        boolean hasABoxedDouble = false;
        Map<Double, Double> aDoubleDoubleMap = null;
        boolean hasADoubleDoubleMap = false;
        List<Double> aDoubleList = null;
        boolean hasADoubleList = false;
        Double[] aBoxedDoubleArray = null;
        boolean hasABoxedDoubleArray = false;
        double[] aDoubleArray = null;
        boolean hasADoubleArray = false;
        while (ionReader.next() != null) {
            switch (ionReader.getFieldName()) {
                case "aDouble":
                    aDouble = ionReader.doubleValue();
                    hasADouble = true;
                    break;
                case "aBoxedDouble":
                    aBoxedDouble = deserializeBoxedDouble(ionReader);
                    hasABoxedDouble = true;
                    break;
                case "aDoubleDoubleMap":
                    aDoubleDoubleMap = deserializeDoubleDoubleMap(ionReader);
                    hasADoubleDoubleMap = true;
                    break;
                case "aDoubleList":
                    aDoubleList = deserializeDoubleList(ionReader);
                    hasADoubleList = true;
                    break;
                case "aBoxedDoubleArray":
                    aBoxedDoubleArray = deserializeBoxedDoubleArray(ionReader);
                    hasABoxedDoubleArray = true;
                    break;
                case "aDoubleArray":
                    aDoubleArray = deserializeDoubleArray(ionReader);
                    hasADoubleArray = true;
                    break;
            }
        }
        ionReader.stepOut();
        return new DoubleTestClass(aDouble, aBoxedDouble, aDoubleDoubleMap, aDoubleList, aBoxedDoubleArray, aDoubleArray);
    }

    public static void serializeBlobTestClass(BlobTestClass v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.STRUCT);
            return;
        }
        ionWriter.stepIn(IonType.STRUCT);
        ionWriter.setFieldName("aBlob");
        serializeByteArray(v.getABlob(), ionWriter);
        ionWriter.stepOut();
    }

    public static BlobTestClass deserializeBlobTestClass(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        byte[] aBlob = null;
        boolean hasABlob = false;
        while (ionReader.next() != null) {
            switch (ionReader.getFieldName()) {
                case "aBlob":
                    aBlob = deserializeByteArray(ionReader);
                    hasABlob = true;
                    break;
            }
        }
        ionReader.stepOut();
        return new BlobTestClass(aBlob);
    }

    public static void serializeStringTestClass(StringTestClass v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.STRUCT);
            return;
        }
        ionWriter.stepIn(IonType.STRUCT);
        ionWriter.setFieldName("aString");
        serializeCharSequence(v.getaString(), ionWriter);
        ionWriter.stepOut();
    }

    public static StringTestClass deserializeStringTestClass(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        String aString = null;
        boolean hasAString = false;
        while (ionReader.next() != null) {
            switch (ionReader.getFieldName()) {
                case "aString":
                    aString = deserializeCharSequence(ionReader);
                    hasAString = true;
                    break;
            }
        }
        ionReader.stepOut();
        return new StringTestClass(aString);
    }

    public static void serializeTemporalTestClass(TemporalTestClass v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.STRUCT);
            return;
        }
        ionWriter.stepIn(IonType.STRUCT);
        ionWriter.setFieldName("aZonedDateTime");
        serializeZonedDateTime(v.getaZonedDateTime(), ionWriter);
        ionWriter.stepOut();
    }

    public static TemporalTestClass deserializeTemporalTestClass(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        ZonedDateTime aZonedDateTime = null;
        boolean hasAZonedDateTime = false;
        while (ionReader.next() != null) {
            switch (ionReader.getFieldName()) {
                case "aZonedDateTime":
                    aZonedDateTime = deserializeZonedDateTime(ionReader);
                    hasAZonedDateTime = true;
                    break;
            }
        }
        ionReader.stepOut();
        return new TemporalTestClass(aZonedDateTime);
    }

    public static void serializeBoxedInt(Integer v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.INT);
        } else {
            ionWriter.writeInt(v);
        }
    }

    public static Integer deserializeBoxedInt(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        } else {
            return ionReader.intValue();
        }
    }

    public static void serializeIntegerIntegerMap(Map<Integer, Integer> v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Map.Entry<Integer, Integer> entry : v.entrySet()) {
            ionWriter.stepIn(IonType.STRUCT);
            ionWriter.setFieldName("key");
            serializeBoxedInt(entry.getKey(), ionWriter);
            ionWriter.setFieldName("value");
            serializeBoxedInt(entry.getValue(), ionWriter);
            ionWriter.stepOut();
        }
        ionWriter.stepOut();
    }

    public static Map<Integer, Integer> deserializeIntegerIntegerMap(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        Map<Integer, Integer> output = new HashMap<Integer, Integer>();
        ionReader.stepIn();
        while (ionReader.next() != null) {
            Integer key = null;
            boolean hasKey = false;
            Integer value = null;
            boolean hasValue = false;
            ionReader.stepIn();
            while (ionReader.next() != null) {
                switch (ionReader.getFieldName()) {
                    case "key":
                        key = deserializeBoxedInt(ionReader);
                        hasKey = true;
                        break;
                    case "value":
                        value = deserializeBoxedInt(ionReader);
                        hasValue = true;
                        break;
                }
                output.put(key, value);
            }
            ionReader.stepOut();
        }
        ionReader.stepOut();
        return output;
    }

    public static void serializeIntegerList(List<Integer> v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Integer e : v) {
            serializeBoxedInt(e, ionWriter);
        }
        ionWriter.stepOut();
    }

    public static List<Integer> deserializeIntegerList(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        List<Integer> output = new ArrayList<Integer>();
        while (ionReader.next() != null) {
            output.add(deserializeBoxedInt(ionReader));
        }
        ionReader.stepOut();
        return output;
    }

    public static void serializeBoxedIntArray(Integer[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Integer e : v) {
            serializeBoxedInt(e, ionWriter);
        }
        ionWriter.stepOut();
    }

    public static Integer[] deserializeBoxedIntArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        Integer[] output = new Integer[10];
        int count = 0;
        while (ionReader.next() != null) {
            Integer element = deserializeBoxedInt(ionReader);
            if (output.length == count) {
                output = Arrays.copyOf(output, count * 2);
            }
            output[count++] = element;
        }
        output = Arrays.copyOfRange(output, 0, count);
        ionReader.stepOut();
        return output;
    }

    public static void serializeIntArray(int[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (int e : v) {
            ionWriter.writeInt(e);
        }
        ionWriter.stepOut();
    }

    public static int[] deserializeIntArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        int[] output = new int[10];
        int count = 0;
        while (ionReader.next() != null) {
            int element = ionReader.intValue();
            if (output.length == count) {
                output = Arrays.copyOf(output, count * 2);
            }
            output[count++] = element;
        }
        output = Arrays.copyOfRange(output, 0, count);
        ionReader.stepOut();
        return output;
    }

    public static void serializeBoxedLong(Long v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.INT);
        } else {
            ionWriter.writeInt(v);
        }
    }

    public static Long deserializeBoxedLong(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        } else {
            return ionReader.longValue();
        }
    }

    public static void serializeLongLongMap(Map<Long, Long> v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Map.Entry<Long, Long> entry : v.entrySet()) {
            ionWriter.stepIn(IonType.STRUCT);
            ionWriter.setFieldName("key");
            serializeBoxedLong(entry.getKey(), ionWriter);
            ionWriter.setFieldName("value");
            serializeBoxedLong(entry.getValue(), ionWriter);
            ionWriter.stepOut();
        }
        ionWriter.stepOut();
    }

    public static Map<Long, Long> deserializeLongLongMap(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        Map<Long, Long> output = new HashMap<Long, Long>();
        ionReader.stepIn();
        while (ionReader.next() != null) {
            Long key = null;
            boolean hasKey = false;
            Long value = null;
            boolean hasValue = false;
            ionReader.stepIn();
            while (ionReader.next() != null) {
                switch (ionReader.getFieldName()) {
                    case "key":
                        key = deserializeBoxedLong(ionReader);
                        hasKey = true;
                        break;
                    case "value":
                        value = deserializeBoxedLong(ionReader);
                        hasValue = true;
                        break;
                }
                output.put(key, value);
            }
            ionReader.stepOut();
        }
        ionReader.stepOut();
        return output;
    }

    public static void serializeLongList(List<Long> v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Long e : v) {
            serializeBoxedLong(e, ionWriter);
        }
        ionWriter.stepOut();
    }

    public static List<Long> deserializeLongList(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        List<Long> output = new ArrayList<Long>();
        while (ionReader.next() != null) {
            output.add(deserializeBoxedLong(ionReader));
        }
        ionReader.stepOut();
        return output;
    }

    public static void serializeBoxedLongArray(Long[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Long e : v) {
            serializeBoxedLong(e, ionWriter);
        }
        ionWriter.stepOut();
    }

    public static Long[] deserializeBoxedLongArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        Long[] output = new Long[10];
        int count = 0;
        while (ionReader.next() != null) {
            Long element = deserializeBoxedLong(ionReader);
            if (output.length == count) {
                output = Arrays.copyOf(output, count * 2);
            }
            output[count++] = element;
        }
        output = Arrays.copyOfRange(output, 0, count);
        ionReader.stepOut();
        return output;
    }

    public static void serializeLongArray(long[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (long e : v) {
            ionWriter.writeInt(e);
        }
        ionWriter.stepOut();
    }

    public static long[] deserializeLongArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        long[] output = new long[10];
        int count = 0;
        while (ionReader.next() != null) {
            long element = ionReader.longValue();
            if (output.length == count) {
                output = Arrays.copyOf(output, count * 2);
            }
            output[count++] = element;
        }
        output = Arrays.copyOfRange(output, 0, count);
        ionReader.stepOut();
        return output;
    }

    public static void serializeBoxedBoolean(Boolean v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.BOOL);
        } else {
            ionWriter.writeBool(v);
        }
    }

    public static Boolean deserializeBoxedBoolean(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        } else {
            return ionReader.booleanValue();
        }
    }

    public static void serializeBooleanBooleanMap(Map<Boolean, Boolean> v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Map.Entry<Boolean, Boolean> entry : v.entrySet()) {
            ionWriter.stepIn(IonType.STRUCT);
            ionWriter.setFieldName("key");
            serializeBoxedBoolean(entry.getKey(), ionWriter);
            ionWriter.setFieldName("value");
            serializeBoxedBoolean(entry.getValue(), ionWriter);
            ionWriter.stepOut();
        }
        ionWriter.stepOut();
    }

    public static Map<Boolean, Boolean> deserializeBooleanBooleanMap(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        Map<Boolean, Boolean> output = new HashMap<Boolean, Boolean>();
        ionReader.stepIn();
        while (ionReader.next() != null) {
            Boolean key = null;
            boolean hasKey = false;
            Boolean value = null;
            boolean hasValue = false;
            ionReader.stepIn();
            while (ionReader.next() != null) {
                switch (ionReader.getFieldName()) {
                    case "key":
                        key = deserializeBoxedBoolean(ionReader);
                        hasKey = true;
                        break;
                    case "value":
                        value = deserializeBoxedBoolean(ionReader);
                        hasValue = true;
                        break;
                }
                output.put(key, value);
            }
            ionReader.stepOut();
        }
        ionReader.stepOut();
        return output;
    }

    public static void serializeBooleanList(List<Boolean> v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Boolean e : v) {
            serializeBoxedBoolean(e, ionWriter);
        }
        ionWriter.stepOut();
    }

    public static List<Boolean> deserializeBooleanList(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        List<Boolean> output = new ArrayList<Boolean>();
        while (ionReader.next() != null) {
            output.add(deserializeBoxedBoolean(ionReader));
        }
        ionReader.stepOut();
        return output;
    }

    public static void serializeBoxedBooleanArray(Boolean[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Boolean e : v) {
            serializeBoxedBoolean(e, ionWriter);
        }
        ionWriter.stepOut();
    }

    public static Boolean[] deserializeBoxedBooleanArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        Boolean[] output = new Boolean[10];
        int count = 0;
        while (ionReader.next() != null) {
            Boolean element = deserializeBoxedBoolean(ionReader);
            if (output.length == count) {
                output = Arrays.copyOf(output, count * 2);
            }
            output[count++] = element;
        }
        output = Arrays.copyOfRange(output, 0, count);
        ionReader.stepOut();
        return output;
    }

    public static void serializeBooleanArray(boolean[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (boolean e : v) {
            ionWriter.writeBool(e);
        }
        ionWriter.stepOut();
    }

    public static boolean[] deserializeBooleanArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        boolean[] output = new boolean[10];
        int count = 0;
        while (ionReader.next() != null) {
            boolean element = ionReader.booleanValue();
            if (output.length == count) {
                output = Arrays.copyOf(output, count * 2);
            }
            output[count++] = element;
        }
        output = Arrays.copyOfRange(output, 0, count);
        ionReader.stepOut();
        return output;
    }

    public static void serializeBoxedDouble(Double v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.DECIMAL);
        } else {
            ionWriter.writeFloat(v);
        }
    }

    public static Double deserializeBoxedDouble(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        } else {
            return ionReader.doubleValue();
        }
    }

    public static void serializeDoubleDoubleMap(Map<Double, Double> v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Map.Entry<Double, Double> entry : v.entrySet()) {
            ionWriter.stepIn(IonType.STRUCT);
            ionWriter.setFieldName("key");
            serializeBoxedDouble(entry.getKey(), ionWriter);
            ionWriter.setFieldName("value");
            serializeBoxedDouble(entry.getValue(), ionWriter);
            ionWriter.stepOut();
        }
        ionWriter.stepOut();
    }

    public static Map<Double, Double> deserializeDoubleDoubleMap(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        Map<Double, Double> output = new HashMap<Double, Double>();
        ionReader.stepIn();
        while (ionReader.next() != null) {
            Double key = null;
            boolean hasKey = false;
            Double value = null;
            boolean hasValue = false;
            ionReader.stepIn();
            while (ionReader.next() != null) {
                switch (ionReader.getFieldName()) {
                    case "key":
                        key = deserializeBoxedDouble(ionReader);
                        hasKey = true;
                        break;
                    case "value":
                        value = deserializeBoxedDouble(ionReader);
                        hasValue = true;
                        break;
                }
                output.put(key, value);
            }
            ionReader.stepOut();
        }
        ionReader.stepOut();
        return output;
    }

    public static void serializeDoubleList(List<Double> v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Double e : v) {
            serializeBoxedDouble(e, ionWriter);
        }
        ionWriter.stepOut();
    }

    public static List<Double> deserializeDoubleList(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        List<Double> output = new ArrayList<Double>();
        while (ionReader.next() != null) {
            output.add(deserializeBoxedDouble(ionReader));
        }
        ionReader.stepOut();
        return output;
    }

    public static void serializeBoxedDoubleArray(Double[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (Double e : v) {
            serializeBoxedDouble(e, ionWriter);
        }
        ionWriter.stepOut();
    }

    public static Double[] deserializeBoxedDoubleArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        Double[] output = new Double[10];
        int count = 0;
        while (ionReader.next() != null) {
            Double element = deserializeBoxedDouble(ionReader);
            if (output.length == count) {
                output = Arrays.copyOf(output, count * 2);
            }
            output[count++] = element;
        }
        output = Arrays.copyOfRange(output, 0, count);
        ionReader.stepOut();
        return output;
    }

    public static void serializeDoubleArray(double[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.LIST);
            return;
        }
        ionWriter.stepIn(IonType.LIST);
        for (double e : v) {
            ionWriter.writeFloat(e);
        }
        ionWriter.stepOut();
    }

    public static double[] deserializeDoubleArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        ionReader.stepIn();
        double[] output = new double[10];
        int count = 0;
        while (ionReader.next() != null) {
            double element = ionReader.doubleValue();
            if (output.length == count) {
                output = Arrays.copyOf(output, count * 2);
            }
            output[count++] = element;
        }
        output = Arrays.copyOfRange(output, 0, count);
        ionReader.stepOut();
        return output;
    }

    public static void serializeByteArray(byte[] v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.BLOB);
        } else {
            ionWriter.writeBlob(v);
        }
    }

    public static byte[] deserializeByteArray(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        } else {
            return ionReader.newBytes();
        }
    }

    public static void serializeCharSequence(CharSequence v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.STRING);
        } else {
            ionWriter.writeString(v.toString());
        }
    }

    public static String deserializeCharSequence(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        } else {
            return ionReader.stringValue();
        }
    }

    public static void serializeZonedDateTime(ZonedDateTime v, IonWriter ionWriter) throws IOException {
        if (v == null) {
            ionWriter.writeNull(IonType.TIMESTAMP);
            return;
        }
        ionWriter.writeTimestamp(Timestamp.forEpochSecond(v.toEpochSecond(), v.getNano(), v.getOffset().getTotalSeconds() / 60));
    }

    public static ZonedDateTime deserializeZonedDateTime(IonReader ionReader) throws IOException {
        if (ionReader.isNullValue()) {
            return null;
        }
        Timestamp timestamp = ionReader.timestampValue();
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timestamp.getLocalOffset() * 60);
        BigDecimal decimalSecond = timestamp.getDecimalSecond();
        int seconds = decimalSecond.intValue();
        int nanos = decimalSecond.subtract(BigDecimal.valueOf(seconds)).movePointRight(9).intValue();
        return ZonedDateTime.of(timestamp.getYear(), timestamp.getMonth(), timestamp.getDay(), timestamp.getHour(), timestamp.getMinute(), seconds, nanos, zoneOffset);
    }
}
