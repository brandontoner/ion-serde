package com.brandontoner.ion.serde;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;

/**
 * Generates serializers for CharSequences.
 */
final class CharSequenceGenerator extends Generator {
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
        super(generatorFactory, serializationConfig, generationContext);
    }

    @Override
    public CharSequence callSerializer(final String value, final String ionWriterName) {
        return new StringBuilder(getSerializerName(CharSequence.class)).append('(')
                                                                       .append(value)
                                                                       .append(", ")
                                                                       .append(ionWriterName)
                                                                       .append(')');
    }

    @Override
    public CharSequence callDeserializer(final String ionReaderName) {
        return new StringBuilder(getDeserializerName(CharSequence.class)).append('(').append(ionReaderName).append(')');
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    public CharSequence generateSerializer() {
        StringBuilder stringBuilder = new StringBuilder();

        // Method declaration line
        stringBuilder.append(indent(1))
                     .append("public static void ")
                     .append(getSerializerName(CharSequence.class))
                     .append('(')
                     .append(getTypeName(CharSequence.class))
                     .append(" v, ")
                     .append(getTypeName(IonWriter.class))
                     .append(" ionWriter) throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(newline());

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
        stringBuilder.append(indent(1)).append('}').append(newline());
        return stringBuilder;
    }

    @Override
    public CharSequence generateDeserializer() {
        StringBuilder stringBuilder = new StringBuilder();

        // Method declaration line
        stringBuilder.append(indent(1))
                     .append("public static ")
                     .append(getTypeName(String.class))
                     .append(' ')
                     .append(getDeserializerName(CharSequence.class))
                     .append('(')
                     .append(getTypeName(IonReader.class))
                     .append(" ionReader) throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(newline());
        stringBuilder.append(indent(2)).append("if (ionReader.isNullValue()) {").append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3)).append("return ionReader.stringValue();").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(indent(1)).append('}').append(newline());
        return stringBuilder;
    }
}
