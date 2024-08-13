package com.nagne.domain.place.mapper;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlaceMapper {

  PlaceMapper INSTANCE = Mappers.getMapper(PlaceMapper.class);

  @Mapping(target = "placeImgs", ignore = true)
  PlaceDTO placeToPlaceDTO(Place place);
  
  @Mapping(target = "placeImgs", ignore = true)
  Place placeDTOToPlace(PlaceDTO placeDTO);


}
