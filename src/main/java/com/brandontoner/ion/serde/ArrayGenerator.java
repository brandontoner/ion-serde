package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Generates serialization for arrays.
 */
final class ArrayGenerator extends NullableGenerator {
    /** Array type. */
    private final Class<?> clazz;

    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     * @param aClass              array type to generate
     */
    ArrayGenerator(final GeneratorFactory generatorFactory,
                   final SerializationConfig serializationConfig,
                   final GenerationContext generationContext,
                   final Class<?> aClass) {
        super(generatorFactory, serializationConfig, generationContext, aClass, IonType.LIST);
        this.clazz = aClass;
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    public CharSequence generateNonNullSerializerBody(final String valueName, final String ionWriterName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2))
                     .append(ionWriterName)
                     .append(".stepIn(")
                     .append(getTypeName(IonType.class))
                     .append(".LIST);")
                     .append(newline());
        stringBuilder.append(indent(2))
                     .append("for (")
                     .append(getTypeName(clazz.getComponentType()))
                     .append(" e : ")
                     .append(valueName)
                     .append(") {")
                     .append(newline());
        stringBuilder.append(indent(3))
                     .append(getGeneratorFactory().getGenerator(clazz.getComponentType())
                                                  .callSerializer("e", ionWriterName))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(indent(2)).append(ionWriterName).append(".stepOut();").append(newline());
        return stringBuilder;
    }

    @Override
    public final CharSequence generateNonNullDeserializerBody(final String ionReaderName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append(ionReaderName).append(".stepIn();").append(newline());
        stringBuilder.append(indent(2))
                     .append(getTypeName(clazz))
                     .append(" output = new ")
                     .append(getTypeName(clazz.getComponentType()))
                     .append("[10];")
                     .append(newline());
        stringBuilder.append(indent(2)).append(getTypeName(int.class)).append(" count = 0;").append(newline());
        stringBuilder.append(indent(2))
                     .append("while (")
                     .append(ionReaderName)
                     .append(".next() != null) {")
                     .append(newline());
        stringBuilder.append(indent(3))
                     .append(getTypeName(clazz.getComponentType()))
                     .append(" element = ")
                     .append(getGeneratorFactory().getGenerator(clazz.getComponentType())
                                                  .callDeserializer(ionReaderName))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(3)).append("if (output.length == count) {").append(newline());
        stringBuilder.append(indent(4))
                     .append("output = ")
                     .append(getTypeName(Arrays.class))
                     .append(".copyOf(output, count * 2);")
                     .append(newline());
        stringBuilder.append(indent(3)).append('}').append(newline());
        stringBuilder.append(indent(3)).append("output[count++] = element;").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        stringBuilder.append(indent(2))
                     .append("output = ")
                     .append(getTypeName(Arrays.class))
                     .append(".copyOfRange(output, 0, count);")
                     .append(newline());

        stringBuilder.append(indent(2)).append(ionReaderName).append(".stepOut();").append(newline());
        stringBuilder.append(indent(2)).append("return output;").append(newline());
        return stringBuilder;
    }
}
