package com.tien.identity.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/test.properties")
class ApplicationTests {

    @Test
    void hash() throws NoSuchAlgorithmException {
        String password = "123456";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte[] digest = md.digest();
        String mdHash = DatatypeConverter.printHexBinary(digest);

        System.out.println("MD5 round 1: " + mdHash);

        md.update(password.getBytes());
        digest = md.digest();
        mdHash = DatatypeConverter.printHexBinary(digest);
        System.out.println("MD5 round 2: " + mdHash);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        System.out.println("Bcrypt round 1: " + passwordEncoder.encode(password));
        System.out.println("Bcrypt round 2: " + passwordEncoder.encode(password));
    }
}
