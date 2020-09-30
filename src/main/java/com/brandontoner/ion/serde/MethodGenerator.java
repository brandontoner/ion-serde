package com.brandontoner.ion.serde;

import java.io.IOException;
import java.lang.reflect.Type;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonWriter;

public abstract class MethodGenerator extends Generator {
    private final Type mInputType;
    private final Type mOutputType;

    protected MethodGenerator(final GeneratorFactory generatorFactory,
                              final SerializationConfig serializationConfig,
                              final GenerationContext generationContext,
                              final Type type) {
        this(generatorFactory, serializationConfig, generationContext, type, type);
    }

    protected MethodGenerator(final GeneratorFactory generatorFactory,
                              final SerializationConfig serializationConfig,
                              final GenerationContext generationContext,
                              final Type inputType,
                              final Type outputType) {
        super(generatorFactory, serializationConfig, generationContext);
        this.mInputType = inputType;
        this.mOutputType = outputType;
    }

    @Override
    public final CharSequence callSerializer(final String value, final String ionWriterName) {
        return new StringBuilder(getSerializerName(mInputType)).append('(')
                                                               .append(value)
                                                               .append(", ")
                                                               .append(ionWriterName)
                                                               .append(')');
    }

    @Override
    public final CharSequence callDeserializer(final String ionReaderName) {
        return new StringBuilder(getDeserializerName(mInputType)).append('(').append(ionReaderName).append(')');
    }

    @Override
    public final CharSequence generateSerializer() {
        StringBuilder stringBuilder = new StringBuilder();

        // Method declaration line
        stringBuilder.append(indent(1))
                     .append("public static void ")
                     .append(getSerializerName(mInputType))
                     .append('(')
                     .append(getTypeName(mInputType))
                     .append(" v, ")
                     .append(getTypeName(IonWriter.class))
                     .append(" ionWriter) throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(newline());
        stringBuilder.append(generateSerializerBody());
        stringBuilder.append(indent(1)).append('}').append(newline());
        return stringBuilder;
    }

    @Override
    public final CharSequence generateDeserializer() {
        StringBuilder stringBuilder = new StringBuilder();

        // Method declaration line
        stringBuilder.append(indent(1))
                     .append("public static ")
                     .append(getTypeName(mOutputType))
                     .append(' ')
                     .append(getDeserializerName(mInputType))
                     .append('(')
                     .append(getTypeName(IonReader.class))
                     .append(" ionReader) throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(newline());
        stringBuilder.append(generateDeserializerBody());
        stringBuilder.append(indent(1)).append('}').append(newline());
        return stringBuilder;
    }

    /**
     * Generates the body of the serialize method.
     *
     * @return serialize method body
     */
    protected abstract CharSequence generateSerializerBody();

    /**
     * Generates the body of the deserialize method.
     *
     * @return deserialize method body
     */
    protected abstract CharSequence generateDeserializerBody();
}
