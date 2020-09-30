package com.brandontoner.ion.serde.testtypes;

import java.util.Objects;

public class StringTestClass {
    private final String aString;

    public StringTestClass(final String aString) {
        this.aString = aString;
    }

    public String getaString() {
        return aString;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StringTestClass)) {
            return false;
        }
        StringTestClass that = (StringTestClass) o;
        return Objects.equals(aString, that.aString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aString);
    }

    @Override
    public String toString() {
        return "StringTestClass{" + "aString='" + aString + '\'' + '}';
    }
}
