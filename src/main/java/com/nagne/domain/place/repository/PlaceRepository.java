package com.nagne.domain.place.repository;

import com.nagne.domain.place.entity.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

  // Area 엔티티의 areaCode 필드를 참조하도록 수정
  List<Place> findByContentTypeIdAndArea_AreaCode(Long contentTypeId, Integer areaCode);
  List<Place> findByArea_AreaCode(Integer areaCode);

}
