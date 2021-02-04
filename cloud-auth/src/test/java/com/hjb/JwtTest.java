package com.hjb;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.impl.crypto.RsaSigner;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testCreateToken(){

    }

    @Test
    public void testParseToken(){
        //令牌
String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhbGwiXSwiaWQiOjEsImV4cCI6MTYxMjQ3MDA0NSwiYXV0aG9yaXRpZXMiOlsic2VydmljZS1nb29kIiwic2VydmljZS1vcmRlciIsInNlcnZpY2UtdXNlciIsInNlcnZpY2UtY2FydCIsIlN5c3RlbSIsInRlc3RVc2VyVXBkYXRlIl0sImp0aSI6ImMyMDgwYWNhLTViZDEtNDQ1Yy05MmYyLTcxOTNlMTlkMWU2OSIsImNsaWVudF9pZCI6ImNsaWVudCIsInVzZXJuYW1lIjoiYWRtaW4ifQ.eCx1qvSt_Aa_ta8tt8uPWkcAhNg8xDDS3MMKj-IIuIk7cXHj1fLgB3psGFgMbhBA-3wngQuQTrW5dJGmNz-SzSnzv2YlOznUgDtnj_EPEfSFHFJ9qnucGHdcGNSELfLUk8aKHLRKZx4IQBF1LEMFdmuk_ACK4hiCOoaMxPSdpEVN2KBSoGPEXv-9g-D8efYPOooM-5ktwJEqoKc74Kx8E3roygdh6QUtcGQO4Yf6a2fwpRTXOMYY3Jlg4Ab5RccH3q5vszV--zQhNTKXqP7W_TMSLYUhDRCNyh01TQ4k_TEJfPVYNCIdGmku698BWepkap5cDB7WJUxz2yfXptrPgg";        //公钥
String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjvR/5+Wf4h2o8VieSeMH3zVvExlVaHfijXZZ/SQhykyt7RPdUVGg+QsmxAvFBimsegqee0efkjsfjiejDYGxdz9M9YCVvUjQJvcAlS/0xiq5CLN8Z4DKm9A6UxJhWSBOI8qzJ95LSzPqQQ8Mw8TJ9Q8RPKCWpZNGLxw0UMFKA1xqZG9aenJZQhQ581ONNkyG+i9rZz0vQT9pFMs5bKC+FKAYqj7R4M13gTOW3Y/hq8ibbxKPlwvuOLCArA3Ak3umBDKWkGsvoYnYyE+VnCHknVwmTHo9yS9JVsyD1KTgPxBYhfK6E7vUZCf/XAutMIEtexylfI208IvshrFDmPNWdwIDAQAB-----END PUBLIC KEY-----";        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
