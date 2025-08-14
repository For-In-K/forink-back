package com.forink.forink.guide.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.guideverification.GuideVerification;
import org.web3j.protocol.Web3j;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

@Component
@RequiredArgsConstructor
public class GuideVerificationManager {

    private final Web3j web3j;

    @Value("${blockchain.contract-address}")
    private String contractAddress;

    @Value("${blockchain.chain-id}")
    private Long chainId;

    public GuideVerification loadGuideVerification(String privateKey) {
        Credentials credentials = Credentials.create(privateKey);
        TransactionManager txManager = new RawTransactionManager(web3j, credentials, chainId);
        return GuideVerification.load(
                contractAddress,
                web3j,
                txManager,
                new DefaultGasProvider()
        );
    }

}
