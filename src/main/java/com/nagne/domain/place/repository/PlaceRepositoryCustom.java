package com.nagne.domain.place.repository;

import com.nagne.domain.place.dto.PlaceDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {

  List<PlaceDTO> findByRegionAndSearchTerm(Long[] regionIds, int areaCode, String searchTerm,
    Pageable pageable);
}
