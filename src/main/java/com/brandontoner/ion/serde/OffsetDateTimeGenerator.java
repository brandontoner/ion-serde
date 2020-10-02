package com.brandontoner.ion.serde;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Set;

import com.amazon.ion.IonType;
import com.amazon.ion.Timestamp;

/**
 * Serializer for OffsetDateTime.
 */
final class OffsetDateTimeGenerator extends NullableGenerator {

    /**
     * Constructor.
     *
     * @param generatorFactory    generator factory
     * @param serializationConfig serialization config
     * @param generationContext   generation context
     */
    OffsetDateTimeGenerator(final GeneratorFactory generatorFactory,
                            final SerializationConfig serializationConfig,
                            final GenerationContext generationContext) {
        super(generatorFactory, serializationConfig, generationContext, OffsetDateTime.class, IonType.TIMESTAMP);
    }

    @Override
    public Collection<Type> dependencies() {
        return Set.of();
    }

    @Override
    protected CharSequence generateNonNullSerializerBody(final String valueName, final String ionWriterName) {
        return new StringBuilder().append(indent(2))
                                  .append(ionWriterName)
                                  .append(".writeTimestamp(")
                                  .append(getTypeName(Timestamp.class))
                                  .append(".forEpochSecond(")
                                  .append(valueName)
                                  .append(".toEpochSecond()")
                                  .append(", ")
                                  .append(valueName)
                                  .append(".getNano()")
                                  .append(", ")
                                  .append(valueName)
                                  .append(".getOffset().getTotalSeconds() / 60));")
                                  .append(newline());
    }

    @Override
    protected CharSequence generateNonNullDeserializerBody(final String ionReaderName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent(2))
                     .append(getTypeName(Timestamp.class))
                     .append(" timestamp = ")
                     .append(ionReaderName)
                     .append(".timestampValue();")
                     .append(newline());
        stringBuilder.append(indent(2))
                     .append(getTypeName(ZoneOffset.class))
                     .append(" zoneOffset = ")
                     .append(getTypeName(ZoneOffset.class))
                     .append(".ofTotalSeconds(timestamp.getLocalOffset() * 60);")
                     .append(newline());
        stringBuilder.append(indent(2))
                     .append(getTypeName(BigDecimal.class))
                     .append(" decimalSecond = timestamp.getDecimalSecond();")
                     .append(newline());
        stringBuilder.append(indent(2)).append("int seconds = decimalSecond.intValue();").append(newline());
        stringBuilder.append(indent(2))
                     .append("int nanos = decimalSecond.subtract(BigDecimal.valueOf(seconds)).movePointRight(9).intValue();")
                     .append(newline());
        stringBuilder.append(indent(2))
                     .append("return ")
                     .append(getTypeName(OffsetDateTime.class))
                     .append(".of(timestamp.getYear(), timestamp.getMonth(), timestamp.getDay(), timestamp.getHour(), timestamp.getMinute(), seconds, nanos, zoneOffset);")
                     .append(newline());
        return stringBuilder;
    }
}
