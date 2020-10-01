package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class SerializationConfig {
    private final Map<Class<?>, Class<?>> implementations = new HashMap<>();
    private final Map<Type, Generator> generators = new HashMap<>();
    private String mNewLine = System.lineSeparator();

    public <T> SerializationConfig addImplementation(final Class<T> iface, final Class<? extends T> impl) {
        implementations.put(iface, impl);
        return this;
    }

    public <T> Class<T> getImplementation(final Class<T> clazz) {
        Class<T> output = (Class<T>) implementations.getOrDefault(clazz, clazz);
        if (output.isInterface()) {
            throw new IllegalArgumentException(clazz + " is an interface");
        }
        return output;
    }

    public SerializationConfig addGenerator(final Type type, final Generator generator) {
        generators.put(type, generator);
        return this;
    }

    public Generator getGenerator(final Type type) {
        return generators.get(type);
    }

    public CharSequence indent(final int i) {
        // TODO make indentation configurable
        return "    ".repeat(i);
    }

    /**
     * Sets the line separator string.
     *
     * @param s line separator string
     * @return builder with line separator string set
     */
    public SerializationConfig withLineSeparator(final CharSequence s) {
        mNewLine = s.toString();
        return this;
    }

    /**
     * Returns the line separator string.
     *
     * @return the line separator string
     */
    public CharSequence newline() {
        return mNewLine;
    }
}
