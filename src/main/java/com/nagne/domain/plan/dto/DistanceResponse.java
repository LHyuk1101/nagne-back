package com.nagne.domain.plan.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class DistanceResponse {
    private List<Double> distances;
    private List<String> placeTitles;
    private List<String> placeContentTypeNames;
}