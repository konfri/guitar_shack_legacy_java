package com.guitarshack.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class GetRequestParameter {

    @NonNull
    String baseUri;
    @Singular
    @NonNull List<GetParameter> parameters;
}
