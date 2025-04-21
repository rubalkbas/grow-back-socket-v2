package com.nezztech.apuesta.notificacion.service;

import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.nezztech.apuesta.security.entity.UsuarioInterno;
import com.nezztech.apuesta.security.repositoy.UsuarioInternoRepository;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@Service
public class NotificacionCorreoService {
	
	@Autowired
    private JavaMailSender correo; 
	
	@Autowired
	UsuarioInternoRepository usuarioRepository;
	
	private final ResourceLoader resourceLoader;
	
	public NotificacionCorreoService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

	public void envioCorreo( int idUsuario , String cuerpo, String info) throws MessagingException, IOException {
		
		log.info("-");
        log.info("INICIO DE ENVIO DE CORREO !!!");
		
		Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(idUsuario);
		UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
		
		String correoEnvio = usuarioEnvio.getCorreo();
		//String ticketFormateo = String.format("%0" + 6 + "d", ticket);
		
		String asunto = "GROWING CAPITAL MAKER - Notificación del cliente : " + usuarioEnvio.getAlias();

        MimeMessage message = correo.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
             
        // URL de la imagen en línea
        URL url = getClass().getClassLoader().getResource("img/LOGO CORREO.jpg");

        // Leer la imagen desde la URL
        BufferedImage image = ImageIO.read(url);

        // Convertir la imagen a un array de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);        
      
        String html2 = "<!DOCTYPE html>\r\n"
    	  		+ "<html lang=\"en\">\r\n"
    	  		+ "\r\n"
    	  		+ "<head>\r\n"
    	  		+ "    <meta charset=\"UTF-8\">\r\n"
    	  		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
    	  		+ "    <title> GROWING CAPITAL MAKER </title>\r\n"
    	  		+ "</head>\r\n"
    	  		+ "<body>\r\n"
    	  		+ "    <h3 style=\"color: #333;\">Sistema de notificaciones GROWING CAPITAL MAKER</h3>\r\n"
    	  		+ "    <p><strong>\r\n"
    	  		+ "            Buen día,\r\n"
    	  		+ "        </strong></p>\r\n"
    	  		+ "    <p><br></p>\r\n"
    	  		+ "    <p><strong>\r\n"
    	  		+ "            Te informamos que tienes una nueva notificación :  \""+ cuerpo +" \", favor de dar el seguimiento correspondiente.\r\n"
    	  		+ "        </strong></p>\r\n"
    	  		+ "    <p><br></p>\r\n"
    	  		+ "    <p><strong>\r\n"
    	  		+ "            Información adicional : \""+ info +"\".\r\n"
    	  		+ "        </strong></p>\r\n"
    	  		+ "    <p><br></p>\r\n"
    	  		+ "    <p><strong>\r\n"
    	  		+ "            Saludos Cordiales.\r\n"
    	  		+ "        </strong></p>\r\n"
    	  		+ "    <p><br></p>\r\n"
    	  		+ "    </div>\r\n"
    	  		+ "</body><footer>\r\n"
    	  		+ "    <p>\r\n"
    	  		+ "        <img src='cid:signature'  \r\n"
    	  		+ "            alt=\"Logo GROWING CAPITAL MAKER\" width=\"500\" height=\"120\">\r\n"
    	  		+ "\r\n"
    	  		+ "    </p>\r\n"
    	  		+ "</footer>\r\n"
    	  		+ "\r\n"
    	  		+ "</html>";
    
        try {
        	
            helper.setTo(correoEnvio);
            helper.setSubject(asunto);
            helper.setText( html2, true);
            helper.setFrom("lyan.0702.nunez@gmail.com");
            // para archvio adjunto
            // helper.addInline("image", new File(rutaImagen));
            
            // agrega firma del correo
            //Path signaturePath = Paths.get(ruta);
            //byte[] signatureBytes = Files.readAllBytes(signaturePath);            
            byte[] signatureBytes = byteArrayOutputStream.toByteArray();
            
            DataSource dataSource = new ByteArrayDataSource(signatureBytes, "image/jpeg");            
            helper.addInline("signature", dataSource );          
                        
            correo.send(message);
            
            log.info("-");
            log.info("EL CORREO FUE ENVIADO EXITOSAMENTE !!!");
            log.info("-");
            
        } catch (MessagingException e) {
        	
            e.printStackTrace();
            log.info("ERROR AL ENVIAR EL CORREO : " + e.getMessage());
            
        }
	}
        
