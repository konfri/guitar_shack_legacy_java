package com.guitarshack.converter;

import com.guitarshack.domain.LeadTime;
import com.guitarshack.domain.Product;
import com.guitarshack.domain.ProductId;
import com.guitarshack.domain.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapToProductConverterTest {

    private MapToProductConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new MapToProductConverter();
    }

    @Test
    void shouldConvert() {

        // GIVEN
        Map<String, Object> input = Map.of(
                "id", 1.0,
                "stock", 2.0,
                "leadTime", 3.0);

        // WHEN
        Product result = underTest.convert(input);

        // THEN
        Product expected = Product.builder()
                .productId(new ProductId(1))
                .stock(new Stock(2))
                .leadTime(new LeadTime(3))
                .build();
        assertThat(result).isEqualTo(expected);
    }
}