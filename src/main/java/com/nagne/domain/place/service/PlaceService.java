package com.nagne.domain.place.service;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.implement.PlaceReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

	private final PlaceReader placeReader;

	public List<PlaceDTO> fetchPlaceByRegion(ReqPlaceDto reqPlaceDto) {

		return placeReader.readPlace(reqPlaceDto);
	}


}
