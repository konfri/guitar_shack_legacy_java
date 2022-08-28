package com.guitarshack.calculator;

import com.guitarshack.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class ProductReorderCalculatorTest {

    private ProductReorderCalculator underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProductReorderCalculator();
    }

    @Test
    void shouldAlertHigherDemand() {

        // GIVEN
        Product product = createProduktBuilder()
                .leadTime(new LeadTime(2))
                .build();
        Quantity quantity = creatQuantity();
        SalesTotal salesTotal = new SalesTotal(30);

        // WHEN
        boolean result = underTest.shouldReorder(product, quantity, salesTotal);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void shouldAlertWithEqualDemand() {

        // GIVEN
        Product product = createProduktBuilder()
                .leadTime(new LeadTime(1))
                .build();
        Quantity quantity = creatQuantity();
        SalesTotal salesTotal = new SalesTotal(30);

        // WHEN
        boolean result = underTest.shouldReorder(product, quantity, salesTotal);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void shouldSkipAlertWithLowDemand() {

        // GIVEN
        Product product = createProduktBuilder()
                .leadTime(new LeadTime(1))
                .build();
        Quantity quantity = creatQuantity();
        SalesTotal salesTotal = new SalesTotal(0);

        // WHEN
        boolean result = underTest.shouldReorder(product, quantity, salesTotal);

        // THEN
        assertThat(result).isFalse();
    }

    private static Product.ProductBuilder createProduktBuilder() {
        return Product.builder()
                .productId(new ProductId(1))
                .stock(new Stock(2));
    }

    private static Quantity creatQuantity() {
        return new Quantity(1);
    }
}