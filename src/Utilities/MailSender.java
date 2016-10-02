package Utilities;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * @author Bishops
 * Envia mail de texto plano / HTML / ArchivoAdjunto
 */
public class MailSender 
{
    public enum TypeMessage
    {
        PLANE_TEXT,
        HTML_TEXT
    }
    
    private String ToMail;          // La dirección de envío (to)    
    private String FromMail;        // La dirección de la cuenta de envío (from)
    private String ServerHost;      // El servidor (host). En este caso usamos localhost
    private Session sesion;         // objeto de clase para sesion
    private MimeMessage mensaje;    // objeto de clase para mensajes
    private Properties propiedades; // objeto de propiedades

    public MailSender() {}
    
    public MailSender(String destinatary, String emisor, String hostserver )
    {
        ToMail= destinatary;     
        FromMail= emisor; 
        ServerHost= hostserver;
        
        // Obtenemos las propiedades del sistema
        propiedades = System.getProperties();
        // Configuramos el servidor de correo
        propiedades.setProperty("mail.smtp.host", ServerHost);
        // Obtenemos la sesión por defecto
        sesion = Session.getDefaultInstance(propiedades); 
        mensaje = new MimeMessage(sesion); // Creamos un objeto mensaje tipo MimeMessage por defecto.
    }
    
    public MailSender(String destinatary, String emisor, String hostserver,String MailUser, String MailPass) 
    {
        this(destinatary,emisor,hostserver);
        propiedades.setProperty("mail.user", MailUser); 
        propiedades.setProperty("mail.password", MailPass);
    }
    
    // Asigna la direccion del destinatario y del emisor
    private void AsignarAddress()
    {
        try
        {
            mensaje.setFrom(new InternetAddress(FromMail)); // Asignamos el “de o from” al header del correo.
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(ToMail)); // Asignamos el “para o to” al header del correo.
        }catch (MessagingException e) 
        { 
          Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
        }
    }

    public boolean SenderMailText(String message, String subject, TypeMessage text)
    {
        try{
         
          AsignarAddress();
          
          mensaje.setSubject(subject); // Asignamos el asunto
          
          switch(text)
          {
              case PLANE_TEXT:
                  mensaje.setText(message);    // Asignamos el mensaje como tal
                  break;
                  
              case HTML_TEXT:
                  mensaje.setContent(message,"text/html");    // Asignamos el mensaje como tal
                  break;
          }
          
          
          // Enviamos el correo
          Transport.send(mensaje);
          System.out.println("Mensaje enviado");
          
        } catch (MessagingException e) 
        { 
          Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
          return false;
        } 
        return true;
    }
       
    public boolean SenderMailAddFileText(String message, String subject, String pathfile, TypeMessage text)
    {
        try{
         
          AsignarAddress();
          
          mensaje.setSubject(subject); // Asignamos el asunto
              
          BodyPart cuerpoMensaje = new MimeBodyPart(); // Creamos un cuerpo del correo con ayuda de la clase BodyPart

          switch(text)
          {
              case PLANE_TEXT:
                  cuerpoMensaje.setText(message);    // Asignamos el mensaje como tal
                  break;
                  
              case HTML_TEXT:
                  cuerpoMensaje.setContent(message,"text/html");    // Asignamos el mensaje como tal
                  break;
          }           
             
          Multipart multiparte = new MimeMultipart(); // Creamos un multipart al correo

          // Agregamos el texto al cuerpo del correo multiparte
          multiparte.addBodyPart(cuerpoMensaje); 

            // Ahora el proceso para adjuntar el archivo
            cuerpoMensaje = new MimeBodyPart();
            String nombreArchivo = pathfile; //"consulta.pdf";
            DataSource fuente = new FileDataSource(nombreArchivo);
            cuerpoMensaje.setDataHandler(new DataHandler(fuente));
            cuerpoMensaje.setFileName(nombreArchivo);
            multiparte.addBodyPart(cuerpoMensaje);

            // Asignamos al mensaje todas las partes que creamos anteriormente
            mensaje.setContent(multiparte);         
          
          // Enviamos el correo
          Transport.send(mensaje);
          System.out.println("Mensaje enviado");
          
        } catch (MessagingException e) 
        { 
          Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
          return false;
        }     
        return true;
    }
    
}
