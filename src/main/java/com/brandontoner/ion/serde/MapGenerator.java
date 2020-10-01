package com.brandontoner.ion.serde;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazon.ion.IonType;

/**
 * Serializes a map. Serializes as a list of key-value pairs instead of a struct to support any type of key.
 */
final class MapGenerator extends MethodGenerator {
    private final ParameterizedType mapType;
    private final ParameterizedType hashMapType;
    private final ParameterizedType entryType;

    MapGenerator(final GeneratorFactory generatorFactory,
                 final SerializationConfig serializationConfig,
                 final GenerationContext generationContext,
                 final ParameterizedType mapType) {
        super(generatorFactory, serializationConfig, generationContext, mapType);
        this.mapType = mapType;
        hashMapType = TypeUtils.parameterizedType(HashMap.class, mapType.getActualTypeArguments());
        entryType = TypeUtils.parameterizedType(Map.Entry.class, mapType.getActualTypeArguments());
    }

    @Override
    public Collection<Type> dependencies() {
        return List.of(mapType.getActualTypeArguments());
    }


    @Override
    public CharSequence generateSerializerBody(final String valueName, final String ionWriterName) {
        Type keyType = mapType.getActualTypeArguments()[0];
        Type valueType = mapType.getActualTypeArguments()[1];
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
                     .append(getTypeName(entryType))
                     .append(" entry : v.entrySet()) {")
                     .append(newline());
        stringBuilder.append(indent(3))
                     .append("ionWriter.stepIn(")
                     .append(getTypeName(IonType.class))
                     .append(".STRUCT);")
                     .append(newline());
        stringBuilder.append(indent(3)).append("ionWriter.setFieldName(\"key\");").append(newline());
        stringBuilder.append(indent(3))
                     .append(getGeneratorFactory().getGenerator(keyType).callSerializer("entry.getKey()", "ionWriter"))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(3)).append("ionWriter.setFieldName(\"value\");").append(newline());
        stringBuilder.append(indent(3))
                     .append(getGeneratorFactory().getGenerator(valueType)
                                                  .callSerializer("entry.getValue()", "ionWriter"))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(3)).append("ionWriter.stepOut();").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(indent(2)).append("ionWriter.stepOut();").append(newline());
        return stringBuilder;
    }

    @Override
    public CharSequence generateDeserializerBody(final String ionReaderName) {
        Type keyType = mapType.getActualTypeArguments()[0];
        Type valueType = mapType.getActualTypeArguments()[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (ionReader.isNullValue()) {").append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());

        stringBuilder.append(indent(2))
                     .append(getTypeName(mapType))
                     .append(" output = new ")
                     .append(getTypeName(hashMapType))
                     .append("();")
                     .append(newline());
        stringBuilder.append(indent(2)).append("ionReader.stepIn();").append(newline());
        stringBuilder.append(indent(2)).append("while (ionReader.next() != null) {").append(newline());
        stringBuilder.append(indent(3))
                     .append(getTypeName(keyType))
                     .append(' ')
                     .append("key = ")
                     .append(getGeneratorFactory().getGenerator(keyType).defaultValue())
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(3)).append("boolean hasKey = false;").append(newline());
        stringBuilder.append(indent(3))
                     .append(getTypeName(valueType))
                     .append(' ')
                     .append("value = ")
                     .append(getGeneratorFactory().getGenerator(valueType).defaultValue())
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(3)).append("boolean hasValue = false;").append(newline());
        stringBuilder.append(indent(3)).append("ionReader.stepIn();").append(newline());
        stringBuilder.append(indent(3)).append("while (ionReader.next() != null) {").append(newline());
        stringBuilder.append(indent(4)).append("switch (ionReader.getFieldName()) {").append(newline());
        stringBuilder.append(indent(5)).append("case \"key\":").append(newline());
        stringBuilder.append(indent(6))
                     .append("key = ")
                     .append(getGeneratorFactory().getGenerator(keyType).callDeserializer("ionReader"))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(6)).append("hasKey = true;").append(newline());
        stringBuilder.append(indent(6)).append("break;").append(newline());
        stringBuilder.append(indent(5)).append("case \"value\":").append(newline());
        stringBuilder.append(indent(6))
                     .append("value = ")
                     .append(getGeneratorFactory().getGenerator(valueType).callDeserializer("ionReader"))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(6)).append("hasValue = true;").append(newline());
        stringBuilder.append(indent(6)).append("break;").append(newline());
        stringBuilder.append(indent(4)).append('}').append(newline());
        stringBuilder.append(indent(4)).append("output.put(key, value);").append(newline());
        stringBuilder.append(indent(3)).append('}').append(newline());
        stringBuilder.append(indent(3)).append("ionReader.stepOut();").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(indent(2)).append("ionReader.stepOut();").append(newline());
        stringBuilder.append(indent(2)).append("return output;").append(newline());
        return stringBuilder;
    }
}
