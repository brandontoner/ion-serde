package com.brandontoner.ion.serde.testtypes;

import java.time.ZonedDateTime;

public class TemporalTestClass {
    private final ZonedDateTime aZonedDateTime;

    public TemporalTestClass(final ZonedDateTime aZonedDateTime) {
        this.aZonedDateTime = aZonedDateTime;
    }

    public ZonedDateTime getaZonedDateTime() {
        return aZonedDateTime;
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

        // using isEqual instead of equals because we drop the time zone name
        return aZonedDateTime != null ? aZonedDateTime.isEqual(that.aZonedDateTime) : that.aZonedDateTime == null;
    }

    @Override
    public int hashCode() {
        return aZonedDateTime != null ? aZonedDateTime.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TemporalTestClass{" + "aZonedDateTime=" + aZonedDateTime + '}';
    }
}
