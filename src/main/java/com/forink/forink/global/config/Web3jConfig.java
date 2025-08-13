package com.forink.forink.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.guideverification.GuideVerification;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class Web3jConfig {

  @Value("${blockchain.rpc-url}")
  private String rpcUrl;

  @Value("${blockchain.private-key}")
  private String privateKey;

  @Value("${blockchain.contract-address}")
  private String contractAddress;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(rpcUrl));
  }

  @Bean
  public Credentials credentials() {
    return Credentials.create(privateKey);
  }

  // @Bean
  // public GuideVerification guideVerificationContract(Web3j web3j, Credentials credentials) {
  //   TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, 31337L);

  //   return GuideVerification.load(
  //       contractAddress,
  //       web3j,
  //       transactionManager,
  //       new DefaultGasProvider()
  //   );
  // }
}
