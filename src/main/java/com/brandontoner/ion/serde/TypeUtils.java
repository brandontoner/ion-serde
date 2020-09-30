package com.brandontoner.ion.serde;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class TypeUtils {
    /** Map of boxed type to primitive type. */
    private static final Map<Class<?>, Class<?>> BOXED_TO_PRIMITIVE = Map.of(Byte.class,
                                                                             byte.class,
                                                                             Short.class,
                                                                             short.class,
                                                                             Integer.class,
                                                                             int.class,
                                                                             Long.class,
                                                                             long.class,
                                                                             Float.class,
                                                                             float.class,
                                                                             Double.class,
                                                                             double.class,
                                                                             Character.class,
                                                                             char.class,
                                                                             Boolean.class,
                                                                             boolean.class);

    public static ParameterizedType parameterizedType(final Class<?> rawType, final Type... actualTypeArgument) {
        for (Type type : actualTypeArgument) {
            if (type instanceof Class<?>) {
                if (((Class<?>) type).isPrimitive()) {
                    throw new IllegalArgumentException(
                            "Primitive types cannot be used as generic type parameters. Given " + Arrays.toString(
                                    actualTypeArgument));
                }
            }
        }
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return actualTypeArgument;
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return rawType.getDeclaringClass();
            }

            @Override
            public boolean equals(final Object o) {
                if (!(o instanceof ParameterizedType)) {
                    return false;
                }
                if (this == o) {
                    return true;
                }
                ParameterizedType that = (ParameterizedType) o;

                return Objects.equals(getOwnerType(), that.getOwnerType())
                       && Objects.equals(getRawType(),
                                         that.getRawType())
                       && Arrays.equals(getActualTypeArguments(), that.getActualTypeArguments());
            }

            @Override
            public int hashCode() {
                return Arrays.hashCode(getActualTypeArguments()) ^ Objects.hashCode(getOwnerType()) ^ Objects.hashCode(
                        getRawType());
            }
        };
    }

    /**
     * Gets the the primitive type for a boxed type.
     *
     * @param clazz boxed type
     * @return primitive type, or null if not a boxed primitive
     */
    public static Class<?> getUnboxedType(Class<?> clazz) {
        return BOXED_TO_PRIMITIVE.get(clazz);
    }
}
