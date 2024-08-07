package com.nagne.domain.place.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistanceRequest {
    private Long placeId1;
    private Long placeId2;
}
