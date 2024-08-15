package com.nagne.service;

import com.nagne.domain.place.entity.Place;
import com.nagne.dto.PopularDestinationDto;
import com.nagne.repository.PopularDestinationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularDestinationService {
  private final PopularDestinationRepository popularDestinationRepository;

  public List<PopularDestinationDto> getTop4PopularDestinations() {
    List<Place> places = popularDestinationRepository.findTop4ByLikes();
    return places.stream()
      .map(place -> PopularDestinationDto.builder()
        .thumbnailUrl(place.getThumbnailUrl())
        .title(place.getTitle())
        .overview(place.getOverview())
        .build())
      .collect(Collectors.toList());
  }
}