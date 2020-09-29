package com.brandontoner.ion.serde;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;

final class ListGenerator extends Generator {
    private final ParameterizedType listType;
    private final ParameterizedType arrayListType;
    private final Type componentType;

    ListGenerator(final GeneratorFactory generatorFactory,
                  final SerializationConfig serializationConfig,
                  final GenerationContext generationContext,
                  final ParameterizedType clazz) {
        super(generatorFactory, serializationConfig, generationContext);
        listType = clazz;
        componentType = listType.getActualTypeArguments()[0];
        arrayListType = TypeUtils.parameterizedType(ArrayList.class, listType.getActualTypeArguments());
    }

    @Override
    public Collection<Type> dependencies() {
        return Arrays.asList(listType.getActualTypeArguments());
    }

    @Override
    public CharSequence callSerializer(final String value, final String ionWriterName) {
        return new StringBuilder(getSerializerName(listType)).append('(')
                                                             .append(value)
                                                             .append(", ")
                                                             .append(ionWriterName)
                                                             .append(')');
    }

    @Override
    public CharSequence callDeserializer(final String ionReaderName) {
        return new StringBuilder(getDeserializerName(listType)).append('(').append(ionReaderName).append(')');
    }

    @Override
    public CharSequence generateSerializer() {
        StringBuilder stringBuilder = new StringBuilder();

        // Method declaration line
        stringBuilder.append(indent(1))
                     .append("public static void ")
                     .append(getSerializerName(listType))
                     .append('(')
                     .append(getTypeName(listType))
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
                     .append(getTypeName(componentType))
                     .append(" e : v) {")
                     .append(newline());
        stringBuilder.append(indent(3))
                     .append(getGeneratorFactory().getGenerator(componentType).callSerializer("e", "ionWriter"))
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
                     .append(getTypeName(listType))
                     .append(' ')
                     .append(getDeserializerName(listType))
                     .append('(')
                     .append(getTypeName(IonReader.class))
                     .append(" ionReader) throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(newline());

        stringBuilder.append(indent(2)).append("ionReader.stepIn();").append(newline());
        stringBuilder.append(indent(2))
                     .append(getTypeName(listType))
                     .append(" output = new ")
                     .append(getTypeName(arrayListType))
                     .append("();")
                     .append(newline());
        stringBuilder.append(indent(2)).append("while (ionReader.next() != null) {").append(newline());
        stringBuilder.append(indent(3))
                     .append("output.add(")
                     .append(getGeneratorFactory().getGenerator(componentType).callDeserializer("ionReader"))
                     .append(");")
                     .append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(indent(2)).append("ionReader.stepOut();").append(newline());
        stringBuilder.append(indent(2)).append("return output;").append(newline());
        stringBuilder.append(indent(1)).append('}').append(newline());

        return stringBuilder;
    }
}
