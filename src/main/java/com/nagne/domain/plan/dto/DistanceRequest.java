package com.nagne.domain.plan.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistanceRequest {

  private List<Long> placeIds;
}