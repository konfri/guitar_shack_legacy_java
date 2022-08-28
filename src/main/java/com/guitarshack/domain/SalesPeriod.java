package com.guitarshack.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class SalesPeriod {

    @NonNull
    ProductId productId;
    @NonNull
    LocalDate startDate;
    @NonNull
    LocalDate endDate;
}
