package com.imd.backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/protected")
public class ProtectedController {
    @GetMapping
    public ResponseEntity<String> secret() {
        return ResponseEntity.ok("Acesso Permitido");
    }
}
