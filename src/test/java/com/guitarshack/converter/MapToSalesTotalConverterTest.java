package com.guitarshack.converter;

import com.guitarshack.domain.SalesTotal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapToSalesTotalConverterTest {

    private MapToSalesTotalConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new MapToSalesTotalConverter();
    }

    @Test
    void shouldConvert() {

        // GIVEN
        Map<String, Object> input = Map.of("total", 1.0);

        // WHEN
        SalesTotal result = underTest.convert(input);

        // THEN
        assertThat(result).isEqualTo(new SalesTotal(1));
    }
}