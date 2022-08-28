package com.guitarshack.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class Quantity {

    @NonNull
    Integer value;
}
