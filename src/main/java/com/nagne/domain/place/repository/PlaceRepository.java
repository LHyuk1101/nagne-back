package com.nagne.domain.place.repository;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.entity.PlaceImg;
import com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo;
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
  
  
  @Query(
    "SELECT new com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo(p.id, p.area, p.title, p.address, "
      +
      "p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl, pi.imgUrl) "
      +
      "FROM Place p " +
      "LEFT JOIN Store s ON s.place.id = p.id " +
      "LEFT JOIN Area a ON p.area.id = a.id " +
      "LEFT JOIN PlaceImg pi ON pi.place.id = p.id " +
      "WHERE a.name = :region " +
      "AND p.contentTypeId = 76 " +
      "ORDER BY p.likes DESC, p.id")
  List<PlaceDTOforTravelInfo> findTop10ByRegionAndContentTypeId76OrderByLikes(
    @Param("region") String region,
    Pageable pageable
  );
  
  // contentTypeId가 82인 데이터 중 place_img의 img_url 포함
  @Query(
    "SELECT new com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo(p.id, p.area, p.title, p.address, "
      +
      "p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl, pi.imgUrl) "
      +
      "FROM Place p " +
      "LEFT JOIN Store s ON s.place.id = p.id " +
      "LEFT JOIN Area a ON p.area.id = a.id " +
      "LEFT JOIN PlaceImg pi ON pi.place.id = p.id " +
      "WHERE a.name = :region " +
      "AND p.contentTypeId = 82 " +
      "ORDER BY p.likes DESC, p.id")
  List<PlaceDTOforTravelInfo> findTop10ByRegionAndContentTypeId82OrderByLikes(
    @Param("region") String region,
    Pageable pageable
  );
  
  
  @Query(
    "SELECT new com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo(p.id, p.area, p.title, p.address, "
      + "p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl, pi.imgUrl) "
      + "FROM Place p " +
      "LEFT JOIN Store s ON s.place.id = p.id " +
      "LEFT JOIN Area a ON p.area.id = a.id " +
      "LEFT JOIN PlaceImg pi ON pi.place.id = p.id " +
      "WHERE a.name = :region "
      + "ORDER BY p.likes DESC, p.id"
  )
  List<PlaceDTOforTravelInfo> findAllPlacesByRegion(@Param("region") String region);
  
  
  @Query("SELECT p "
    + "FROM Place p "
    + "JOIN FETCH p.area "
    + "LEFT JOIN FETCH p.placeImgs "
    + "WHERE p.id IN :placeIds")
  List<Place> findPlaceByPlaceId(@Param("placeIds") List<Long> placeIds);
  
  
}
