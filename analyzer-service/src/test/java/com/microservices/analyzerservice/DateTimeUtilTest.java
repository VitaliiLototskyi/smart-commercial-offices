package com.microservices.analyzerservice;

import com.microservices.analyzerservice.model.analysis.TemporalRange;
import com.microservices.analyzerservice.utility.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {DateTimeUtil.class})
class DateTimeUtilTest {

    @Test
    void getDateTimeChunksWithHoursTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.withHour(1).withMinute(50);
        LocalDateTime endTime = now.withHour(4).withMinute(20);

        List<TemporalRange<LocalDateTime>> rt = DateTimeUtil.getDateTimeChunks(startTime, endTime, HOURS);
        assertNotNull(rt);
        assertEquals(2, rt.size());
        assertEquals(now.withHour(2).truncatedTo(HOURS), rt.get(0).getStart());
        assertEquals(now.withHour(3).truncatedTo(HOURS), rt.get(0).getEnd());
        assertEquals(now.withHour(3).truncatedTo(HOURS), rt.get(1).getStart());
        assertEquals(now.withHour(4).truncatedTo(HOURS), rt.get(1).getEnd());
    }

    @Test
    void getDateTimeChunksWithMonthsTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.withMonth(1).withDayOfMonth(12);
        LocalDateTime endTime = now.withMonth(4).withDayOfMonth(1);

        List<TemporalRange<LocalDateTime>> rt = DateTimeUtil.getDateTimeChunks(startTime, endTime, MONTHS);
        assertNotNull(rt);
        assertEquals(2, rt.size());
        assertEquals(now.withMonth(2).with(firstDayOfMonth()).toLocalDate().atStartOfDay(), rt.get(0).getStart());
        assertEquals(now.withMonth(3).with(firstDayOfMonth()).toLocalDate().atStartOfDay(), rt.get(0).getEnd());
        assertEquals(now.withMonth(3).with(firstDayOfMonth()).toLocalDate().atStartOfDay(), rt.get(1).getStart());
        assertEquals(now.withMonth(4).with(firstDayOfMonth()).toLocalDate().atStartOfDay(), rt.get(1).getEnd());
    }

    @Test
    void getDateTimeChunksWithYearsTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.withYear(2001).withMonth(5);
        LocalDateTime endTime = now.withYear(2004).withMonth(1);

        List<TemporalRange<LocalDateTime>> rt = DateTimeUtil.getDateTimeChunks(startTime, endTime, YEARS);
        assertNotNull(rt);
        assertEquals(2, rt.size());
        assertEquals(now.withYear(2002).with(firstDayOfYear()).toLocalDate().atStartOfDay(), rt.get(0).getStart());
        assertEquals(now.withYear(2003).with(firstDayOfYear()).toLocalDate().atStartOfDay(), rt.get(0).getEnd());
        assertEquals(now.withYear(2003).with(firstDayOfYear()).toLocalDate().atStartOfDay(), rt.get(1).getStart());
        assertEquals(now.withYear(2004).with(firstDayOfYear()).toLocalDate().atStartOfDay(), rt.get(1).getEnd());
    }
}