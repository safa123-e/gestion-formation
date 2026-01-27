package com.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @GetMapping("/auth/ping")
  public String ping() {
    return "auth-service OK";
  }
}

