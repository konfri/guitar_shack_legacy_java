package com.guitarshack.repository;

import com.guitarshack.HttpGetRequester;
import com.guitarshack.UriConfig;
import com.guitarshack.converter.MapToSalesTotalConverter;
import com.guitarshack.domain.GetParameter;
import com.guitarshack.domain.GetRequestParameter;
import com.guitarshack.domain.SalesPeriod;
import com.guitarshack.domain.SalesTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SalesRepository {

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    private final MapToSalesTotalConverter mapToSalesTotalConverter;
    private final UriConfig uriConfig;
    private final HttpGetRequester httpGetRequester;

    public SalesTotal read(SalesPeriod salesPeriod) {

        GetRequestParameter getRequestParameter = GetRequestParameter.builder()
                .baseUri(uriConfig.getSales())
                .parameter(new GetParameter("productId", salesPeriod.getProductId().getValue()))
                .parameter(new GetParameter("startDate", dateTimeFormatter.format(salesPeriod.getStartDate())))
                .parameter(new GetParameter("endDate", dateTimeFormatter.format(salesPeriod.getEndDate())))
                .parameter(new GetParameter("action", "total"))
                .build();

        Map<String, Object> map = httpGetRequester.request(getRequestParameter);
        return mapToSalesTotalConverter.convert(map);
    }
}
