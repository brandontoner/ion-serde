package com.brandontoner.ion.serde;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;

/**
 * Generates serialization for arrays.
 */
public final class ArrayGenerator extends Generator {
    /** Array type. */
    private final Class<?> clazz;
    /** Equivalent list type. */
    private final ParameterizedType listType;

    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     * @param aClass              array type to generate
     */
    public ArrayGenerator(final GeneratorFactory generatorFactory,
                          final SerializationConfig serializationConfig,
                          final GenerationContext generationContext,
                          final Class<?> aClass) {
        super(generatorFactory, serializationConfig, generationContext);
        this.clazz = aClass;
        listType = TypeUtils.parameterizedType(List.class, aClass.getComponentType());
    }

    @Override
    public CharSequence callSerializer(final String value, final String ionWriterName) {
        return new StringBuilder().append(getSerializerName(clazz))
                                  .append('(')
                                  .append(value)
                                  .append(", ")
                                  .append(ionWriterName)
                                  .append(')');
    }

    @Override
    public CharSequence callDeserializer(final String ionReaderName) {
        return new StringBuilder().append(getDeserializerName(clazz)).append('(').append(ionReaderName).append(')');
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of(listType);
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

        stringBuilder.append(indent(2))
                     .append("ionWriter.stepIn(")
                     .append(getTypeName(IonType.class))
                     .append(".LIST);")
                     .append(newline());
        stringBuilder.append(indent(2))
                     .append("for (")
                     .append(getTypeName(clazz.getComponentType()))
                     .append(" e : v) {")
                     .append(newline());
        stringBuilder.append(indent(3))
                     .append(getGeneratorFactory().getGenerator(clazz.getComponentType())
                                                  .callSerializer("e", "ionWriter"))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(indent(2)).append("ionWriter.stepOut();").append(newline());
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

        stringBuilder.append(indent(2))
                     .append(getTypeName(listType))
                     .append(" output = ")
                     .append(getGeneratorFactory().getGenerator(listType).callDeserializer("ionReader"))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(2))
                     .append("return output.toArray(new ")
                     .append(getTypeName(clazz.getComponentType()))
                     .append("[0]);")
                     .append(newline());
        stringBuilder.append(indent(1)).append('}').append(newline());
        return stringBuilder;
    }
}
