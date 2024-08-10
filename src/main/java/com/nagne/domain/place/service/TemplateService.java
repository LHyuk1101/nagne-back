package com.nagne.domain.place.service;

import com.nagne.domain.place.entity.ContentType;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.plan.dto.PlanDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nagne.domain.plan.dto.DistanceRequest;
import com.nagne.domain.plan.dto.DistanceResponse;

@Service
public class TemplateService {

    @Autowired
    private PlaceRepository placeRepository;

    public DistanceResponse calculateDistance(DistanceRequest request) {
        List<Long> placeIds = request.getPlaceIds();
        List<Double> distances = new ArrayList<>();
        List<String> placeTitles = new ArrayList<>();
        List<String> placeContentTypeNames = new ArrayList<>();

        for (int i = 0; i < placeIds.size() - 1; i++) {
            int index = i;
            Place place1 = placeRepository.findById(placeIds.get(index))
                    .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeIds.get(index)));
            Place place2 = placeRepository.findById(placeIds.get(index + 1))
                    .orElseThrow(
                            () -> new IllegalArgumentException("Place not found with id: " + placeIds.get(index + 1)));

            double distance = haversine(place1.getLat(), place1.getLng(), place2.getLat(), place2.getLng());
            distances.add(distance);

            placeTitles.add(place1.getTitle());
            placeContentTypeNames.add(getContentTypeName(place1.getContentTypeId()));
        }

        // Add the last place title and content type
        Place lastPlace = placeRepository.findById(placeIds.get(placeIds.size() - 1))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Place not found with id: " + placeIds.get(placeIds.size() - 1)));
        placeTitles.add(lastPlace.getTitle());
        placeContentTypeNames.add(getContentTypeName(lastPlace.getContentTypeId()));

        return DistanceResponse.builder()
                .distances(distances)
                .placeTitles(placeTitles)
                .placeContentTypeNames(placeContentTypeNames)
                .build();
    }

    public String generateDistanceText(DistanceRequest request, PlanDto planDto) {
        // Calculate distances and build response
        DistanceResponse response = calculateDistance(request);

        // Use the duration from the provided PlanDto
        int totalDays = planDto.getDuration();

        // Format the response into a text string
        return formatDistanceResponse(response, totalDays);
    }

    private String formatDistanceResponse(DistanceResponse response, int totalDays) {
        StringBuilder result = new StringBuilder();

        // Add total duration (days)
        result.append("Total days: ").append(totalDays).append(", ");

        // Add place titles and content types
        for (int i = 0; i < response.getPlaceTitles().size(); i++) {
            result.append(response.getPlaceTitles().get(i))
                    .append("(")
                    .append(response.getPlaceContentTypeNames().get(i))
                    .append(")");

            // Add a comma separator if not the last place
            if (i < response.getPlaceTitles().size() - 1) {
                result.append(", ");
            }
        }

        result.append(". ");

        // Add distances between places
        for (int i = 0; i < response.getDistances().size(); i++) {
            result.append("Distance(")
                    .append(response.getPlaceTitles().get(i))
                    .append(" to ")
                    .append(response.getPlaceTitles().get(i + 1))
                    .append("): ")
                    .append(response.getDistances().get(i))
                    .append(" km");

            // Add a comma separator if not the last distance
            if (i < response.getDistances().size() - 1) {
                result.append(", ");
            }
        }

        result.append(".");

        return result.toString();
    }

    private String getContentTypeName(Long contentTypeId) {
        return ContentType.getNameById(contentTypeId);
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