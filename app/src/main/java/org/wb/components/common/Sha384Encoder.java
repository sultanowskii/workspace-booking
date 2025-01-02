package org.wb.components.common;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public class Sha384Encoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence raw) {
        byte[] bytes = raw.toString().getBytes(StandardCharsets.UTF_8);

        try {
            var md = MessageDigest.getInstance("SHA-384");
            md.update(bytes);
            return new String(Base64.getEncoder().encode(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-384 algorithm not found in MessageDigest", e);
        }
    }

    @Override
    public boolean matches(CharSequence raw, String encoded) {
        var rawEncoded = encode(raw);
        return Objects.equals(rawEncoded, encoded);
    }
}