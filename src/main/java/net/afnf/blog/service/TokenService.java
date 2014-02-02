package net.afnf.blog.service;

import net.afnf.blog.common.Crypto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenService {

    private static Logger logger = LoggerFactory.getLogger(TokenService.class);

    public static final int VALID_MS = 20 * 1000;

    public static String getToken() {
        String token = "" + (System.currentTimeMillis() + VALID_MS);
        String encrypted = Crypto.encrypt(token);
        return encrypted;
    }

    public static boolean validateToken(String encryptedToken) {

        String token = Crypto.decrypt(encryptedToken);
        if (token == null) {
            return false;
        }

        if (StringUtils.isNumeric(token) == false) {
            logger.warn("token is not numeric");
            return false;
        }

        long until = NumberUtils.toLong(token, -1);
        long now = System.currentTimeMillis();
        if (until - VALID_MS <= now && now <= until) {
            return true;
        }
        else {
            logger.warn("token is expired");
            return false;
        }
    }
}
