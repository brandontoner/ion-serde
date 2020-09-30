package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Generates serialization for arrays.
 */
final class ArrayGenerator extends MethodGenerator {
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
        super(generatorFactory, serializationConfig, generationContext, aClass);
        this.clazz = aClass;
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    public CharSequence generateSerializerBody() {
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
        return stringBuilder;
    }

    @Override
    public CharSequence generateDeserializerBody() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (ionReader.isNullValue()) {").append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());

        stringBuilder.append(indent(2)).append("ionReader.stepIn();").append(newline());
        stringBuilder.append(indent(2))
                     .append(getTypeName(clazz))
                     .append(" output = new ")
                     .append(getTypeName(clazz.getComponentType()))
                     .append("[10];")
                     .append(newline());
        stringBuilder.append(indent(2)).append(getTypeName(int.class)).append(" count = 0;").append(newline());
        stringBuilder.append(indent(2)).append("while (ionReader.next() != null) {").append(newline());
        stringBuilder.append(indent(3))
                     .append(getTypeName(clazz.getComponentType()))
                     .append(" element = ")
                     .append(getGeneratorFactory().getGenerator(clazz.getComponentType()).callDeserializer("ionReader"))
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

        stringBuilder.append(indent(2)).append("ionReader.stepOut();").append(newline());
        stringBuilder.append(indent(2)).append("return output;").append(newline());
        return stringBuilder;
    }
}
