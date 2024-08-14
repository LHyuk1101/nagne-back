package com.nagne.domain.template.controller;

import com.nagne.domain.template.dto.CustomTemplateDto;
import com.nagne.domain.template.service.CustomTemplateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomTemplateController {

  private final CustomTemplateService customTemplateService;

  @GetMapping("/api/templates")
  public ResponseEntity<List<CustomTemplateDto>> getTemplates(@RequestParam int area) {
    List<CustomTemplateDto> templates = customTemplateService.getTemplatesByAreaCode(area);
    return ResponseEntity.ok(templates);
  }
}