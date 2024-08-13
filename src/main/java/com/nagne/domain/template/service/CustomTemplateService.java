package com.nagne.domain.template.service;

import com.nagne.domain.template.dto.CustomTemplateDto;
import com.nagne.domain.template.repository.CustomTemplateRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomTemplateService {

  private final CustomTemplateRepository customTemplateRepository;

  public List<CustomTemplateDto> getTemplatesByAreaCode(int areaCode) {
    return customTemplateRepository.findCustomTemplateByAreaCode(areaCode).stream().map(template ->
      CustomTemplateDto.builder()
        .id(template.getId())
        .subject(template.getPlan().getSubject())
        .overview(template.getPlan().getOverview())
        .thumbnailUrl(template.getPlan().getThumbnailUrl())
        .areaCode(String.valueOf(template.getPlan().getArea().getAreaCode()))
        .build()
    ).collect(Collectors.toList());
  }
}