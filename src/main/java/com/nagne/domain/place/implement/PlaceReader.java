package com.nagne.domain.place.implement;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.mapper.PlaceMapper;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.exception.ApiException;
import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class PlaceReader {

  private final PlaceRepository placeRepository;
  private final PlaceMapper placeMapper = PlaceMapper.INSTANCE;


  public List<PlaceDTO> readPlace(String [] regions) {

    Long[] convertRegions = Arrays.stream(regions)
        .map(convertToLong)
        .filter(Objects::nonNull)
        .toArray(Long[]::new);
    PageRequest pageRequest = PageRequest.of(1, 3);

    List<Place> byRegion = placeRepository.findByRegion(convertRegions, pageRequest);

    if(byRegion.isEmpty()) {
      throw new ApiException(ErrorCode.PLACE_FOUND_NOT_ERROR);
    }

    return byRegion.stream()
        .map(placeMapper::placeToPlaceDTO)
        .toList();
  }


  private static final Function<String, Long> convertToLong = str -> {
    try {
      return Long.parseLong(str);
    } catch (NumberFormatException e) {
      System.err.println("Error converting " + str + " to Long: " + e.getMessage());
      return null;
    }
  };
}
