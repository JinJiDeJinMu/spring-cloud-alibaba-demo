package com.hjb.jwt;

import com.alibaba.fastjson.JSONObject;
import com.hjb.constant.CommonConstants;
import com.hjb.execption.auth.UserJwtException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.*;

public class JwtUtils {

    /**
     * 创建秘钥
     */
    private static final byte[] SECRET = "6MNSobBRCHGIO0fS6MNSobBRCHGIO0fS".getBytes();

    /**
     * 过期时间24秒
     */
    private static final long EXPIRE_TIME = 24 * 1000 * 60 * 60;


    /**
     * 生成Token
     * @param userId
     * @param userName
     * @return
     */
    public static String createdToken(Long userId, String userName) {
        String token = null;
        try {
            MACSigner macSigner = new MACSigner(SECRET);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("admin")
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .claim("userId",userId)
                    .claim("userName",userName)
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(macSigner);

            token = signedJWT.serialize();
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static String vaildToken(String token) {
        String result = null;
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);
            //校验是否有效
            if (!jwt.verify(verifier)) {
                throw new UserJwtException("Token 无效", CommonConstants.EX_USER_JWT_IS_VERIFIER);
            }
            //校验超时
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)) {
                throw new UserJwtException("Token 已过期", CommonConstants.EX_USER_JWT_EXPIRATION_TIME);
            }
            //获取载体中的数据
            result = JSONObject.toJSONString(jwt.getJWTClaimsSet().getClaims());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return result;
    }

}
