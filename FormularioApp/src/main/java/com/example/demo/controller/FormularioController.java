package com.example.demo.controller;

import com.example.demo.model.Respuesta;
import com.example.demo.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import org.apache.camel.ProducerTemplate;

@Controller
public class FormularioController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private ProducerTemplate producerTemplate;

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model, @ModelAttribute("caja") Respuesta respuesta) {
        if (respuesta == null) {
            respuesta = new Respuesta();
        }
        model.addAttribute("caja", respuesta);
        return "formulario"; // Thymeleaf 
    }

    @PostMapping("/guardar")
    public String guardarRespuesta(@Valid @ModelAttribute("caja") Respuesta respuesta, 
                                   BindingResult bindingResult, 
                                   RedirectAttributes redirectAttributes) {
        
        // Verificar si hay errores de validación
        if (bindingResult.hasErrors()) {
            return "formulario"; 
        }
        
        try {
            respuestaRepository.save(respuesta);

            // Enviar correo al usuario registrado usando Camel
            String body = "Hola " + respuesta.getFirstName() + " " + respuesta.getLastName() + "\n\n"
                        + "Tu registro se ha completado exitosamente.\n\n"
                        + "Saludos de parte de:\nDaniel Lujan Borjas";

            producerTemplate.sendBodyAndHeader(
                    "direct:sendEmail",
                    body,
                    "To", respuesta.getEmail() // Se envía al correo del usuario
            );

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("mensaje", "Empleado registrado exitosamente!");
            return "redirect:/exito";
        }
        
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            String mensajeError = "Error: ";
            if (e.getMessage().contains("EMPLOYEE_ID")) {
                mensajeError += "Ya existe un empleado con ese ID";
            } else if (e.getMessage().contains("EMAIL")) {
                mensajeError += "Ya existe un empleado con ese email";
            } else if (e.getMessage().contains("PHONE_NUMBER")) {
                mensajeError += "Ya existe un empleado con ese número de teléfono";
            } else {
                mensajeError += "Datos duplicados: " + e.getMessage();
            }
            redirectAttributes.addFlashAttribute("respuesta", respuesta);
            redirectAttributes.addFlashAttribute("error", mensajeError);
            return "redirect:/formulario";
        }
        
        catch (Exception e) {
            // Manejar otros errores de base de datos o envío de correo
            redirectAttributes.addFlashAttribute("respuesta", respuesta);
            redirectAttributes.addFlashAttribute("error", "Error al guardar o enviar correo: " + e.getMessage());
            return "redirect:/formulario";
        }
    }
    
    @GetMapping("/exito")
    public String mostrarExito() {
        return "exito";
    }
    
    @GetMapping("/")
    public String paginaPrincipal() {
        return "redirect:/formulario";
    }
}
