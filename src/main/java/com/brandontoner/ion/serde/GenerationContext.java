package com.brandontoner.ion.serde;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class GenerationContext {
    private final Map<String, Class<?>> imports = new HashMap<>();

    public String tryImport(final Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return clazz.getName();
        }
        String simpleName = clazz.getSimpleName();

        Class<?> existing = imports.putIfAbsent(simpleName, clazz);
        if (existing == null) {
            // no existing value,
            return simpleName;
        }
        if (existing.equals(clazz)) {
            return simpleName;
        }
        return clazz.getName();
    }

    public Iterable<? extends Class<?>> imports() {
        return imports.values()
                      .stream()
                      .sorted(Comparator.comparing(Class::getName))
                      .collect(Collectors.toUnmodifiableList());
    }
}
