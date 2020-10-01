package com.brandontoner.ion.serde;

import java.lang.reflect.Type;

import com.amazon.ion.IonType;

/**
 * Generator for type that can be null.
 */
public abstract class NullableGenerator extends MethodGenerator {
    private final IonType mIonType;

    protected NullableGenerator(final GeneratorFactory generatorFactory,
                                final SerializationConfig serializationConfig,
                                final GenerationContext generationContext,
                                final Type type,
                                final IonType ionType) {
        super(generatorFactory, serializationConfig, generationContext, type);
        mIonType = ionType;
    }

    @Override
    public final CharSequence generateSerializerBody(final String valueName, final String ionWriterName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (").append(valueName).append(" == null) {").append(newline());
        stringBuilder.append(indent(3))
                     .append(ionWriterName)
                     .append(".writeNull(")
                     .append(getTypeName(IonType.class))
                     .append('.')
                     .append(mIonType.name())
                     .append(");")
                     .append(newline());
        stringBuilder.append(indent(3)).append("return;").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(generateNonNullSerializerBody(valueName, ionWriterName));
        return stringBuilder;
    }

    @Override
    public final CharSequence generateDeserializerBody(final String ionReaderName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2))
                     .append("if (")
                     .append(ionReaderName)
                     .append(".isNullValue()) {")
                     .append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(generateNonNullDeserializerBody(ionReaderName));
        return stringBuilder;
    }

    /**
     * Generates the body of the serialize method.
     *
     * @param valueName     name of the value variable
     * @param ionWriterName name of the ion writer variable
     * @return serialize method body
     */
    protected abstract CharSequence generateNonNullSerializerBody(String valueName, String ionWriterName);

    /**
     * Generates the body of the deserialize method.
     *
     * @param ionReaderName name of the ion reader variable
     * @return deserialize method body
     */
    protected abstract CharSequence generateNonNullDeserializerBody(String ionReaderName);
}
