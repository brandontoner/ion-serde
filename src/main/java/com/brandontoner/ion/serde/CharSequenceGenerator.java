package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Generates serializers for CharSequences.
 */
final class CharSequenceGenerator extends NullableGenerator {
    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     */
    CharSequenceGenerator(final GeneratorFactory generatorFactory,
                          final SerializationConfig serializationConfig,
                          final GenerationContext generationContext) {
        super(generatorFactory,
              serializationConfig,
              generationContext,
              CharSequence.class,
              String.class,
              IonType.STRING);
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    protected CharSequence generateNonNullSerializerBody(final String valueName, final String ionWriterName) {
        return new StringBuilder(indent(2)).append(ionWriterName)
                                           .append(".writeString(")
                                           .append(valueName)
                                           .append(".toString());")
                                           .append(newline());
    }

    @Override
    protected CharSequence generateNonNullDeserializerBody(final String ionReaderName) {
        return new StringBuilder(indent(2)).append("return ")
                                           .append(ionReaderName)
                                           .append(".stringValue();")
                                           .append(newline());
    }
}
