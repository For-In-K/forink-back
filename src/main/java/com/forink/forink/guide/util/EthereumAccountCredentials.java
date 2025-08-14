package com.forink.forink.guide.util;

import lombok.Builder;

@Builder
public record EthereumAccountCredentials(

        String address,

        String privateKey
) {
}
