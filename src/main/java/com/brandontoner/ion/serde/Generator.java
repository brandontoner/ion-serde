package com.brandontoner.ion.serde;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Generates a serializer and deserializer for a type.
 */
public abstract class Generator {
    private final SerializationConfig serializationConfig;
    private final GenerationContext generationContext;
    private final GeneratorFactory generatorFactory;

    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization configuration
     * @param generationContext   generation context
     */
    protected Generator(final GeneratorFactory generatorFactory,
                        final SerializationConfig serializationConfig,
                        final GenerationContext generationContext) {
        this.serializationConfig = serializationConfig;
        this.generationContext = generationContext;
        this.generatorFactory = generatorFactory;
    }

    /**
     * Returns a how to call the serializer.
     *
     * @param value         the value being serialized, could be a literal, a variable, or a method call
     * @param ionWriterName the name of the IonReader variable to use
     * @return a non-null CharSequence containing how to call the serializer for this type
     */
    public abstract CharSequence callSerializer(String value, String ionWriterName);

    /**
     * Returns a how to call the deserializer.
     *
     * @param ionReaderName the name of the IonReader variable to use
     * @return a non-null CharSequence containing how to call the serializer for this type
     */
    public abstract CharSequence callDeserializer(String ionReaderName);

    /**
     * Gets the collection of types which need to be serialized / deserialized to make this generator work
     *
     * @return non-null collection of dependent types
     */
    public abstract Collection<Type> dependencies();

    /**
     * Generates the serialization code, which can be invoked by {@link #callSerializer(String, String)}. If null, no
     * serialization code is needed.
     *
     * @return serialization code or null
     */
    public abstract CharSequence generateSerializer();

    /**
     * Generates the deserialization code, which can be invoked by {@link #callDeserializer(String)}. If null, no
     * deserialization code is needed.
     *
     * @return deserialization code or null
     */
    public abstract CharSequence generateDeserializer();

    /**
     * Returns the default value used to initialize variables. Defaults to null, which should be correct for
     * non-primitive types.
     *
     * @return default value
     */
    public CharSequence defaultValue() {
        return "null";
    }

    protected SerializationConfig getSerializationConfig() {
        return serializationConfig;
    }

    protected GenerationContext getGenerationContext() {
        return generationContext;
    }

    protected GeneratorFactory getGeneratorFactory() {
        return generatorFactory;
    }

    /**
     * Gets a CharSequence for the given indentation level.
     *
     * @param i indentation level (each level incremented by 1)
     * @return indentation char sequence
     */
    protected CharSequence indent(final int i) {
        return serializationConfig.indent(i);
    }

    /**
     * Returns the line separator string.
     *
     * @return the line separator string
     */
    protected CharSequence newline() {
        return serializationConfig.newline();
    }

    protected String getTypeName(final Type type) {
        if (type instanceof Class<?>) {
            Class<?> clazz = (Class<?>) type;
            if (clazz.isArray()) {
                return getTypeName(clazz.getComponentType()) + "[]";
            }
            if (clazz.getDeclaringClass() != null) {
                return getTypeName(clazz.getDeclaringClass()) + '.' + clazz.getSimpleName();
            }
            return generationContext.tryImport(clazz);
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return getTypeName(parameterizedType.getRawType())
                   + '<'
                   + Arrays.stream(parameterizedType.getActualTypeArguments())
                           .map(this::getTypeName)
                           .collect(Collectors.joining(", "))
                   + '>';
        }
        throw new UnsupportedOperationException();
    }

    public String getDeserializerName(final Type type) {
        return "de" + getSerializerName(type);
    }

    public String getSerializerName(final Type type) {
        if (type instanceof Class<?>) {
            Class<?> clazz = (Class<?>) type;
            if (clazz.isArray()) {
                return getSerializerName(clazz.getComponentType()) + "Array";
            }
            return "serialize" + camelCase(getTypeName(type));
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return "serialize"
                   + Arrays.stream(parameterizedType.getActualTypeArguments())
                           .map(this::getTypeName)
                           .map(Generator::camelCase)
                           .collect(Collectors.joining())
                   + camelCase(getTypeName(parameterizedType.getRawType()));
        } else {
            throw new UnsupportedOperationException();
        }
    }


    public static String quote(final String paramName) {
        return '"' + paramName + '"';
    }

    public static String camelCase(final String typeName) {
        return Character.toUpperCase(typeName.charAt(0)) + typeName.substring(1);
    }
}
