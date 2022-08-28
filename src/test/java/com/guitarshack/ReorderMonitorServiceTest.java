package com.guitarshack;

import com.guitarshack.calculator.ProductReorderCalculator;
import com.guitarshack.calculator.SalesPeriodCalculator;
import com.guitarshack.domain.*;
import com.guitarshack.repository.ProductRepository;
import com.guitarshack.repository.SalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReorderMonitorServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private SalesRepository salesRepository;
    @Mock
    private SalesPeriodCalculator salesPeriodCalculator;
    @Mock
    private ProductReorderCalculator productReorderCalculator;
    @Mock
    private ProductReorderNotifier productReorderNotifier;

    private ReorderMonitorService underTest;

    @Mock
    private ProductId productId;
    @Mock
    private Quantity quantity;
    @Mock
    private Product product;
    @Mock
    private SalesPeriod salesPeriod;
    @Mock
    private SalesTotal salesTotal;

    @BeforeEach
    void setUp() {
        underTest = new ReorderMonitorService(productRepository, salesRepository, salesPeriodCalculator, productReorderCalculator, productReorderNotifier);

        given(productRepository.read(productId)).willReturn(product);
        given(salesPeriodCalculator.calculateSalesInput(productId)).willReturn(salesPeriod);
        given(salesRepository.read(salesPeriod)).willReturn(salesTotal);
    }

    @Test
    void shouldAlert() {

        // GIVEN
        boolean shouldAlert = true;
        given(productReorderCalculator.shouldReorder(product, quantity, salesTotal)).willReturn(shouldAlert);

        // WHEN
        underTest.sold(productId, quantity);

        // THEN
        verify(productReorderNotifier).notify(product);
    }

    @Test
    void shouldSkipAlert() {

        // GIVEN
        boolean shouldAlert = false;
        given(productReorderCalculator.shouldReorder(product, quantity, salesTotal)).willReturn(shouldAlert);

        // WHEN
        underTest.sold(productId, quantity);

        // THEN
        verify(productReorderNotifier, times(0)).notify(any());
    }
}