package com.guitarshack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
@ConfigurationProperties(prefix = "base.url")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UriConfig {
    @NonNull
    private String produkt;
    @NonNull
    private String sales;
}
