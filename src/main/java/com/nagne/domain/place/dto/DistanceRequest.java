package com.nagne.domain.place.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistanceRequest {
    private List<Long> placeIds;
}
