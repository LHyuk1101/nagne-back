package com.nagne.domain.place.controller;

import com.nagne.domain.place.dto.DistanceRequest;
import com.nagne.domain.place.dto.DistanceResponse;
import com.nagne.domain.place.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @PostMapping("/distance")
    public DistanceResponse calculateDistance(@RequestBody DistanceRequest request) {
        return templateService.calculateDistance(request);
    }
}