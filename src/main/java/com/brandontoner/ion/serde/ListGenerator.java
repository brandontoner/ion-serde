package com.brandontoner.ion.serde;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.amazon.ion.IonType;

final class ListGenerator extends MethodGenerator {
    private final ParameterizedType listType;
    private final ParameterizedType arrayListType;
    private final Type componentType;

    ListGenerator(final GeneratorFactory generatorFactory,
                  final SerializationConfig serializationConfig,
                  final GenerationContext generationContext,
                  final ParameterizedType clazz) {
        super(generatorFactory, serializationConfig, generationContext, clazz);
        listType = clazz;
        componentType = listType.getActualTypeArguments()[0];
        arrayListType = TypeUtils.parameterizedType(ArrayList.class, listType.getActualTypeArguments());
    }

    @Override
    public Collection<Type> dependencies() {
        return Arrays.asList(listType.getActualTypeArguments());
    }

    @Override
    public CharSequence generateSerializerBody(final String valueName, final String ionWriterName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (v == null) {").append(newline());
        stringBuilder.append(indent(3))
                     .append("ionWriter.writeNull(")
                     .append(getTypeName(IonType.class))
                     .append(".LIST);")
                     .append(newline());
        stringBuilder.append(indent(3)).append("return;").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());

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
        return stringBuilder;
    }

    @Override
    public CharSequence generateDeserializerBody(final String ionReaderName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (ionReader.isNullValue()) {").append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());

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
        return stringBuilder;
    }
}
