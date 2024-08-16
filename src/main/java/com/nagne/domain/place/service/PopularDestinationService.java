package com.nagne.domain.place.service;

import com.nagne.domain.place.dto.PopularDestinationDto;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PopularDestinationRepository;
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
        .id(place.getId())
        .address(place.getAddress())
        .contactNumber(place.getStore() != null ? place.getStore().getContactNumber() : null)
        .imgUrl(
          place.getPlaceImgs() != null && !place.getPlaceImgs().isEmpty() ? place.getPlaceImgs()
            .get(0).getImgUrl()
            : null)
        .likes(place.getLikes())
        .thumbnailUrl(place.getThumbnailUrl())
        .title(place.getTitle())
        .overview(place.getOverview())
        .build())
      .collect(Collectors.toList());
  }
}