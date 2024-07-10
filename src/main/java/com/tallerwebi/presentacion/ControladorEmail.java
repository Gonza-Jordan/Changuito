package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ControladorEmail {

    @Autowired
    private ServicioEmail servicioEmail;
    private ServicioUsuario servicioUsuario;
    private ServicioPromocion servicioPromocion;

    public ControladorEmail(ServicioUsuario servicioUsuario, ServicioPromocion servicioPromocion) {
        this.servicioUsuario = servicioUsuario;
        this.servicioPromocion = servicioPromocion;
    }


    @GetMapping("/send-email")
    public String sendEmail() {
        try {
            Map<String, Object> variables = new HashMap<>();
//            sql to email user

            List<Usuario> usuariosEmail = servicioUsuario.consultarTodosLosUsuarios();

            for (Usuario usuario : usuariosEmail) {
                servicioEmail.sendMimeMessageWithImage(usuario.getEmail(), "Tu changuito", "emailTemplate", variables);
            }

//            servicioEmail.sendMimeMessageWithImage(to, "Tu changuito", "emailTemplate", variables);
            return "Email enviado exitosamente!";
        } catch (MessagingException | IOException e) {
            return "Error enviando email: " + e.getMessage();
        }
    }

    @GetMapping("/send-email-carrito")
    public String sendEmailCarrito() {
        try {
            Map<String, Object> variables = new HashMap<>();
//            sql to email user

            List<Usuario> usuariosEmail = servicioUsuario.consultarTodosLosUsuarios();

            for (Usuario usuario : usuariosEmail) {

                if (!usuario.getAdmin()) {

                    List<Promocion> promoUsuario = new ArrayList<>();
                    usuario.getFavoritos().forEach(producto -> promoUsuario.addAll(servicioPromocion.obtenerPromocionesDeProducto(producto)));



                    StringBuilder email = new StringBuilder();
                    email.append("<!DOCTYPE html>");
                    email.append("<html lang=\"en\">");
                    email.append("  <head>");
                    email.append("    <meta charset=\"UTF-8\" />");
                    email.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
                    email.append("    <title>Tu changuito</title>");
                    email.append("    <style>");
                    email.append("      body {");
                    email.append("        font-family: Arial, sans-serif;");
                    email.append("        background-color: #f4f4f4;");
                    email.append("        margin: 0;");
                    email.append("        padding: 0;");
                    email.append("      }");
                    email.append("      .container {");
                    email.append("        width: 100%;");
                    email.append("        max-width: 600px;");
                    email.append("        margin: 0 auto;");
                    email.append("        background-color: #ffffff;");
                    email.append("        padding: 20px;");
                    email.append("        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
                    email.append("      }");
                    email.append("      .banner {");
                    email.append("        width: 100%;");
                    email.append("        text-align: center;");
                    email.append("        background-color: #007bff;");
                    email.append("        color: white;");
                    email.append("        padding: 10px 0;");
                    email.append("        font-size: 24px;");
                    email.append("        margin-bottom: 20px;");
                    email.append("      }");
                    email.append("      h1 {");
                    email.append("        color: #333333;");
                    email.append("      }");
                    email.append("      p {");
                    email.append("        font-size: 16px;");
                    email.append("        color: #666666;");
                    email.append("      }");
                    email.append("      .promotions {");
                    email.append("        margin-top: 20px;");
                    email.append("      }");
                    email.append("      .promotion-item {");
                    email.append("        border-bottom: 1px solid #dddddd;");
                    email.append("        padding: 10px 10px;");
                    email.append("        display: flex;");
                    email.append("        align-items: center;");
                    email.append("      }");
                    email.append("      img {");
                    email.append("        height: 10vh;");
                    email.append("        margin-right: 20px;");
                    email.append("      }");
                    email.append("      .promotion-details {");
                    email.append("        flex: 1;");
                    email.append("      }");
                    email.append("      .promotion-details h2 {");
                    email.append("        margin: 0 0 10px;");
                    email.append("        font-size: 18px;");
                    email.append("        color: #333333;");
                    email.append("      }");
                    email.append("      .promotion-details p {");
                    email.append("        margin: 5px 0;");
                    email.append("        font-size: 14px;");
                    email.append("        color: #666666;");
                    email.append("      }");
                    email.append("      .footer {");
                    email.append("        text-align: center;");
                    email.append("        margin-top: 20px;");
                    email.append("        font-size: 12px;");
                    email.append("        color: #999999;");
                    email.append("      }");
                    email.append("      .promotion-paquete-title {");
                    email.append("        margin-bottom: 20px;");
                    email.append("      }");
                    email.append("      .promotion-paquete-producto {");
                    email.append("        margin-bottom: 20px;");
                    email.append("      }");
                    email.append("      .promotion-paquete-item {");
                    email.append("        border-bottom: 1px solid #dddddd;");
                    email.append("        padding: 0 10px;");
                    email.append("        align-items: center;");
                    email.append("      }");
                    email.append("      h5 {");
                    email.append("        margin: 5px;");
                    email.append("      }");
                    email.append("    </style>");
                    email.append("  </head>");
                    email.append("  <body>");
                    email.append("    <div class=\"container\">");
                    email.append("      <div class=\"banner\">");
                    email.append("        <h5>Hola Jose, tenemos estas promociones para vos.</h5>");
                    email.append("      </div>");

                    for (Promocion promo : promoUsuario) {
                        if (promo instanceof Paquete) {
                            email.append("<div class=\"promotion-paquete-item\">\n");
                            email.append("<div class=\"promotion-paquete-title\">\n");
                            email.append("<h2>").append(((Paquete) promo).getNombre()).append("</h2>\n");
                            email.append("<div class=\"promotion-details\">\n");
                            email.append("<p>Precio final: $").append(((Paquete) promo).getPrecioFinal()).append("</p>\n");
                            email.append("<p>Descuento aplicado: %").append(((Paquete) promo).getDescuento()).append("</p>\n");
                            email.append("<p>Fecha inicio: ").append(((Paquete) promo).getFechaInicio()).append("</p>\n");
                            email.append("<p>Fecha fin: ").append(((Paquete) promo).getFechaFin()).append("</p>\n");
                            email.append("</div>\n");
                            email.append("</div>\n");

                            for (SupermercadoProducto superProdu : ((Paquete) promo).getProductos()) {
                                email.append("<div class=\"promotion-paquete-producto\">\n");
                                email.append("<img src=\"")
                                        .append(superProdu.getProducto().getUrlImagen())
                                        .append("\" alt=\"")
                                        .append(superProdu.getProducto().getNombre())
                                        .append("\">\n");
                                email.append("<div class=\"promotion-details\">\n");
                                email.append("<h2>").append(superProdu.getProducto().getNombre()).append("</h2>\n");
                                email.append("</div>\n");
                                email.append("</div>\n");
                            }
                            email.append("</div>\n");
                        } else if (promo instanceof Combo) {
                            email.append("<div class=\"promotion-item\">\n");
                            email.append("<img src=\"")
                                    .append(((Combo) promo).getProducto().getProducto().getUrlImagen())
                                    .append("\" alt=\"")
                                    .append(((Combo) promo).getProducto().getProducto().getNombre())
                                    .append("\">\n");
                            email.append("<div class=\"promotion-details\">\n");
                            email.append("<h2>").append(((Combo) promo).getProducto().getProducto().getNombre()).append("</h2>\n");
                            email.append("<div Promocion:>\n");
                            email.append("<span>").append(((Combo) promo).getCantidadVendida()).append("</span>\n");
                            email.append("<span>X</span>\n");
                            email.append("<span>").append(((Combo) promo).getCantidadCobrada()).append("</span>\n");
                            email.append("</div>\n");
                            email.append("<p>Precio final: $").append(((Combo) promo).getPrecioFinal()).append("</p>\n");
                            email.append("<p>Fecha inicio: ").append(((Combo) promo).getFechaInicio()).append("</p>\n");
                            email.append("<p>Fecha fin: ").append(((Combo) promo).getFechaFin()).append("</p>\n");
                            email.append("</div>\n");
                            email.append("</div>\n");
                        }
                    }

                    email.append("      <div class=\"footer\">");
                    email.append("        <p>¡Aprovecha estas promociones antes de que se terminen!</p>");
                    email.append("        <p>&copy; 2024 Tu changuito. Todos los derechos reservados.</p>");
                    email.append("      </div>");
                    email.append("    </div>");
                    email.append("  </body>");
                    email.append("</html>");

                    String emailString = email.toString();

                    servicioEmail.sendMimeMessageWithImage(usuario.getEmail(), "Tu changuito", emailString, variables);
                }
            }

//            servicioEmail.sendMimeMessageWithImage(to, "Tu changuito", "emailTemplate", variables);
            return "Email enviado exitosamente!";
        } catch (MessagingException | IOException e) {
            return "Error enviando email: " + e.getMessage();
        }
    }
}
