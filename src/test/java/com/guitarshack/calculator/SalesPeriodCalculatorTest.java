package com.guitarshack.calculator;

import com.guitarshack.TimeProvider;
import com.guitarshack.domain.ProductId;
import com.guitarshack.domain.SalesPeriod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SalesPeriodCalculatorTest {

    @Mock
    private TimeProvider timeProvider;

    private SalesPeriodCalculator unterTest;

    @Mock
    private ProductId productId;

    @BeforeEach
    void setUp() {

        unterTest = new SalesPeriodCalculator(timeProvider);
    }

    @Test
    void shouldCalculate() {

        // GIVEN
        given(timeProvider.now()).willReturn(Instant.parse("2020-12-24T18:35:24.00Z"));

        // WHEN
        SalesPeriod result = unterTest.calculateSalesInput(productId);

        // THEN
        SalesPeriod expected = SalesPeriod.builder()
                .productId(productId)
                .startDate(LocalDate.parse("2020-11-24"))
                .endDate(LocalDate.parse("2020-12-24"))
                .build();
        assertThat(result).isEqualTo(expected);
    }
}