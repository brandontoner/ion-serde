package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Generates serializers for byte arrays.
 */
final class ByteArrayGenerator extends NullableGenerator {
    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     */
    ByteArrayGenerator(final GeneratorFactory generatorFactory,
                       final SerializationConfig serializationConfig,
                       final GenerationContext generationContext) {
        super(generatorFactory, serializationConfig, generationContext, byte[].class, IonType.BLOB);
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    public CharSequence generateNonNullSerializerBody(final String valueName, final String ionWriterName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2))
                     .append(ionWriterName)
                     .append(".writeBlob(")
                     .append(valueName)
                     .append(");")
                     .append(newline());
        return stringBuilder;
    }

    @Override
    public CharSequence generateNonNullDeserializerBody(final String ionReaderName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2))
                     .append("return ")
                     .append(ionReaderName)
                     .append(".newBytes();")
                     .append(newline());
        return stringBuilder;
    }
}
