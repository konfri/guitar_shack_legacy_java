package com.guitarshack.calculator;

import com.guitarshack.domain.Product;
import com.guitarshack.domain.Quantity;
import com.guitarshack.domain.SalesTotal;
import org.springframework.stereotype.Component;

@Component
public class ProductReorderCalculator {

    public boolean shouldReorder(Product product, Quantity quantity, SalesTotal total) {
        return product.getStock().getValue() - quantity.getValue() <= (int) ((double) (total.getValue() / 30) * product.getLeadTime().getValue());
    }
}