    	public void envioPassNueva(String pass, int idUsuario , String infoAccion) throws MessagingException, IOException {
    		
    		log.info("-");
            log.info("INICIO DE ENVIO DE CORREO !!!");
    		
    		Optional<UsuarioInterno> usuarioRecuperado = usuarioRepository.findById(idUsuario);
    		UsuarioInterno usuarioEnvio = usuarioRecuperado.get();
    		
    		String correoEnvio = usuarioEnvio.getCorreo();
    		String Password = pass;
    		
    		String asunto = "GROWING CAPITAL MAKER - Notificación Cambio de contraseña";

            MimeMessage message = correo.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            

            URL url = getClass().getClassLoader().getResource("img/LOGO CORREO.jpg");

            BufferedImage image = ImageIO.read(url);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);        
          
            String html2 = "<!DOCTYPE html>\r\n"
        	  		+ "<html lang=\"en\">\r\n"
        	  		+ "\r\n"
        	  		+ "<head>\r\n"
        	  		+ "    <meta charset=\"UTF-8\">\r\n"
        	  		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
        	  		+ "    <title>GROWING CAPITAL MAKER</title>\r\n"
        	  		+ "</head>\r\n"
        	  		+ "<body>\r\n"
        	  		+ "    <h3 style=\"color: #333;\">Sistema de notificaciones GROWING CAPITAL MAKER</h3>\r\n"
        	  		+ "    <p><strong>\r\n"
        	  		+ "            Buen día,\r\n"
        	  		+ "        </strong></p>\r\n"
        	  		+ "    <p><br></p>\r\n"
        	  		+ "    <p><strong>\r\n"
        	  		+ "            Te informamos que esta es contraseña temporal: <br><br>"+"<h3 style=\\\"\\\">"+ Password +"</h3>"
        	  		+ "        </strong></p>\r\n"
        	  		+ "    <p><br></p>\r\n"
        	  		+ "    <p><strong>\r\n"
        	  		+ "            Información adicional : \""+ infoAccion +"\".\r\n"
        	  		+ "        </strong></p>\r\n"
        	  		+ "    <p><br></p>\r\n"
        	  		+ "    <p><strong>\r\n"
        	  		+ "            Saludos Cordiales.\r\n"
        	  		+ "        </strong></p>\r\n"
        	  		+ "    <p><br></p>\r\n"
        	  		+ "    </div>\r\n"
        	  		+ "</body><footer>\r\n"
        	  		+ "    <p>\r\n"
        	  		+ "        <img src='cid:signature'  \r\n"
        	  		+ "            alt=\"Logo GROWING CAPITAL MAKER\" width=\"500\" height=\"120\">\r\n"
        	  		+ "\r\n"
        	  		+ "    </p>\r\n"
        	  		+ "</footer>\r\n"
        	  		+ "\r\n"
        	  		+ "</html>";
        
            try {
            	
                helper.setTo(correoEnvio);
                helper.setSubject(asunto);
                helper.setText( html2, true);
                helper.setFrom("lyan.0702.nunez@gmail.com");
           
                byte[] signatureBytes = byteArrayOutputStream.toByteArray();
                
                DataSource dataSource = new ByteArrayDataSource(signatureBytes, "image/jpeg");            
                helper.addInline("signature", dataSource );          
                            
                correo.send(message);
                
                log.info("-");
                log.info("EL CORREO FUE ENVIADO EXITOSAMENTE !!!");
                log.info("-");
                
            } catch (MessagingException e) {
            	
                e.printStackTrace();
                log.info("ERROR AL ENVIAR EL CORREO : " + e.getMessage());
                
            }
        
    }

}
