package com.brandontoner.ion.serde;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Generates IonReader / IonWriter serialization code for POJOs.
 * <p>
 * Class is not threadsafe, and is one-time-use.
 */
public final class IonSerializationGenerator {
    /** Serialization config. */
    private final SerializationConfig mSerializationConfig;
    /** Generation context. */
    private final GenerationContext mGenerationContext;
    /** Generator factory. */
    private final GeneratorFactory mGeneratorFactory;
    /** Name of the class to put the serializers in. */
    private final String mClassName;
    /** Name of the package to put the class in. */
    private final String mPackage;
    /** Generated source directory. */
    private final Path mSourceDirectory;
    /** Collection of types to generate. */
    private final Collection<Type> mTypes;

    /**
     * Constructor, use {@link #builder()} instead.
     *
     * @param builder non-bull builder
     */
    private IonSerializationGenerator(final Builder builder) {
        this.mSerializationConfig = Objects.requireNonNull(builder.getSerializationConfig(), "serialization config");
        this.mGenerationContext = Objects.requireNonNull(builder.getGenerationContext(), "generation context");
        this.mGeneratorFactory = Objects.requireNonNull(builder.getGeneratorFactory(), "generator factory");
        this.mSourceDirectory = Objects.requireNonNull(builder.getSourceDirectory(), "source directory");
        // Make immutable copies
        this.mClassName = Objects.requireNonNull(builder.getClassName(), "class name").toString();
        this.mPackage = Objects.requireNonNull(builder.getPackage(), "package").toString();
        this.mTypes = List.copyOf(Objects.requireNonNull(builder.getTypes(), "types"));
        if (mTypes.isEmpty()) {
            throw new IllegalArgumentException("no types provided");
        }
    }

