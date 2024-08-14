package com.nagne.domain.place.repository;

import com.nagne.domain.place.dto.ResponsePlaceDto;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {

  ResponsePlaceDto findByRegionAndSearchTerm(Long[] regionIds, int areaCode, String searchTerm,
    Pageable pageable);
}
