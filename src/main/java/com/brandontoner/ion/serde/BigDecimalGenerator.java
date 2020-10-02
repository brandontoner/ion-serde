package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Serializer for BigDecimal.
 */
final class BigDecimalGenerator extends NullableGenerator {

    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     */
    BigDecimalGenerator(final GeneratorFactory generatorFactory,
                        final SerializationConfig serializationConfig,
                        final GenerationContext generationContext) {
        super(generatorFactory, serializationConfig, generationContext, BigDecimal.class, IonType.DECIMAL);
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    protected CharSequence generateNonNullSerializerBody(final String valueName, final String ionWriterName) {
        return new StringBuilder(indent(2)).append(ionWriterName)
                                           .append(".writeDecimal(")
                                           .append(valueName)
                                           .append(");")
                                           .append(newline());
    }

    @Override
    protected CharSequence generateNonNullDeserializerBody(final String ionReaderName) {
        return new StringBuilder(indent(2)).append("return ")
                                           .append(ionReaderName)
                                           .append(".bigDecimalValue();")
                                           .append(newline());
    }
}
