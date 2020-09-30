package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

final class LongGenerator extends Generator {
    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     */
    LongGenerator(final GeneratorFactory generatorFactory,
                  final SerializationConfig serializationConfig,
                  final GenerationContext generationContext) {
        super(generatorFactory, serializationConfig, generationContext);
    }

    @Override
    public CharSequence callSerializer(final String value, final String ionWriterName) {
        return new StringBuilder(ionWriterName).append(".writeInt(").append(value).append(')');
    }

    @Override
    public CharSequence callDeserializer(final String ionReaderName) {
        return new StringBuilder(ionReaderName).append(".longValue()");
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

    @Override
    public CharSequence defaultValue() {
        return "0L";
    }
}
