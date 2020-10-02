package com.brandontoner.ion.serde;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brandontoner.ion.serde.testtypes.IntTestClass;

public class TestInstanceGenerator {

    public static <T> Collection<T> getPermutations(Type type) {
        try {
            if (type instanceof Class<?>) {
                List<T> output = new ArrayList<>();
                Class<?> clazz = (Class<?>) type;
                if (!clazz.isPrimitive()) {
                    output.add(null);
                }
                if (clazz.isArray()) {
                    if (byte.class.equals(clazz.getComponentType())) {
                        output.add((T) new byte[]{3});
                        return output;
                    }
                    for (Object o : getPermutations(clazz.getComponentType())) {
                        if (int.class.equals(clazz.getComponentType())) {
                            int[] a = (int[]) Array.newInstance(int.class, 1);
                            a[0] = (int) o;
                            output.add((T) a);
                        } else if (long.class.equals(clazz.getComponentType())) {
                            long[] a = (long[]) Array.newInstance(long.class, 1);
                            a[0] = (long) o;
                            output.add((T) a);
                        } else if (boolean.class.equals(clazz.getComponentType())) {
                            boolean[] a = (boolean[]) Array.newInstance(boolean.class, 1);
                            a[0] = (boolean) o;
                            output.add((T) a);
                        } else if (double.class.equals(clazz.getComponentType())) {
                            double[] a = (double[]) Array.newInstance(double.class, 1);
                            a[0] = (double) o;
                            output.add((T) a);
                        } else {
                            Object[] a = (Object[]) Array.newInstance(clazz.getComponentType(), 1);
                            a[0] = o;
                            output.add((T) a);
                        }
                    }
                    return output;
                } else if (int.class.equals(type)) {
                    output.addAll((Collection<T>) List.of(1234));
                } else if (long.class.equals(type)) {
                    output.addAll((Collection<T>) List.of(1234L));
                } else if (boolean.class.equals(type)) {
                    output.addAll((Collection<T>) List.of(true, false));
                } else if (double.class.equals(type)) {
                    output.addAll((Collection<T>) List.of(Math.PI, Double.NaN));
                } else if (String.class.equals(type)){
                    output.add((T) "test");
                } else if (OffsetDateTime.class.equals(type)) {
                     output.add((T) OffsetDateTime.now());
                } else {
                    Class<?> unboxed = TypeUtils.getUnboxedType(clazz);
                    if (unboxed != null) {
                        output.addAll(getPermutations(unboxed));
                    } else {
                        Constructor<?> ctor = getConstructor(clazz);
                        List<Object[]> params = getParams(ctor.getGenericParameterTypes());
                        for (Object[] param : params) {
                            output.add((T) ctor.newInstance(param));
                        }
                    }
                }
                return output;
            } else if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (Map.class.equals(parameterizedType.getRawType())) {
                    List<T> output = new ArrayList<>();
                    output.add(null);
                    Collection<?> keys = getPermutations(parameterizedType.getActualTypeArguments()[0]);
                    Collection<?> values = getPermutations(parameterizedType.getActualTypeArguments()[1]);
                    for (Object key : keys) {
                        for (Object value : values) {
                            Map map = new HashMap();
                            map.put(key, value);
                            output.add((T) map);
                        }
                    }
                    return output;
                }
                if (List.class.equals(parameterizedType.getRawType())) {
                    List<T> output = new ArrayList<>();
                    output.add(null);
                    Collection<?> values = getPermutations(parameterizedType.getActualTypeArguments()[0]);
                    for (Object value : values) {
                        List list = new ArrayList();
                        list.add(value);
                        output.add((T) list);
                    }
                    return output;
                }
            }
            throw new UnsupportedOperationException(type.toString());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Object[]> getParams(Type[] genericParameterTypes) {
        List<Object[]> output = new ArrayList<>();
        getParams(genericParameterTypes, 0, new Object[genericParameterTypes.length], output);
        return output;
    }

    private static void getParams(Type[] types, int i, Object[] params, List<Object[]> output) {
        if (i == types.length) {
            output.add(params.clone());
            return;
        }
        for (Object o : getPermutations(types[i])) {
            params[i] = o;
            getParams(types, i + 1, params, output);
        }
    }

    private static Constructor<?> getConstructor(Class<?> clazz) {
        Constructor<?>[] ctors = clazz.getConstructors();
        if (ctors.length != 1) {
            throw new UnsupportedOperationException(clazz.toString());
        }
        return ctors[0];
    }

    public static void main(String[] args) {
        System.err.println(getPermutations(IntTestClass.class));
    }
}
