package com.guitarshack.repository;

import com.guitarshack.HttpGetRequester;
import com.guitarshack.UriConfig;
import com.guitarshack.converter.MapToProductConverter;
import com.guitarshack.domain.GetParameter;
import com.guitarshack.domain.GetRequestParameter;
import com.guitarshack.domain.Product;
import com.guitarshack.domain.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductRepository {

    private final MapToProductConverter mapToProductConverter;
    private final UriConfig uriConfig;
    private final HttpGetRequester httpGetRequester;

    public Product read(ProductId productId) {

        GetRequestParameter getRequestParameter = GetRequestParameter.builder()
                .baseUri(uriConfig.getProdukt())
                .parameter(new GetParameter("id", productId.getValue()))
                .build();
        Map<String, Object> produktAsMap = httpGetRequester.request(getRequestParameter);
        return mapToProductConverter.convert(produktAsMap);
    }
}
