package com.guitarshack;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.guitarshack.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@WireMockTest
public class ReorderMonitorServiceEndToEndIT {

    @MockBean
    private TimeProvider timeProvider;
    @MockBean
    private ProductReorderNotifier productReorderNotifier;
    @MockBean
    private UriConfig uriConfig;

    @Autowired
    private ReorderMonitorService reorderMonitorService;

    @Test
    void shouldAlert(WireMockRuntimeInfo wireMockRuntimeInfo) {

        // GIVEN
        given(timeProvider.now()).willReturn(Instant.parse("2020-12-24T18:35:24.00Z"));
        given(uriConfig.getProdukt()).willReturn(String.format("http://localhost:%d/default/produkt", wireMockRuntimeInfo.getHttpPort()));
        given(uriConfig.getSales()).willReturn(String.format("http://localhost:%d/default/sales", wireMockRuntimeInfo.getHttpPort()));
        stubFor(WireMock.get("/default/produkt?id=1&").willReturn(ok("""
                {
                    "id": 1,
                    "stock": 2,
                    "leadTime": 3
                }""")));


        stubFor(WireMock.get("/default/sales?productId=1&startDate=11/24/2020&endDate=12/24/2020&action=total&").willReturn(ok("""
                {
                    "total": 1
                }""")));

        final ProductId productId = new ProductId(1);
        final Quantity quantity = new Quantity(2);

        // WHEN
        reorderMonitorService.sold(productId, quantity);

        // THEN
        Product expected = Product.builder()
                .productId(productId)
                .stock(new Stock(2))
                .leadTime(new LeadTime(3))
                .build();
        verify(productReorderNotifier).notify(expected);
    }
}
