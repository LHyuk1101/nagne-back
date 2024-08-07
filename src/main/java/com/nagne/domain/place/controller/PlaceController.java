package com.nagne.domain.place.controller;

import com.nagne.domain.place.dto.DistanceResponse;
import com.nagne.domain.place.dto.DistanceRequest;
import com.nagne.domain.place.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @PostMapping("/distance")
    public DistanceResponse calculateDistance(@RequestBody DistanceRequest request) {
        return placeService.calculateDistance(request);
    }
}