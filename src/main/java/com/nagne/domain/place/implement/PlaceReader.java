package com.nagne.domain.place.implement;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.dto.ResponsePlaceDto;
import com.nagne.domain.place.mapper.PlaceMapper;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.exception.ApiException;
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
  
  private static final Function<String, Long> convertToLong = str -> {
    try {
      return Long.parseLong(str);
    } catch (NumberFormatException e) {
      System.err.println("Error converting " + str + " to Long: " + e.getMessage());
      return null;
    }
  };
  private final PlaceRepository placeRepository;
  private final PlaceMapper placeMapper = PlaceMapper.INSTANCE;
  
  public ResponsePlaceDto readPlace(ReqPlaceDto reqPlaceDto) {
    
    Long[] convertRegions = Arrays.stream(reqPlaceDto.getRegions())
      .map(convertToLong)
      .filter(Objects::nonNull)
      .toArray(Long[]::new);
    PageRequest pageRequest = PageRequest.of(reqPlaceDto.getPage() - 1, reqPlaceDto.getSize());
    
    List<PlaceDTO> byRegion = placeRepository.findByRegion(convertRegions,
      reqPlaceDto.getAreaCode(), pageRequest);
    int totalCount = placeRepository.getTotalCountByRegion(convertRegions,
      reqPlaceDto.getAreaCode());
    
    if (byRegion.isEmpty() || totalCount == 0) {
      throw new ApiException(ErrorCode.PLACE_FOUND_NOT_ERROR);
    }
    
    return ResponsePlaceDto.builder()
      .placeList(byRegion)
      .totalCount(totalCount)
      .build();
    
  }
}
