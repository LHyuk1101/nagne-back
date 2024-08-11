package com.nagne.domain.place.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nagne.domain.place.entity.Area;
import com.nagne.domain.place.entity.PlaceImg;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceDTO {

  private Long id;
  private Area area;
  private Integer areaCode;
  private String title;
  private String address;
  private Long contentTypeId;
  private String overview;
  private String contactNumber;
  private String opentime;
  private Double lat;
  private Double lng;
  private int likes;
  private String thumbnailUrl;
  private List<String> placeUrlImages;

  public PlaceDTO(Long id, Area area, String title, String address,
    Long contentTypeId, String overview, String contactNumber, String opentime, Double lat,
    Double lng,
    int likes, String thumbnailUrl) {
    this.id = id;
    this.area = area;
    this.title = title;
    this.address = address;
    this.contentTypeId = contentTypeId;
    this.overview = overview;
    this.contactNumber = contactNumber;
    this.opentime = opentime;
    this.lat = lat;
    this.lng = lng;
    this.likes = likes;
    this.thumbnailUrl = thumbnailUrl;
  }

  public PlaceDTO addPlaceImg(List<PlaceImg> placeImgs) {
    if (this.placeUrlImages == null) {
      this.placeUrlImages = new ArrayList<>();
    }

    if (placeImgs != null) {
      List<String> imgUrls = placeImgs.stream()
        .map(PlaceImg::getImgUrl)
        .filter(Objects::nonNull)
        .toList();

      this.placeUrlImages.addAll(imgUrls);
    }

    return this;
  }

}
