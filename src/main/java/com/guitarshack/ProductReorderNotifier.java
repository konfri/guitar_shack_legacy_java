package com.guitarshack;

import com.guitarshack.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductReorderNotifier {

    void notify(Product product) {

        // We are faking this for now
        System.out.println(
                "You need to reorder product " + product.getProductId().getValue() +
                        ". Only " + product.getStock() + " remaining in stock");
    }
}
