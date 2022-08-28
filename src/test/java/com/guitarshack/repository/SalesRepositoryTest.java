package com.guitarshack.repository;

import com.guitarshack.HttpGetRequester;
import com.guitarshack.UriConfig;
import com.guitarshack.converter.MapToSalesTotalConverter;
import com.guitarshack.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SalesRepositoryTest {

    @Mock
    private MapToSalesTotalConverter mapToSalesTotalConverter;
    @Mock
    private UriConfig uriConfig;
    @Mock
    private HttpGetRequester httpGetRequester;

    private SalesRepository underTest;

    @Mock
    private Map<String, Object> map;
    @Mock
    private SalesTotal salesTotal;

    @BeforeEach
    void setUp() {
        underTest = new SalesRepository(mapToSalesTotalConverter, uriConfig, httpGetRequester);
    }

    @Test
    void shouldRead() {

        // GIVEN
        given(uriConfig.getSales()).willReturn("salesUri");
        SalesPeriod input = SalesPeriod.builder()
                .productId(new ProductId(1))
                .startDate(LocalDate.parse("2020-11-24"))
                .endDate(LocalDate.parse("2020-12-24"))
                .build();

        GetRequestParameter getRequestParameter = GetRequestParameter.builder()
                .baseUri("salesUri")
                .parameter(new GetParameter("productId", 1))
                .parameter(new GetParameter("startDate", "11/24/2020"))
                .parameter(new GetParameter("endDate", "12/24/2020"))
                .parameter(new GetParameter("action", "total"))
                .build();

        given(httpGetRequester.request(getRequestParameter)).willReturn(map);
        given(mapToSalesTotalConverter.convert(map)).willReturn(salesTotal);

        // WHEN
        SalesTotal result = underTest.read(input);

        // THEN
        assertThat(result).isEqualTo(salesTotal);
    }
}