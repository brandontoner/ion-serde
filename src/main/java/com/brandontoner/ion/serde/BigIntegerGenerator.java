package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;

/**
 * Serializer for BigInteger.
 */
final class BigIntegerGenerator extends NullableGenerator {

    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     */
    BigIntegerGenerator(final GeneratorFactory generatorFactory,
                        final SerializationConfig serializationConfig,
                        final GenerationContext generationContext) {
        super(generatorFactory, serializationConfig, generationContext, BigInteger.class, IonType.INT);
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    protected CharSequence generateNonNullSerializerBody(final String valueName, final String ionWriterName) {
        return new StringBuilder(indent(2)).append(ionWriterName)
                                           .append(".writeInt(")
                                           .append(valueName)
                                           .append(");")
                                           .append(newline());
    }

    @Override
    protected CharSequence generateNonNullDeserializerBody(final String ionReaderName) {
        return new StringBuilder(indent(2)).append("return ")
                                           .append(ionReaderName)
                                           .append(".bigIntegerValue();")
                                           .append(newline());
    }
}
