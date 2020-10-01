package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Generates serializers for byte arrays.
 */
final class ByteArrayGenerator extends MethodGenerator {
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
        super(generatorFactory, serializationConfig, generationContext, byte[].class);
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    public CharSequence generateSerializerBody(final String valueName, final String ionWriterName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (v == null) {").append(newline());
        stringBuilder.append(indent(3))
                     .append("ionWriter.writeNull(")
                     .append(getTypeName(IonType.class))
                     .append(".BLOB")
                     .append(");")
                     .append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3)).append("ionWriter.writeBlob(v);").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        return stringBuilder;
    }

    @Override
    public CharSequence generateDeserializerBody(final String ionReaderName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (ionReader.isNullValue()) {").append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3)).append("return ionReader.newBytes();").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        return stringBuilder;
    }
}
