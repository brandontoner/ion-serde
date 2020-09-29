package com.brandontoner.ion.serde;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;

/**
 * Serializer for boxed integers (Integer).
 */
final class IntegerGenerator extends Generator {
    private final Class<Integer> clazz = Integer.class;

    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     */
    IntegerGenerator(final GeneratorFactory generatorFactory,
                     final SerializationConfig serializationConfig,
                     final GenerationContext generationContext) {
        super(generatorFactory, serializationConfig, generationContext);
    }

    @Override
    public CharSequence callSerializer(final String value, final String ionWriterName) {
        return new StringBuilder(getSerializerName(Integer.class)).append('(')
                                                                  .append(value)
                                                                  .append(", ")
                                                                  .append(ionWriterName)
                                                                  .append(')');
    }

    @Override
    public CharSequence callDeserializer(final String ionReaderName) {
        return new StringBuilder(getDeserializerName(Integer.class)).append('(').append(ionReaderName).append(')');
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
                     .append(getSerializerName(clazz))
                     .append('(')
                     .append(getTypeName(clazz))
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
                     .append(".INT);")
                     .append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3)).append("ionWriter.writeInt(v);").append(newline());
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
                     .append(getTypeName(clazz))
                     .append(' ')
                     .append(getDeserializerName(clazz))
                     .append('(')
                     .append(getTypeName(IonReader.class))
                     .append(" ionReader) throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(newline());
        stringBuilder.append(indent(2)).append("if (ionReader.isNullValue()) {").append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3)).append("return ionReader.intValue();").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(indent(1)).append('}').append(newline());
        return stringBuilder;
    }
}
