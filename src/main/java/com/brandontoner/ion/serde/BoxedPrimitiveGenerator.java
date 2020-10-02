package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Serializer for boxed primitives.
 */
final class BoxedPrimitiveGenerator extends NullableGenerator {
    private final Class<?> mPrimitiveClass;

    /**
     * Constructor.
     *
     * @param <T>                 type
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     * @param primitiveClass      primitive class
     * @param boxedClass          boxed class
     * @param ionType             ion type
     */
    <T> BoxedPrimitiveGenerator(final GeneratorFactory generatorFactory,
                                final SerializationConfig serializationConfig,
                                final GenerationContext generationContext,
                                final Class<T> primitiveClass,
                                final Type boxedClass,
                                final IonType ionType) {
        super(generatorFactory, serializationConfig, generationContext, boxedClass, ionType);
        // TODO make sure types make sense
        mPrimitiveClass = primitiveClass;
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of(mPrimitiveClass);
    }

    @Override
    public CharSequence generateNonNullSerializerBody(final String valueName, final String ionWriterName) {
        return new StringBuilder(indent(2)).append(getGeneratorFactory().getGenerator(mPrimitiveClass)
                                                                        .callSerializer(valueName, ionWriterName))
                                           .append(';')
                                           .append(newline());
    }

    @Override
    public CharSequence generateNonNullDeserializerBody(final String ionReaderName) {
        return new StringBuilder(indent(2)).append("return ")
                                           .append(getGeneratorFactory().getGenerator(mPrimitiveClass)
                                                                        .callDeserializer(ionReaderName))
                                           .append(';')
                                           .append(newline());
    }
}
