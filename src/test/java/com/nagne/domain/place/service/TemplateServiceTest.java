package com.nagne.domain.place.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.nagne.domain.place.entity.ContentType;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(TemplateServiceTest.class);

    @InjectMocks
    private TemplateService templateService;

    @Mock
    private PlaceRepository placeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void calculateDistance_ShouldReturnCorrectDistances() {
        Place place1 = Place.builder()
                .id(1L)
                .lat(37.7749)
                .lng(-122.4194)
                .contentTypeId(76L)  // Matches ContentType.A (관광지)
                .title("Place 1")
                .build();

        Place place2 = Place.builder()
                .id(2L)
                .lat(34.0522)
                .lng(-118.2437)
                .contentTypeId(82L)  // Matches ContentType.C (맛집)
                .title("Place 2")
                .build();

        Place place3 = Place.builder()
                .id(3L)
                .lat(36.1699)
                .lng(-115.1398)
                .contentTypeId(85L)  // Matches ContentType.D (축제)
                .title("Place 3")
                .build();

        when(placeRepository.findById(1L)).thenReturn(Optional.of(place1));
        when(placeRepository.findById(2L)).thenReturn(Optional.of(place2));
        when(placeRepository.findById(3L)).thenReturn(Optional.of(place3));

        DistanceRequest request = DistanceRequest.builder()
                .placeIds(Arrays.asList(1L, 2L, 3L))
                .build();

        logger.info("Test request: {}", request);

        DistanceResponse response = templateService.calculateDistance(request);

        logger.info("Test response: {}", response);

        assertEquals(2, response.getDistances().size());
        assertEquals(559.0, response.getDistances().get(0), 1.0); // Distance between Place 1 and Place 2
        assertEquals(368.0, response.getDistances().get(1), 1.0); // Distance between Place 2 and Place 3

        assertEquals("Place 1 to Place 2", response.getPlaceTitles().get(0));
        assertEquals("Place 2 to Place 3", response.getPlaceTitles().get(1));

        // Check the content type names
        assertEquals(ContentType.TOURIST_SPOT.getName(), response.getPlaceContentTypeNames().get(0)); // 관광지
        assertEquals(ContentType.RESTAURANT.getName(), response.getPlaceContentTypeNames().get(1)); // 맛집
        assertEquals(ContentType.RESTAURANT.getName(),
                response.getPlaceContentTypeNames().get(2)); // 맛집 (again, since it's between Place 2 and 3)
        assertEquals(ContentType.FESTIVAL.getName(), response.getPlaceContentTypeNames().get(3)); // 축제
    }

    @Test
    public void calculateDistance_ShouldThrowException_WhenPlaceNotFound() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.empty());

        DistanceRequest request = DistanceRequest.builder()
                .placeIds(Arrays.asList(1L, 2L, 3L))
                .build();

        logger.info("Test request with non-existing places: {}", request);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            templateService.calculateDistance(request);
        });

        logger.error("Expected exception: {}", exception.getMessage());
        assertEquals("Place not found with id: 1", exception.getMessage());
    }
}