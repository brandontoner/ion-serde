package com.brandontoner.ion.serde;

import java.io.IOException;
import java.lang.reflect.Type;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonWriter;

public abstract class MethodGenerator extends Generator {
    private static final String ION_READER_NAME = "ionReader";
    private static final String ION_WRITER_NAME = "ionWriter";
    private static final String VALUE_NAME = "v";
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
                     .append(' ')
                     .append(VALUE_NAME)
                     .append(", ")
                     .append(getTypeName(IonWriter.class))
                     .append(' ')
                     .append(ION_WRITER_NAME)
                     .append(") throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(newline());
        stringBuilder.append(generateSerializerBody(VALUE_NAME, ION_WRITER_NAME));
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
                     .append(' ')
                     .append(ION_READER_NAME)
                     .append(") throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(newline());
        stringBuilder.append(generateDeserializerBody(ION_READER_NAME));
        stringBuilder.append(indent(1)).append('}').append(newline());
        return stringBuilder;
    }

    /**
     * Generates the body of the serialize method.
     *
     * @param valueName     name of the value variable
     * @param ionWriterName name of the ion writer variable
     * @return serialize method body
     */
    protected abstract CharSequence generateSerializerBody(String valueName, String ionWriterName);

    /**
     * Generates the body of the deserialize method.
     *
     * @param ionReaderName name of the ion reader variable
     * @return deserialize method body
     */
    protected abstract CharSequence generateDeserializerBody(String ionReaderName);
}
