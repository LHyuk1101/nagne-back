package com.nagne.domain.place.controller;

import com.nagne.domain.place.dto.DistanceRequest;
import com.nagne.domain.place.dto.DistanceResponse;
import com.nagne.domain.place.service.TemplateService;
import com.nagne.domain.plan.dto.PlanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/distance/text")
    public ResponseEntity<String> getDistanceText(@RequestBody DistanceRequest request, @RequestBody PlanDto planDto) {
        String distanceText = templateService.generateDistanceText(request, planDto);
        return ResponseEntity.ok(distanceText);
    }
}