package com.brandontoner.ion.serde;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.amazon.ion.IonType;

final class PojoGenerator extends NullableGenerator {
    private final Type serializationType;
    private final Type deserializationType;
    private final List<Param> params;

    private PojoGenerator(final Type serializationType,
                          final Type deserializationType,
                          final List<Param> params,
                          final SerializationConfig serializationConfig,
                          final GenerationContext generationContext,
                          final GeneratorFactory generatorFactory) {
        super(generatorFactory,
              serializationConfig,
              generationContext,
              serializationType,
              deserializationType,
              IonType.STRUCT);
        // TODO make sure the types make sense
        // * deserializationType must be a subclass / implement serialization type
        // * deserializationType must have a constructor with the provided parameters
        this.serializationType = serializationType;
        this.deserializationType = deserializationType;
        this.params = params;
    }

    static PojoGenerator create(final GeneratorFactory generatorFactory,
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
    public CharSequence generateNonNullSerializerBody(final String valueName, final String ionWriterName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2))
                     .append(ionWriterName)
                     .append(".stepIn(")
                     .append(getTypeName(IonType.class))
                     .append(".STRUCT);")
                     .append(newline());

        for (final Param param : params) {
            String paramName = param.name();
            Type paramType = param.type();
            stringBuilder.append(indent(2))
                         .append(ionWriterName)
                         .append(".setFieldName(")
                         .append(quote(paramName))
                         .append(");")
                         .append(newline());
            stringBuilder.append(indent(2))
                         .append(getGeneratorFactory().getGenerator(paramType)
                                                      .callSerializer(valueName + "." + param.getter().getName() + "()",
                                                                      ionWriterName))
                         .append(';')
                         .append(newline());

        }
        stringBuilder.append(indent(2)).append(ionWriterName).append(".stepOut();").append(newline());
        return stringBuilder;
    }

    @Override
    public CharSequence generateNonNullDeserializerBody(final String ionReaderName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append(ionReaderName).append(".stepIn();").append(newline());

        for (final Param param : params) {
            String paramName = param.name();
            Type paramType = param.type();

            stringBuilder.append(indent(2))
                         .append(getTypeName(paramType))
                         .append(' ')
                         .append(paramName)
                         .append(" = ")
                         .append(getGeneratorFactory().getGenerator(paramType).defaultValue())
                         .append(';')
                         .append(newline());
            stringBuilder.append(indent(2))
                         .append("boolean has")
                         .append(camelCase(paramName))
                         .append(" = false;")
                         .append(newline());
        }

        stringBuilder.append(indent(2))
                     .append("while (")
                     .append(ionReaderName)
                     .append(".next() != null) {")
                     .append(newline());
        stringBuilder.append(indent(3))
                     .append("switch (")
                     .append(ionReaderName)
                     .append(".getFieldName()) {")
                     .append(newline());
        for (final Param param : params) {
            String paramName = param.name();
            Type paramType = param.type();

            stringBuilder.append(indent(4)).append("case ").append(quote(paramName)).append(':').append(newline());
            stringBuilder.append(indent(5))
                         .append(paramName)
                         .append(" = ")
                         .append(getGeneratorFactory().getGenerator(paramType).callDeserializer(ionReaderName))
                         .append(';')
                         .append(newline());
            stringBuilder.append(indent(5))
                         .append("has")
                         .append(camelCase(paramName))
                         .append(" = true;")
                         .append(newline());
            stringBuilder.append(indent(5)).append("break;").append(newline());
        }
        stringBuilder.append(indent(3)).append('}').append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());

        stringBuilder.append("        ").append(ionReaderName).append(".stepOut();").append(newline());
        stringBuilder.append(indent(2))
                     .append("return new ")
                     .append(getTypeName(deserializationType))
                     .append('(')
                     .append(params.stream().map(Param::name).collect(Collectors.joining(", ")))
                     .append(");")
                     .append(newline());
        return stringBuilder;
    }
}
