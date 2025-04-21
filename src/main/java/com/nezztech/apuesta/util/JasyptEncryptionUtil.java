package com.nezztech.apuesta.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Component
public class JasyptEncryptionUtil {

    private final StandardPBEStringEncryptor encryptor;

	
    public JasyptEncryptionUtil() {
        this.encryptor = new StandardPBEStringEncryptor();
        this.encryptor.setPassword("e#4D!7gH@1Jk*9Lb5fNg8rSa2oT+"); 
        
        // Especifica el algoritmo de encriptación
        this.encryptor.setAlgorithm("PBEWithMD5AndDES"); 
    }

    public String encriptar(String contrasena) {
        return encryptor.encrypt(contrasena);
    }

    public String desencriptar(String contrasena) {
        return encryptor.decrypt(contrasena);
    }

    
    /**
     * Metodo encargado de comparar contraseñas
     * @param contrasena
     * @param encriptada
     * @return True si es igual la contraseña desencriptada a la encriptada
     */
    public boolean matches(String contrasena, String encriptada) {
        return contrasena.equals(desencriptar(encriptada));
    }

}
