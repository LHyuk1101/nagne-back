package com.nagne.domain.plan.service;

import com.nagne.domain.place.entity.ContentType;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.repository.PlanRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlaceRepository placeRepository; // Inject PlaceRepository to fetch places

    public List<PlanDto> getAllPlans() {
        return planRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PlanDto getPlanById(Long id) {
        return planRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    private PlanDto convertToDTO(Plan plan) {
        List<PlanDto.PlaceDetail> placeDetails = placeRepository.findAllByPlanId(plan.getId()).stream()
                .map(place -> new PlanDto.PlaceDetail(place.getTitle(), getContentTypeName(place.getContentTypeId())))
                .collect(Collectors.toList());

        return new PlanDto(plan.getId(), plan.getUser().getId(), plan.getStatus().name(), plan.getStartDay(),
                plan.getEndDay(), placeDetails);
    }

    private String getContentTypeName(Long contentTypeId) {
        return ContentType.getNameById(contentTypeId);
    }
}