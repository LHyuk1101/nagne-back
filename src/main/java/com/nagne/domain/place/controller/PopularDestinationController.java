package com.nagne.controller;

import com.nagne.dto.PopularDestinationDto;
import com.nagne.service.PopularDestinationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/populardestinations")
public class PopularDestinationController {
  private final PopularDestinationService popularDestinationService;

  @GetMapping
  public ResponseEntity<List<PopularDestinationDto>> getPopularDestinations() {
    List<PopularDestinationDto> popularDestinations = popularDestinationService.getTop4PopularDestinations();
    return ResponseEntity.ok(popularDestinations);
  }
}