package com.brandontoner.ion.serde;

/**
 * Function which creates a Generator from the required parameters.
 */
@FunctionalInterface
public interface GeneratorFunction {
    /**
     * Creates a new generator with the given parameters.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization configuration
     * @param generationContext   generation context
     * @return a new, non-null generator
     */
    Generator apply(GeneratorFactory generatorFactory,
                    SerializationConfig serializationConfig,
                    GenerationContext generationContext);
}
