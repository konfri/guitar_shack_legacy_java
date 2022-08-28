package com.guitarshack;

import com.guitarshack.calculator.ProductReorderCalculator;
import com.guitarshack.calculator.SalesPeriodCalculator;
import com.guitarshack.domain.*;
import com.guitarshack.repository.ProductRepository;
import com.guitarshack.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReorderMonitorService {

    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;
    private final SalesPeriodCalculator salesPeriodCalculator;
    private final ProductReorderCalculator productReorderCalculator;
    private final ProductReorderNotifier productReorderNotifier;

    public void sold(ProductId productId, Quantity quantity) {

        SalesPeriod salesPeriod = salesPeriodCalculator.calculateSalesInput(productId);
        SalesTotal salesTotal = salesRepository.read(salesPeriod);

        Product product = productRepository.read(productId);
        if (productReorderCalculator.shouldReorder(product, quantity, salesTotal)) {
            productReorderNotifier.notify(product);
        }
    }
}