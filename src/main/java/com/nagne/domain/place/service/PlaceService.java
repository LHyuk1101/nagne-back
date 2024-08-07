package com.nagne.domain.place.service;

import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.dto.DistanceRequest;
import com.nagne.domain.place.dto.DistanceResponse;
import com.nagne.domain.place.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public DistanceResponse calculateDistance(DistanceRequest request) {
        Place place1 = placeRepository.findById(request.getPlaceId1())
                .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + request.getPlaceId1()));
        Place place2 = placeRepository.findById(request.getPlaceId2())
                .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + request.getPlaceId2()));

        double distance = haversine(place1.getLat(), place1.getLng(), place2.getLat(), place2.getLng());

        DistanceResponse response = DistanceResponse.builder()
                .distance(distance)
                .place1ContentTypeId(place1.getContentTypeId())
                .place1Title(place1.getTitle())
                .place2ContentTypeId(place2.getContentTypeId())
                .place2Title(place2.getTitle())
                .build();

        return response;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구 반지름(KM)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // KM 로 변환
    }
}