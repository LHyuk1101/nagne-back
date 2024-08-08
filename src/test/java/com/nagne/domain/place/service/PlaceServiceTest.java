package com.nagne.domain.place.service;

import com.nagne.domain.place.dto.DistanceRequest;
import com.nagne.domain.place.dto.DistanceResponse;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class PlaceServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PlaceServiceTest.class);

    @InjectMocks
    private PlaceService placeService;

    @Mock
    private PlaceRepository placeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void calculateDistance_ShouldReturnCorrectDistance() {
        Place place1 = Place.builder()
                .id(1L)
                .lat(37.7749)
                .lng(-122.4194)
                .contentTypeId(81L)
                .title("Place 1")
                .build();

        Place place2 = Place.builder()
                .id(2L)
                .lat(34.0522)
                .lng(-118.2437)
                .contentTypeId(82L)
                .title("Place 2")
                .build();

        when(placeRepository.findById(1L)).thenReturn(Optional.of(place1));
        when(placeRepository.findById(2L)).thenReturn(Optional.of(place2));

        DistanceRequest request = DistanceRequest.builder()
                .placeId1(1L)
                .placeId2(2L)
                .build();

        logger.info("Test request: {}", request);

        DistanceResponse response = placeService.calculateDistance(request);

        logger.info("Test response: {}", response);

        logger.info("Calculated distance: {}", response.getDistance());
        logger.info("Place 1 Content Type ID: {}", response.getPlace1ContentTypeId());
        logger.info("Place 1 Title: {}", response.getPlace1Title());
        logger.info("Place 2 Content Type ID: {}", response.getPlace2ContentTypeId());
        logger.info("Place 2 Title: {}", response.getPlace2Title());

        assertEquals(559.0, response.getDistance(), 1.0); // Allowing a delta of 1 km for precision
        assertEquals(81L, response.getPlace1ContentTypeId());
        assertEquals("Place 1", response.getPlace1Title());
        assertEquals(82L, response.getPlace2ContentTypeId());
        assertEquals("Place 2", response.getPlace2Title());
    }

    @Test
    public void calculateDistance_ShouldThrowException_WhenPlaceNotFound() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.empty());

        DistanceRequest request = DistanceRequest.builder()
                .placeId1(1L)
                .placeId2(2L)
                .build();

        logger.info("Test request with non-existing places: {}", request);

        try {
            placeService.calculateDistance(request);
        } catch (IllegalArgumentException e) {
            logger.error("Expected exception: {}", e.getMessage());
            assertEquals("Place not found with id: 1", e.getMessage());
        }
    }
}