package com.nagne.domain.place.mapper;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlaceMapper {

  PlaceMapper INSTANCE = Mappers.getMapper(PlaceMapper.class);

  PlaceDTO placeToPlaceDTO(Place place);

  Place placeDTOToPlace(PlaceDTO placeDTO);


}
