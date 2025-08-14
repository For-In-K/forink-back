package com.forink.forink.guide.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

@Component
public class BlockchainKeyHelper {

    @Value("${blockchain.encryption-key}")
    private String encryptionKey;

    @Value("${blockchain.encryption-algorithm}")
    private String encryptionAlgorithm;

    public EthereumAccountCredentials generateCredentials() throws Exception {
        ECKeyPair keyPair = Keys.createEcKeyPair();

        String privateKeyHex = keyPair.getPrivateKey().toString(16);
        String address = "0x" + Keys.getAddress(keyPair.getPublicKey());
        String encryptedPrivateKey = encrypt(privateKeyHex);

        return EthereumAccountCredentials.builder()
                .address(address)
                .privateKey(encryptedPrivateKey)
                .build();
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), encryptionAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), encryptionAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

}
