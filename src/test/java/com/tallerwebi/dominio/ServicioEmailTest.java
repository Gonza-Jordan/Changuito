//package com.tallerwebi.dominio;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class ServicioEmailTest {
//
//    @Mock
//    private JavaMailSender mailSender;
//
//    @Mock
//    private TemplateEngine templateEngine;
//
//    @InjectMocks
//    private ServicioEmail servicioEmail;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void queSePuedaProbarElServicioDeEnvioConImagenes() throws MessagingException, IOException {
//        // Datos de prueba
//        String to = "ejemplo@example.com";
//        String subject = "Correo de prueba";
//        String templateName = "emailTemplate";
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("nombre", "Usuario");
//
//        MimeMessage mimeMessage = mock(MimeMessage.class);
//        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("<html><body>Contenido de prueba</body></html>");
//
//        servicioEmail.sendMimeMessageWithImage(to, subject, templateName, variables);
//
//        verify(mailSender, times(1)).send(any(MimeMessage.class));
//        verify(templateEngine, times(1)).process(eq(templateName), any(Context.class));
//    }
//
//    @Test
//    public void queElServicioPuedaVerificarElContenidoDelEmail() throws MessagingException, IOException {
//        // Datos de prueba
//        String to = "ejemplo@example.com";
//        String subject = "Correo de prueba";
//        String templateName = "emailTemplate";
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("nombre", "Usuario");
//
//        MimeMessage mimeMessage = mock(MimeMessage.class);
//        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//        String expectedHtmlContent = "<html><body>Contenido de prueba</body></html>";
//        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn(expectedHtmlContent);
//
//        servicioEmail.sendMimeMessageWithImage(to, subject, templateName, variables);
//
//        verify(mailSender, times(1)).send(any(MimeMessage.class));
//
//        verify(templateEngine, times(1)).process(eq(templateName), any(Context.class));
//
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//        helper.setText(expectedHtmlContent, true);
//    }
//}
