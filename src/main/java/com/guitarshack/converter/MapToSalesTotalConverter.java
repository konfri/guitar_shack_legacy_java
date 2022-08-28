package com.guitarshack.converter;

import com.guitarshack.domain.SalesTotal;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapToSalesTotalConverter {

    public SalesTotal convert(Map<String, Object> map) {

        // NOTE: gson is a simple parse and uses always double as default
        Integer productId = (int) (double) map.get("total");

        return new SalesTotal(productId);
    }
}