    /**
     * Generates the serialization / deserialization source.
     *
     * @throws IOException on error writing to file
     */
    public void generate() throws IOException {
        Collection<CharSequence> generatedSource = new ArrayList<>();
        Queue<Type> toGenerate = new LinkedList<>(mTypes);
        Collection<Type> processed = new HashSet<>();
        Type t;
        while ((t = toGenerate.poll()) != null) {
            if (!processed.add(t)) {
                continue;
            }
            Generator generator = mGeneratorFactory.getGenerator(t);
            generatedSource.add(generator.generateSerializer());
            generatedSource.add(generator.generateDeserializer());
            toGenerate.addAll(generator.dependencies());
        }

        Path packageDir = mSourceDirectory;
        for (final String component : mPackage.split("\\.")) {
            packageDir = packageDir.resolve(component);
        }
        Files.createDirectories(packageDir);
        Path outputFile = packageDir.resolve(mClassName + ".java");

        try (OutputStream outputStream = Files.newOutputStream(outputFile);
             PrintWriter printWriter = new PrintWriter(outputStream, false, StandardCharsets.UTF_8)) {
            printWriter.println("package " + mPackage + ';');
            printWriter.println();
            for (final Class<?> anImport : mGenerationContext.imports()) {
                printWriter.println("import " + anImport.getName() + ';');
            }
            printWriter.println();
            printWriter.println("public final class " + mClassName + " {");
            printWriter.print(generatedSource.stream()
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.joining(mSerializationConfig.newline())));
            printWriter.println("}");
        }
    }

    /**
     * Gets a new builder.
     *
     * @return new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for IonSerializationGenerator. */
    public static final class Builder {
        /** Serialization config. */
        private final SerializationConfig mSerializationConfig = new SerializationConfig();
        /** Generation context. */
        private final GenerationContext mGenerationContext = new GenerationContext();
        /** Generator factory. */
        private final GeneratorFactory mGeneratorFactory =
                new GeneratorFactory(mSerializationConfig, mGenerationContext);
        /** Collection of types to generate. */
        private final List<Type> mTypes = new ArrayList<>();
        /** Name of the class to put the serializers in. */
        private CharSequence mClassName;
        /** Name of the package to put the class in. */
        private CharSequence mPackage;
        /** Generated source directory. */
        private Path mSourceDirectory;

        /** Prevents instantiation, use {@link IonSerializationGenerator#builder()}. */
        private Builder() {
            // noop
        }

        /**
         * Gets the serialization config.
         *
         * @return serialization config
         */
        SerializationConfig getSerializationConfig() {
            return mSerializationConfig;
        }

        /**
         * Gets the generation context.
         *
         * @return generation context
         */
        GenerationContext getGenerationContext() {
            return mGenerationContext;
        }

        /**
         * Gets the generator factory.
         *
         * @return generator factory
         */
        GeneratorFactory getGeneratorFactory() {
            return mGeneratorFactory;
        }

        /**
         * Sets the generated source directory (required). Output class file will be at {@code source
         * directory/package/class name.java}.
         *
         * @param sourceDir source directory
         * @return builder with the source directory set.
         */
        public Builder withSourceDirectory(final Path sourceDir) {
            this.mSourceDirectory = sourceDir;
            return this;
        }

        /**
         * Gets the generated source directory.
         *
         * @return generated source directory
         */
        Path getSourceDirectory() {
            return mSourceDirectory;
        }

        /**
         * Sets the generated source class name (required). Output class file will be at {@code source
         * directory/package/class name.java}.
         *
         * @param className source directory
         * @return builder with the class name set.
         */
        public Builder withClassName(final CharSequence className) {
            this.mClassName = className;
            return this;
        }

        /**
         * Gets the generated source class name.
         *
         * @return generated source class name
         */
        CharSequence getClassName() {
            return mClassName;
        }

        /**
         * Sets the generated source package (required). Output class file will be at {@code source
         * directory/package/class name.java}.
         *
         * @param newPackage java package for the generated code
         * @return builder with the java package set.
         */
        public Builder withPackage(final CharSequence newPackage) {
            this.mPackage = newPackage;
            return this;
        }

        /**
         * Gets the java package for the generated code.
         *
         * @return java package for the generated code.
         */
        CharSequence getPackage() {
            return mPackage;
        }

        /**
         * Adds zero or more types to be generated (required). Repeated invocations will add to, not replace old
         * values.
         *
         * @param types types to add
         * @return builder with types added
         */
        public Builder addType(final Type... types) {
            Collections.addAll(mTypes, types);
            return this;
        }

        /**
         * Adds zero or more types to be generated (required). Repeated invocations will add to, not replace old
         * values.
         *
         * @param types types to add
         * @return builder with types added
         */
        public Builder addType(final Collection<? extends Type> types) {
            mTypes.addAll(types);
            return this;
        }

        /**
         * Gets the collection of types to generate.
         *
         * @return collection of types to generate
         */
        Collection<Type> getTypes() {
            return mTypes;
        }

        /**
         * Adds an implementation class for an interface or abstract class. The implementation class will be used during
         * deserialization.
         *
         * @param iface interface
         * @param impl  implementation class
         * @param <T>   type
         * @return builder with implementation added
         */
        public <T> Builder addImplementation(final Class<T> iface, final Class<? extends T> impl) {
            mSerializationConfig.addImplementation(iface, impl);
            return this;
        }

        /**
         * Adds a custom generator. Generator will be used preferentially over built-in implementations.
         *
         * @param type              type of the generator
         * @param generatorFunction function which creates a generator, will be invoked immediately
         * @return builder with generator added
         */
        public Builder addGenerator(final Type type, final GeneratorFunction generatorFunction) {
            mSerializationConfig.addGenerator(type,
                                              generatorFunction.apply(mGeneratorFactory,
                                                                      mSerializationConfig,
                                                                      mGenerationContext));
            return this;
        }

        /**
         * Builds the generator.
         *
         * @return generator
         */
        public IonSerializationGenerator build() {
            return new IonSerializationGenerator(this);
        }
    }
}
