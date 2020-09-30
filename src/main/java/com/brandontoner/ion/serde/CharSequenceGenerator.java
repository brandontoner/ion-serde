package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Generates serializers for CharSequences.
 */
final class CharSequenceGenerator extends MethodGenerator {
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
        super(generatorFactory, serializationConfig, generationContext, CharSequence.class, String.class);
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    protected CharSequence generateSerializerBody() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (v == null) {").append(newline());
        stringBuilder.append(indent(3))
                     .append("ionWriter.writeNull(")
                     .append(getTypeName(IonType.class))
                     .append(".STRING")
                     .append(");")
                     .append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3)).append("ionWriter.writeString(v.toString());").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        return stringBuilder;
    }

    @Override
    protected CharSequence generateDeserializerBody() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (ionReader.isNullValue()) {").append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3)).append("return ionReader.stringValue();").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        return stringBuilder;
    }
}
