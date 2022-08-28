package com.guitarshack;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.guitarshack.domain.GetParameter;
import com.guitarshack.domain.GetRequestParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
class HttpGetRequesterIT {

    private HttpGetRequester underTest;

    @BeforeEach
    void setUp() {
        underTest = new HttpGetRequester();
    }

    @Test
    void shouldRequestWithoutParameter(WireMockRuntimeInfo wireMockRuntimeInfo) {

        // GIVEN
        stubFor(WireMock.get("/values").willReturn(ok("""
                {
                    "value1": 1,
                    "value2": 2,
                    "value3": 3
                }""")));

        GetRequestParameter input = GetRequestParameter.builder()
                .baseUri(String.format("http://localhost:%d/values", wireMockRuntimeInfo.getHttpPort()))
                .build();

        // WHEN
        Map<String, Object> result = underTest.request(input);

        // THEN
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
                "value1", 1.0,
                "value2", 2.0,
                "value3", 3.0
        ));
    }

    @Test
    void shouldRequestWithOneParameter(WireMockRuntimeInfo wireMockRuntimeInfo) {

        // GIVEN
        stubFor(WireMock.get("/value?id=1&").willReturn(ok("""
                {
                    "value1": 1
                }""")));

        GetRequestParameter input = GetRequestParameter.builder()
                .baseUri(String.format("http://localhost:%d/value", wireMockRuntimeInfo.getHttpPort()))
                .parameter(new GetParameter("id", 1))
                .build();

        // WHEN
        Map<String, Object> result = underTest.request(input);

        // THEN
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
                "value1", 1.0
        ));
    }

    @Test
    void shouldRequestWithTwoParameter(WireMockRuntimeInfo wireMockRuntimeInfo) {

        // GIVEN
        stubFor(WireMock.get("/value?id=1&id=2&").willReturn(ok("""
                {
                    "value1": 1,
                    "value2": 2
                }""")));

        GetRequestParameter input = GetRequestParameter.builder()
                .baseUri(String.format("http://localhost:%d/value", wireMockRuntimeInfo.getHttpPort()))
                .parameter(new GetParameter("id", 1))
                .parameter(new GetParameter("id", 2))
                .build();

        // WHEN
        Map<String, Object> result = underTest.request(input);

        // THEN
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
                "value1", 1.0,
                "value2", 2.0
        ));
    }
}