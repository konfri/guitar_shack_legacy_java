package com.guitarshack.calculator;

import com.guitarshack.TimeProvider;
import com.guitarshack.domain.ProductId;
import com.guitarshack.domain.SalesPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class SalesPeriodCalculator {

    private final TimeProvider timeProvider;

    public SalesPeriod calculateSalesInput(ProductId productId) {

        ZoneOffset zone = ZoneOffset.UTC;
        Instant endAsInstant = ZonedDateTime.ofInstant(timeProvider.now(), zone).minusYears(1).toInstant();

        LocalDate start = LocalDate.ofInstant(endAsInstant.minus(30, ChronoUnit.DAYS), zone);
        LocalDate end = LocalDate.ofInstant(endAsInstant, zone);

        return SalesPeriod.builder()
                .productId(productId)
                .startDate(start)
                .endDate(end)
                .build();
    }
}
