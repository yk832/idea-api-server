package com.ideate.ideaapiserver.util;

import com.ideate.ideaapiserver.config.ErrorCode;
import com.ideate.ideaapiserver.handler.GlobalException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException exception) {
            throw new GlobalException(exception, ErrorCode.NOT_FOUND);
        }

    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }


}
