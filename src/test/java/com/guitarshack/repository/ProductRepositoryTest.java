package com.guitarshack.repository;

import com.guitarshack.HttpGetRequester;
import com.guitarshack.UriConfig;
import com.guitarshack.converter.MapToProductConverter;
import com.guitarshack.domain.GetParameter;
import com.guitarshack.domain.GetRequestParameter;
import com.guitarshack.domain.Product;
import com.guitarshack.domain.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private MapToProductConverter mapToProductConverter;
    @Mock
    private UriConfig uriConfig;
    @Mock
    private HttpGetRequester httpGetRequester;

    private ProductRepository underTest;

    @Mock
    private Map<String, Object> map;
    @Mock
    private Product product;

    @BeforeEach
    void setUp() {
        underTest = new ProductRepository(mapToProductConverter, uriConfig, httpGetRequester);
    }

    @Test
    void shouldRead() {

        // GIVEN
        given(uriConfig.getProdukt()).willReturn("produktUri");
        ProductId input = new ProductId(1);

        GetRequestParameter getRequestParameter = GetRequestParameter.builder()
                .baseUri("produktUri")
                .parameter(new GetParameter("id", 1))
                .build();

        given(httpGetRequester.request(getRequestParameter)).willReturn(map);
        given(mapToProductConverter.convert(map)).willReturn(product);

        // WHEN
        Product result = underTest.read(input);

        // THEN
        assertThat(result).isEqualTo(product);
    }
}