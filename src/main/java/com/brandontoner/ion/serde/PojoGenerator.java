package com.brandontoner.ion.serde;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;

public final class PojoGenerator extends Generator {
    private final Type serializationType;
    private final Type deserializationType;
    private final List<Param> params;

    public PojoGenerator(final Type serializationType,
                         final Type deserializationType,
                         final List<Param> params,
                         final SerializationConfig serializationConfig,
                         final GenerationContext generationContext,
                         final GeneratorFactory generatorFactory) {
        super(generatorFactory, serializationConfig, generationContext);
        // TODO make sure the types make sense
        // * deserializationType must be a subclass / implement serialization type
        // * deserializationType must have a constructor with the provided parameters
        this.serializationType = serializationType;
        this.deserializationType = deserializationType;
        this.params = params;
    }

    public static PojoGenerator create(final GeneratorFactory generatorFactory,
                                       final SerializationConfig serializationConfig,
                                       final GenerationContext generationContext,
                                       final Class<?> clazz) {
        Serde<?> serde = Serde.discover(clazz, serializationConfig);
        List<Param> params = new ArrayList<>();
        for (int i = 0; i < serde.getParamNames().size(); i++) {
            Method method = serde.getMethods().get(i);
            params.add(new Param(serde.getParamNames().get(i), method.getGenericReturnType(), method));
        }
        return new PojoGenerator(clazz,
                                 serde.getClazz(),
                                 params,
                                 serializationConfig,
                                 generationContext,
                                 generatorFactory);
    }

    @Override
    public CharSequence callSerializer(final String value, final String ionWriterName) {
        return new StringBuilder(getSerializerName(serializationType)).append("(")
                                                                      .append(value)
                                                                      .append(", ")
                                                                      .append(ionWriterName)
                                                                      .append(")");
    }

    @Override
    public CharSequence callDeserializer(final String ionReaderName) {
        return new StringBuilder(getDeserializerName(serializationType)).append("(").append(ionReaderName).append(")");
    }

    /**
     * Gets the collection of types that must be serialized to serialize this type.
     *
     * @return collection of dependent types
     */
    @Override
    public Collection<Type> dependencies() {
        return params.stream().map(Param::type).collect(Collectors.toList());
    }

    @Override
    public CharSequence generateSerializer() {
        StringBuilder stringBuilder = new StringBuilder();

        // Method declaration line
        stringBuilder.append(indent(1))
                     .append("public static void ")
                     .append(getSerializerName(serializationType))
                     .append("(")
                     .append(getTypeName(serializationType))
                     .append(" v, ")
                     .append(getTypeName(IonWriter.class))
                     .append(" ionWriter) throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(System.lineSeparator());

        stringBuilder.append(indent(2))
                     .append("ionWriter.stepIn(")
                     .append(getTypeName(IonType.class))
                     .append(".STRUCT);")
                     .append(System.lineSeparator());

        for (Param param : params) {
            String paramName = param.name();
            Type paramType = param.type();
            stringBuilder.append(indent(2))
                         .append("ionWriter.setFieldName(")
                         .append(quote(paramName))
                         .append(");")
                         .append(System.lineSeparator());
            stringBuilder.append(indent(2))
                         .append(getGeneratorFactory().getGenerator(paramType)
                                                      .callSerializer("v." + param.getter().getName() + "()",
                                                                      "ionWriter"))
                         .append(";")
                         .append(System.lineSeparator());

        }
        stringBuilder.append(indent(2)).append("ionWriter.stepOut();").append(System.lineSeparator());
        stringBuilder.append(indent(1)).append("}").append(System.lineSeparator());
        return stringBuilder;
    }

    @Override
    public CharSequence generateDeserializer() {
        StringBuilder stringBuilder = new StringBuilder();

        // Method declaration line
        stringBuilder.append(indent(1))
                     .append("public static ")
                     .append(getTypeName(serializationType))
                     .append(" ")
                     .append(getDeserializerName(serializationType))
                     .append("(")
                     .append(getTypeName(IonReader.class))
                     .append(" ionReader) throws ")
                     .append(getTypeName(IOException.class))
                     .append(" {")
                     .append(System.lineSeparator());
        stringBuilder.append(indent(2)).append("ionReader.stepIn();").append(System.lineSeparator());

        for (Param param : params) {
            String paramName = param.name();
            Type paramType = param.type();

            stringBuilder.append(indent(2))
                         .append(getTypeName(paramType))
                         .append(" ")
                         .append(paramName)
                         .append(" = ")
                         .append(getGeneratorFactory().getGenerator(paramType).defaultValue())
                         .append(";")
                         .append(System.lineSeparator());
            stringBuilder.append(indent(2))
                         .append("boolean has")
                         .append(camelCase(paramName))
                         .append(" = false;")
                         .append(System.lineSeparator());
        }

        stringBuilder.append(indent(2)).append("while (ionReader.next() != null) {").append(System.lineSeparator());
        stringBuilder.append(indent(3)).append("switch (ionReader.getFieldName()) {").append(System.lineSeparator());
        for (Param param : params) {
            String paramName = param.name();
            Type paramType = param.type();

            stringBuilder.append(indent(4))
                         .append("case ")
                         .append(quote(paramName))
                         .append(":")
                         .append(System.lineSeparator());
            stringBuilder.append(indent(5))
                         .append(paramName)
                         .append(" = ")
                         .append(getGeneratorFactory().getGenerator(paramType).callDeserializer("ionReader"))
                         .append(";")
                         .append(System.lineSeparator());
            stringBuilder.append(indent(5))
                         .append("has")
                         .append(camelCase(paramName))
                         .append(" = true;")
                         .append(System.lineSeparator());
            stringBuilder.append(indent(5)).append("break;").append(System.lineSeparator());
        }
        stringBuilder.append(indent(3)).append("}").append(System.lineSeparator());
        stringBuilder.append(indent(2)).append("}").append(System.lineSeparator());

        stringBuilder.append("        ionReader.stepOut();").append(System.lineSeparator());
        stringBuilder.append(indent(2))
                     .append("return new ")
                     .append(getTypeName(deserializationType))
                     .append("(")
                     .append(params.stream().map(Param::name).collect(Collectors.joining(", ")))
                     .append(");")
                     .append(System.lineSeparator());

        stringBuilder.append(indent(1)).append("}").append(System.lineSeparator());
        return stringBuilder;
    }
}
