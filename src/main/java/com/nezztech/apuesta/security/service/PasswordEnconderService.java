/**
 * 
 */
package com.nezztech.apuesta.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
public class PasswordEnconderService implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.toString().equals(s);
    }

}
