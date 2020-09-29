package com.brandontoner.ion.serde;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

public final class Param {
    private final String name;
    private final Type type;
    private final Method getter;

    public Param(final String name, final Type type, final Method getter) {
        this.name = name;
        this.type = type;
        this.getter = getter;
    }

    public String name() {
        return name;
    }

    public Type type() {
        return type;
    }

    public Method getter() {
        return getter;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Param) obj;
        return Objects.equals(this.name, that.name)
               && Objects.equals(this.type, that.type)
               && Objects.equals(this.getter, that.getter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, getter);
    }

    @Override
    public String toString() {
        return "Param[" + "name=" + name + ", " + "type=" + type + ", " + "getter=" + getter + ']';
    }
}
