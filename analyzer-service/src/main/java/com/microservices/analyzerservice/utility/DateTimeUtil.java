package com.microservices.analyzerservice.utility;

import com.microservices.analyzerservice.model.analysis.TemporalRange;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.*;
import static java.time.temporal.ChronoUnit.MILLIS;

public final class DateTimeUtil {

    private DateTimeUtil() {
    }

    public static List<TemporalRange<LocalDateTime>> getDateTimeChunks(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit unit) {
        List<TemporalRange<LocalDateTime>> rtList = new ArrayList<>();

        LocalDateTime chunkEndTime = truncateDateTime(endTime, unit);
        LocalDateTime chunkStartTime = chunkEndTime;
        while (chunkStartTime.minus(1, unit).isAfter(startTime)) {
            chunkEndTime = chunkStartTime;
            chunkStartTime = chunkEndTime.minus(1, unit);
            rtList.add(new TemporalRange<>(chunkStartTime, chunkEndTime));
        }
        Collections.reverse(rtList);
        return rtList;
    }

    public static LocalDateTime truncateDateTime(LocalDateTime dateTime, ChronoUnit unit) {
        if (dateTime == null || unit == null) {
            return null;
        }
        switch (unit) {
            case MILLIS:
            case SECONDS:
            case MINUTES:
            case HOURS:
                return dateTime.truncatedTo(unit);
            case DAYS:
                return dateTime.toLocalDate().atStartOfDay();
            case MONTHS:
                return dateTime.withDayOfMonth(1).toLocalDate().atStartOfDay();
            case YEARS:
                return dateTime.withDayOfYear(1).toLocalDate().atStartOfDay();
            default:
                throw new IllegalArgumentException("Unsupported unit " + unit.name());
        }
    }
}
