package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.amazon.ion.Timestamp;

final class DateGenerator extends Generator {
    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     */
    DateGenerator(final GeneratorFactory generatorFactory,
                  final SerializationConfig serializationConfig,
                  final GenerationContext generationContext) {
        super(generatorFactory, serializationConfig, generationContext);
    }


    @Override
    public CharSequence callSerializer(final String value, final String ionWriterName) {
        return new StringBuilder(ionWriterName).append(".writeTimestamp(")
                                               .append(getTypeName(Timestamp.class))
                                               .append(".forDateZ(")
                                               .append(value)
                                               .append("))");
    }

    @Override
    public CharSequence callDeserializer(final String ionReaderName) {
        return new StringBuilder("new ").append(getTypeName(Date.class))
                                        .append('(')
                                        .append(ionReaderName)
                                        .append(".timestampValue().getMillis())");
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    public CharSequence generateSerializer() {
        return null;
    }

    @Override
    public CharSequence generateDeserializer() {
        return null;
    }
}
