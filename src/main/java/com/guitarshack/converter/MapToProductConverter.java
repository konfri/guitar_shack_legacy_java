package com.guitarshack.converter;

import com.guitarshack.domain.LeadTime;
import com.guitarshack.domain.Product;
import com.guitarshack.domain.ProductId;
import com.guitarshack.domain.Stock;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapToProductConverter {

    public Product convert(Map<String, Object> productAsMap) {

        // NOTE: gson is a simple parse and uses always double as default
        Integer productId = (int) (double) productAsMap.get("id");
        Integer stock = (int) (double) productAsMap.get("stock");
        Integer leadTime = (int) (double) productAsMap.get("leadTime");

        return Product.builder()
                .productId(new ProductId(productId))
                .stock(new Stock(stock))
                .leadTime(new LeadTime(leadTime))
                .build();
    }
}
