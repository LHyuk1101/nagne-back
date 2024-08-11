package com.nagne.domain.place.repository;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.entity.PlaceImg;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {

  // Area 엔티티의 areaCode 필드를 참조하도록 수정
  List<Place> findByContentTypeIdAndArea_AreaCode(Long contentTypeId, Integer areaCode);

  List<Place> findByArea_AreaCode(Integer areaCode);

  @Query("SELECT new com.nagne.domain.place.dto.PlaceDTO(p.id, p.area, p.title, p.address, " +
    "p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl "
    +
    ") " +
    "FROM Place p " +
    "LEFT JOIN Store s ON s.place.id = p.id " +
    "WHERE p.contentTypeId IN :regionIds " +
    "AND p.area.areaCode = :areaCode " +
    "ORDER BY p.likes DESC, p.id")
  List<PlaceDTO> findByRegion(@Param("regionIds") Long[] regionIds, @Param("areaCode") int areaCode,
    Pageable pageable);

  @Query("SELECT pi "
    + "FROM PlaceImg pi "
    + "WHERE pi.place.id = :id")
  List<PlaceImg> findByPlaceId(Long id);

  Optional<Place> findByTitle(String title);

  // 새로운 메서드들 추가
  @Query("SELECT new com.nagne.domain.place.dto.PlaceDTO(p.id, p.area, p.title, p.address, " +
    "p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl) "
    +
    "FROM Place p " +
    "LEFT JOIN Store s ON s.place.id = p.id " +
    "LEFT JOIN Area a ON p.area.id = a.id " +
    "WHERE a.name = :region " +
    "AND p.contentTypeId = 76 " +
    "ORDER BY p.likes DESC, p.id")
  List<PlaceDTO> findTop10ByRegionAndContentTypeId76OrderByLikes(
    @Param("region") String region,
    Pageable pageable
  );

  @Query("SELECT new com.nagne.domain.place.dto.PlaceDTO(p.id, p.area, p.title, p.address, " +
    "p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl) "
    +
    "FROM Place p " +
    "LEFT JOIN Store s ON s.place.id = p.id " +
    "LEFT JOIN Area a ON p.area.id = a.id " +
    "WHERE a.name = :region " +
    "AND p.contentTypeId = 80 " +
    "ORDER BY p.likes DESC, p.id")
  List<PlaceDTO> findTop10ByRegionAndContentTypeId82OrderByLikes(
    @Param("region") String region,
    Pageable pageable
  );

}
