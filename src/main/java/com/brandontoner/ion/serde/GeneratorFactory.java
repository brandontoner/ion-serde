package com.brandontoner.ion.serde;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GeneratorFactory {
    private final SerializationConfig serializationConfig;
    private final GenerationContext generationContext;

    private final Map<Type, Generator> cache = new HashMap<>();

    public GeneratorFactory(final SerializationConfig serializationConfig, final GenerationContext generationContext) {
        this.serializationConfig = serializationConfig;
        this.generationContext = generationContext;
    }

    public Generator getGenerator(final Type type) {
        return cache.computeIfAbsent(type, ignored -> {
            {
                Generator generator = serializationConfig.getGenerator(type);
                if (generator != null) {
                    return generator;
                }
            }

            if (type instanceof Class<?>) {
                Class<?> clazz = (Class<?>) type;
                if (byte[].class.equals(clazz)) {
                    return new ByteArrayGenerator(this, serializationConfig, generationContext);
                }
                if (clazz.isArray()) {
                    return new ArrayGenerator(this, serializationConfig, generationContext, clazz);
                }
                if (Date.class.equals(clazz)) {
                    return new DateGenerator(this, serializationConfig, generationContext);
                }
                if (String.class.equals(clazz)) {
                    return new CharSequenceGenerator(this, serializationConfig, generationContext);
                }
                if (int.class.equals(clazz)) {
                    return new IntGenerator(this, serializationConfig, generationContext);
                }
                if (Integer.class.equals(clazz)) {
                    return new IntegerGenerator(this, serializationConfig, generationContext);
                }
                return PojoGenerator.create(this, serializationConfig, generationContext, clazz);
            } else if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                // TODO this should probably be isAssignableFrom? Also doesn't cover if the type is something like
                // class C implements Map<String, String>
                if (Map.class.equals(parameterizedType.getRawType())) {
                    return new MapGenerator(this, serializationConfig, generationContext, parameterizedType);
                }
                if (List.class.equals(parameterizedType.getRawType())) {
                    return new ListGenerator(this, serializationConfig, generationContext, parameterizedType);
                }
                throw new UnsupportedOperationException();
            } else {
                throw new IllegalArgumentException(type.toString());
            }
        });
    }
}
