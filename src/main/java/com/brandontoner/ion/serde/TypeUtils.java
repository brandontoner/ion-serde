package com.brandontoner.ion.serde;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public class TypeUtils {
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
                       && Objects.equals(getRawType(), that.getRawType())
                       && Arrays.equals(getActualTypeArguments(), that.getActualTypeArguments());
            }

            @Override
            public int hashCode() {
                return Arrays.hashCode(getActualTypeArguments())
                       ^ Objects.hashCode(getOwnerType())
                       ^ Objects.hashCode(getRawType());
            }
        };
    }
}
