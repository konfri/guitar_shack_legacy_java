package com.guitarshack.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Product {

    @NonNull
    ProductId productId;
    @NonNull
    Stock stock;
    @NonNull
    LeadTime leadTime;
}
