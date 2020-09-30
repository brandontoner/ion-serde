package com.brandontoner.ion.serde;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonWriter;
import com.amazon.ion.system.IonSystemBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.brandontoner.ion.serde.testtypes.BlobTestClass;
import com.brandontoner.ion.serde.testtypes.BooleanTestClass;
import com.brandontoner.ion.serde.testtypes.DoubleTestClass;
import com.brandontoner.ion.serde.testtypes.IntTestClass;
import com.brandontoner.ion.serde.testtypes.LongTestClass;
import com.brandontoner.ion.serde.testtypes.StringTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IonSerializationGeneratorTest {
    private final IonSystem ionSystem = IonSystemBuilder.standard().build();

    @BeforeAll
    static void generate() throws IOException {
        IonSerializationGenerator.builder()
                                 .withSourceDirectory("C:\\Users\\brand\\IdeaProjects\\ion-serde\\src\\test\\java")
                                 .withPackage("com.brandontoner.ion.serde")
                                 .withClassName("Serializers")
                                 .addType(IntTestClass.class, LongTestClass.class, BooleanTestClass.class,
                                          DoubleTestClass.class, BlobTestClass.class, StringTestClass.class)
                                 .withLineSeparator("\n")
                                 .build()
                                 .generate();
    }

    @MethodSource("roundtripTestArgs")
    @ParameterizedTest
    <T> void roundtripTest(Type type, T value, Serializer<T> serializer, Deserializer<T> deserializer)
            throws IOException {
        byte[] data;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (IonWriter ionWriter = IonTextWriterBuilder.pretty().build(byteArrayOutputStream)) {
                serializer.serialize(value, ionWriter);
            }
            data = byteArrayOutputStream.toByteArray();
            System.err.println(new String(data, StandardCharsets.UTF_8));
        }

        try (IonReader ionReader = ionSystem.newReader(data)) {
            ionReader.hasNext();
            T output = deserializer.deserialize(ionReader);
            Assertions.assertEquals(value, output);
        }
    }

    public static Collection<Arguments> roundtripTestArgs() {
        List<Arguments> arguments = new ArrayList<>();

        for (IntTestClass permutation : TestInstanceGenerator.<IntTestClass>getPermutations(IntTestClass.class)) {
            arguments.add(Arguments.arguments(IntTestClass.class, permutation, new Serializer<IntTestClass>() {
                @Override
                public void serialize(IntTestClass o, IonWriter ionWriter) throws IOException {
                   Serializers.serializeIntTestClass(o, ionWriter);
                }
            }, new Deserializer<IntTestClass>() {
                @Override
                public IntTestClass deserialize(IonReader ionReader) throws IOException {
                    return Serializers.deserializeIntTestClass(ionReader);
                }
            }));
        }

        for (LongTestClass permutation : TestInstanceGenerator.<LongTestClass>getPermutations(LongTestClass.class)) {
            arguments.add(Arguments.arguments(IntTestClass.class, permutation, new Serializer<LongTestClass>() {
                @Override
                public void serialize(LongTestClass o, IonWriter ionWriter) throws IOException {
                    Serializers.serializeLongTestClass(o, ionWriter);
                }
            }, new Deserializer<LongTestClass>() {
                @Override
                public LongTestClass deserialize(IonReader ionReader) throws IOException {
                    return Serializers.deserializeLongTestClass(ionReader);
                }
            }));
        }

        for (BooleanTestClass permutation : TestInstanceGenerator.<BooleanTestClass>getPermutations(BooleanTestClass.class)) {
            arguments.add(Arguments.arguments(BooleanTestClass.class, permutation, new Serializer<BooleanTestClass>() {
                @Override
                public void serialize(BooleanTestClass o, IonWriter ionWriter) throws IOException {
                    Serializers.serializeBooleanTestClass(o, ionWriter);
                }
            }, new Deserializer<BooleanTestClass>() {
                @Override
                public BooleanTestClass deserialize(IonReader ionReader) throws IOException {
                    return Serializers.deserializeBooleanTestClass(ionReader);
                }
            }));
        }

        for (DoubleTestClass permutation : TestInstanceGenerator.<DoubleTestClass>getPermutations(DoubleTestClass.class)) {
            arguments.add(Arguments.arguments(DoubleTestClass.class, permutation, new Serializer<DoubleTestClass>() {
                @Override
                public void serialize(DoubleTestClass o, IonWriter ionWriter) throws IOException {
                    Serializers.serializeDoubleTestClass(o, ionWriter);
                }
            }, new Deserializer<DoubleTestClass>() {
                @Override
                public DoubleTestClass deserialize(IonReader ionReader) throws IOException {
                    return Serializers.deserializeDoubleTestClass(ionReader);
                }
            }));
        }

        for (BlobTestClass permutation : TestInstanceGenerator.<BlobTestClass>getPermutations(BlobTestClass.class)) {
            arguments.add(Arguments.arguments(BlobTestClass.class, permutation, new Serializer<BlobTestClass>() {
                @Override
                public void serialize(BlobTestClass o, IonWriter ionWriter) throws IOException {
                    Serializers.serializeBlobTestClass(o, ionWriter);
                }
            }, new Deserializer<BlobTestClass>() {
                @Override
                public BlobTestClass deserialize(IonReader ionReader) throws IOException {
                    return Serializers.deserializeBlobTestClass(ionReader);
                }
            }));
        }

        for (StringTestClass permutation : TestInstanceGenerator.<StringTestClass>getPermutations(StringTestClass.class)) {
            arguments.add(Arguments.arguments(StringTestClass.class, permutation, new Serializer<StringTestClass>() {
                @Override
                public void serialize(StringTestClass o, IonWriter ionWriter) throws IOException {
                    Serializers.serializeStringTestClass(o, ionWriter);
                }
            }, new Deserializer<StringTestClass>() {
                @Override
                public StringTestClass deserialize(IonReader ionReader) throws IOException {
                    return Serializers.deserializeStringTestClass(ionReader);
                }
            }));
        }
        return arguments;
    }

    interface Serializer<T> {
        void serialize(T t, IonWriter ionWriter) throws IOException;
    }

    interface Deserializer<T> {
        T deserialize(IonReader ionReader) throws IOException;
    }
}