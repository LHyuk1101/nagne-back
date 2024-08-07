package com.nagne.domain.place.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistanceResponse {
    private Double distance;
    private Long place1ContentTypeId;
    private String place1Title;
    private Long place2ContentTypeId;
    private String place2Title;
}