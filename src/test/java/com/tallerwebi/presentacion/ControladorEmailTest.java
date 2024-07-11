//package com.tallerwebi.presentacion;
//
//import com.tallerwebi.dominio.ServicioEmail;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import javax.mail.MessagingException;
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class ControladorEmailTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private ServicioEmail servicioEmail;
//
//    @InjectMocks
//    private ControladorEmail controladorEmail;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(controladorEmail).build();
//    }
//
//    @Test
//    public void queSeEnvieCorrectoElMail() throws Exception {
//        doNothing().when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email").param("to", "test@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Email enviado exitosamente!"));
//
//        verify(servicioEmail, times(1)).sendMimeMessageWithImage(eq("test@example.com"), eq("Tu changuito"), eq("emailTemplate"), anyMap());
//    }
//
//    @Test
//    public void queNoSeEnvioCorrectoElMail() throws Exception {
//        doThrow(new MessagingException("Error al enviar email")).when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email").param("to", "test@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Error enviando email: Error al enviar email"));
//
//        verify(servicioEmail, times(1)).sendMimeMessageWithImage(eq("test@example.com"), eq("Tu changuito"), eq("emailTemplate"), anyMap());
//    }
//
//    @Test
//    public void queSePruebeElEnvioSinDestinatario() throws Exception {
//        mockMvc.perform(get("/send-email"))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void queSePuedaControlarElEnvioDeMailConExcepcion() throws Exception {
//        doThrow(new IOException("Error de IO")).when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email").param("to", "test@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Error enviando email: Error de IO"));
//
//        verify(servicioEmail, times(1)).sendMimeMessageWithImage(eq("test@example.com"), eq("Tu changuito"), eq("emailTemplate"), anyMap());
//    }
//
//    @Test
//    public void queElTiempoDeRespuestaPermitaEnviarMail() throws Exception {
//        doNothing().when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        long startTime = System.currentTimeMillis();
//        mockMvc.perform(get("/send-email").param("to", "test@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Email enviado exitosamente!"));
//        long endTime = System.currentTimeMillis();
//
//        assertTrue((endTime - startTime) < 1000);
//    }
//
//    @Test
//    public void cuandoSeRecibeUnErrorAlEnviar() throws Exception {
//        doThrow(new IOException("Error de I/O al enviar email")).when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email").param("to", "test@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Error enviando email: Error de I/O al enviar email"));
//
//        verify(servicioEmail, times(1)).sendMimeMessageWithImage(eq("test@example.com"), eq("Tu changuito"), eq("emailTemplate"), anyMap());
//    }
//    @Test
//    public void queSePuedaPorbarElServicioDeMail() throws Exception {
//        doThrow(new MessagingException("Servicio no disponible")).when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email").param("to", "test@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Error enviando email: Servicio no disponible"));
//
//        verify(servicioEmail, times(1)).sendMimeMessageWithImage(eq("test@example.com"), eq("Tu changuito"), eq("emailTemplate"), anyMap());
//    }
//
//    @Test
//    public void queElServicioRespondaYDevuelvaRespuesta() throws Exception {
//        doNothing().when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email").param("to", "test@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Email enviado exitosamente!"));
//
//        verify(servicioEmail, times(1)).sendMimeMessageWithImage(eq("test@example.com"), eq("Tu changuito"), eq("emailTemplate"), anyMap());
//    }
//    @Test
//    public void queNoSeaNuloElReceptorDelMail() throws Exception {
//        doNothing().when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email").param("to", (String) null))
//                .andExpect(status().isBadRequest());
//
//        verify(servicioEmail, times(0)).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//    }
//
//    @Test
//    public void queSePuedaVerificarElTiempoDeEnvio() throws Exception {
//        doNothing().when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        long startTime = System.currentTimeMillis();
//        mockMvc.perform(get("/send-email").param("to", "test@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Email enviado exitosamente!"));
//        long endTime = System.currentTimeMillis();
//
//        assertTrue((endTime - startTime) < 2000);
//    }
//    @Test
//    public void queSePuedaVerificarLosParametrosOpcionales() throws Exception {
//        doNothing().when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email")
//                        .param("to", "test@example.com")
//                        .param("cc", "cc@example.com")
//                        .param("bcc", "bcc@example.com"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Email enviado exitosamente!"));
//
//        verify(servicioEmail, times(1)).sendMimeMessageWithImage(eq("test@example.com"), eq("Tu changuito"), eq("emailTemplate"), anyMap());
//    }
//    @Test
//    public void queSePuedaVerificarLosAdjuntosEnElMail() throws Exception {
//        doNothing().when(servicioEmail).sendMimeMessageWithImage(anyString(), anyString(), anyString(), anyMap());
//
//        mockMvc.perform(get("/send-email")
//                        .param("to", "test@example.com")
//                        .param("attachment", "attachment1.txt,attachment2.txt"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Email enviado exitosamente!"));
//
//        verify(servicioEmail, times(1)).sendMimeMessageWithImage(eq("test@example.com"), eq("Tu changuito"), eq("emailTemplate"), anyMap());
//    }
//
//}
