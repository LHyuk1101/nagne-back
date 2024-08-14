package com.nagne.domain.template.service;

import com.nagne.domain.template.dto.CustomTemplateDto;
import com.nagne.domain.template.entity.Template;
import com.nagne.domain.template.repository.CustomTemplateRepository;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomTemplateService {

  private final CustomTemplateRepository customTemplateRepository;

  public List<CustomTemplateDto> getTemplatesByAreaCode(int areaCode) {

    List<Template> templates = customTemplateRepository.findCustomTemplateByAreaCode(areaCode);

    if (templates.isEmpty()) {
      throw new ApiException(ErrorCode.ENTITY_NOT_FOUND);
    }

    List<CustomTemplateDto> customTemplateDtos = templates.stream()
      .map(template -> CustomTemplateDto.builder()
        .id(template.getId())
        .subject(template.getPlan().getSubject())
        .overview(template.getPlan().getOverview())
        .thumbnailUrl(template.getPlan().getThumbnailUrl())
        .areaCode(String.valueOf(template.getPlan().getArea().getAreaCode()))
        .build())
      .toList();

    return customTemplateDtos;
  }
}