package com.nagne.domain.place.repository;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

  @Query(
    value = "SELECT p.place_id, p.area_code, p.title, p.address, p.content_type_id, p.overview, "
      + "COALESCE(s.contact_number, '') AS contact_number, COALESCE(s.open_time, '') AS open_time, "
      + "p.lat, p.lng, p.likes, p.thumbnail_url, pi.img_url "
      + "FROM place p "
      + "LEFT JOIN store s ON s.place_id = p.place_id "
      + "LEFT JOIN place_img pi ON pi.place_id = p.place_id "
      + "WHERE p.area_code = :areaCode "
      + "AND MATCH(p.title) AGAINST(:keyword IN BOOLEAN MODE) "
      + "ORDER BY p.likes DESC, p.place_id",
    nativeQuery = true
  )
  List<Object[]> searchPlacesByRegionAndKeyword(@Param("areaCode") int areaCode,
    @Param("keyword") String keyword);

  // Area 엔티티의 areaCode 필드를 참조하도록 수정
  List<Place> findByContentTypeIdAndArea_AreaCode(Long contentTypeId, Integer areaCode);

  @Query(
    "SELECT DISTINCT new com.nagne.domain.place.dto.PlaceDTO(p.id, p.area, p.title, p.address, "
      + "p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl, pi.imgUrl ) "
      + "FROM Place p "
      + "LEFT JOIN Store s ON s.place.id = p.id "
      + "LEFT JOIN p.placeImgs pi "
      + "WHERE p.contentTypeId IN :regionIds "
      + "AND p.area.areaCode = :areaCode "
      + "ORDER BY p.likes DESC, p.id")
  List<PlaceDTO> findTopLikesByRegion(@Param("regionIds") Long[] regionIds,
    @Param("areaCode") int areaCode,
    Pageable pageable);

  @Query(
    "SELECT new com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo(p.id, p.area, p.title, p.address, "
      + "p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl, pi.imgUrl) "
      + "FROM Place p " +
      "LEFT JOIN Store s ON s.place.id = p.id " +
      "LEFT JOIN Area a ON p.area.id = a.id " +
      "LEFT JOIN PlaceImg pi ON pi.place.id = p.id " +
      "WHERE a.areaCode = :areaCode "
      + "ORDER BY p.likes DESC, p.id"
  )
  List<PlaceDTOforTravelInfo> findAllPlacesByRegion(@Param("areaCode") int areaCode);


  @Query("SELECT p "
    + "FROM Place p "
    + "JOIN FETCH p.area "
    + "LEFT JOIN FETCH p.placeImgs "
    + "WHERE p.id IN :placeIds")
  List<Place> findPlaceByPlaceId(@Param("placeIds") List<Long> placeIds);


}
