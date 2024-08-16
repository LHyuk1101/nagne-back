package com.nagne.domain.place.repository;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.dto.ResponsePlaceDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {
  
  ResponsePlaceDto findByRegionAndSearchTerm(Long[] regionIds, int areaCode, String searchTerm,
    Pageable pageable);
  
  List<PlaceDTO> searchPlacesByRegionAndKeyword(int areaCode, String keyword);
}
