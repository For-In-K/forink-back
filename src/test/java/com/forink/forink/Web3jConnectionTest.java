//package com.forink.forink;
//
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.web3j.protocol.Web3j;
//
//@SpringBootTest
//public class Web3jConnectionTest {
//
//  @Autowired
//  private Web3j web3j;
//
//  @Test
//  public void shouldConnectToAnvil() throws Exception {
//    var blockNumber = web3j.ethBlockNumber().send().getBlockNumber();
//    System.out.println("Current block: " + blockNumber);
//    assertNotNull(blockNumber);
//  }
//}
