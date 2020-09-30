package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;

/**
 * Serializer for boxed primitives.
 */
final class BoxedPrimitiveGenerator extends MethodGenerator {
    private final Class<?> mBoxedClass;
    private final Class<?> mPrimitiveClass;
    private final IonType mIonType;

    /**
     * Constructor.
     *
     * @param <T>                 type
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     * @param primitiveClass      primitive class
     * @param boxedClass          boxed class
     * @param ionType
     */
    <T> BoxedPrimitiveGenerator(final GeneratorFactory generatorFactory,
                                final SerializationConfig serializationConfig,
                                final GenerationContext generationContext,
                                final Class<T> primitiveClass,
                                final Class<T> boxedClass,
                                final IonType ionType) {
        super(generatorFactory, serializationConfig, generationContext, boxedClass);
        // TODO make sure types make sense
        mPrimitiveClass = primitiveClass;
        mBoxedClass = boxedClass;
        mIonType = ionType;
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of(mPrimitiveClass);
    }

    @Override
    public CharSequence generateSerializerBody() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (v == null) {").append(newline());
        stringBuilder.append(indent(3))
                     .append("ionWriter.writeNull(")
                     .append(getTypeName(IonType.class))
                     .append('.')
                     .append(mIonType.name())
                     .append(");")
                     .append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3))
                     .append(getGeneratorFactory().getGenerator(mPrimitiveClass).callSerializer("v", "ionWriter"))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        return stringBuilder;
    }

    @Override
    public CharSequence generateDeserializerBody() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2)).append("if (ionReader.isNullValue()) {").append(newline());
        stringBuilder.append(indent(3)).append("return null;").append(newline());
        stringBuilder.append(indent(2)).append("} else {").append(newline());
        stringBuilder.append(indent(3))
                     .append("return ")
                     .append(getGeneratorFactory().getGenerator(mPrimitiveClass).callDeserializer("ionReader"))
                     .append(';')
                     .append(newline());
        stringBuilder.append(indent(2)).append('}').append(newline());
        return stringBuilder;
    }
}
