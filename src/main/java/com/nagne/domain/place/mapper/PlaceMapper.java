package com.nagne.domain.place.mapper;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.entity.PlaceImg;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import org.mapstruct.factory.Mappers;

@Mapper
public interface PlaceMapper {
  PlaceMapper INSTANCE = Mappers.getMapper(PlaceMapper.class);

  @Mapping(source = "placeImgs", target = "placeUrlImages", qualifiedByName = "placeImgsToUrls")
  PlaceDTO placeToPlaceDTO(Place place);

  Place placeDTOToPlace(PlaceDTO placeDTO);

  @Named("placeImgsToUrls")
  default List<String> placeImgsToUrls(List<PlaceImg> placeImgs) {
    return placeImgs.stream()
        .map(PlaceImg::getImgUrl)
        .toList();
  }

}
