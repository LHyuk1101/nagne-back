package com.nagne.domain.plan.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class DistanceRequest {

  private List<Long> placeIds;
}