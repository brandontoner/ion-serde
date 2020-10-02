package com.brandontoner.ion.serde.testtypes;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public class TemporalTestClass {
    private final OffsetDateTime aOffsetDateTime;

    public TemporalTestClass(final OffsetDateTime aOffsetDateTime) {
        this.aOffsetDateTime = aOffsetDateTime;
    }

    public OffsetDateTime getaZonedDateTime() {
        return aOffsetDateTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemporalTestClass)) {
            return false;
        }
        TemporalTestClass that = (TemporalTestClass) o;
        return Objects.equals(aOffsetDateTime, that.aOffsetDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aOffsetDateTime);
    }

    @Override
    public String toString() {
        return "TemporalTestClass{" + "aOffsetDateTime=" + aOffsetDateTime + '}';
    }
}
