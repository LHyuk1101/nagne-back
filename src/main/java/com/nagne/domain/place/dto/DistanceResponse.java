package com.nagne.domain.place.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistanceResponse {
    private List<Double> distances;
    private List<String> placeTitles;
    private List<Long> placeContentTypeIds;
}