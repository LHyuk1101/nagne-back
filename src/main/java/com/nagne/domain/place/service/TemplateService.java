package com.nagne.domain.place.service;

import com.nagne.domain.place.dto.DistanceRequest;
import com.nagne.domain.place.dto.DistanceResponse;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Autowired
    private PlaceRepository placeRepository;

    public DistanceResponse calculateDistance(DistanceRequest request) {
        List<Long> placeIds = request.getPlaceIds();
        List<Double> distances = new ArrayList<>();
        List<String> placeTitles = new ArrayList<>();
        List<Long> placeContentTypeIds = new ArrayList<>();

        for (int i = 0; i < placeIds.size() - 1; i++) {
            final int index = i;
            Place place1 = placeRepository.findById(placeIds.get(index))
                    .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeIds.get(index)));
            Place place2 = placeRepository.findById(placeIds.get(index + 1))
                    .orElseThrow(
                            () -> new IllegalArgumentException("Place not found with id: " + placeIds.get(index + 1)));

            double distance = haversine(place1.getLat(), place1.getLng(), place2.getLat(), place2.getLng());
            distances.add(distance);

            placeTitles.add(place1.getTitle() + " to " + place2.getTitle());

            placeContentTypeIds.add(place1.getContentTypeId());
            placeContentTypeIds.add(place2.getContentTypeId());
        }

        return DistanceResponse.builder()
                .distances(distances)
                .placeTitles(placeTitles)
                .placeContentTypeIds(placeContentTypeIds)
                .build();
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in KM

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Convert to KM
    }
}