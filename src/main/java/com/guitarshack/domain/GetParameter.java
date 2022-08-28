package com.guitarshack.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class GetParameter {

    @NonNull
    String key;
    @NonNull
    Object value;
}
