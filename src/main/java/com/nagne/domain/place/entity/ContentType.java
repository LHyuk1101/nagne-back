package com.nagne.domain.place.entity;

import lombok.Getter;

@Getter
public enum ContentType {
    TOURIST_SPOT(76L, "관광지"),
    ACCOMMODATION(80L, "숙소"),
    RESTAURANT(82L, "맛집"),
    FESTIVAL(85L, "축제");

    private final Long type;
    private final String name;

    ContentType(Long type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getNameById(Long typeId) {
        for (ContentType contentType : values()) {
            if (contentType.getType().equals(typeId)) {
                return contentType.getName();
            }
        }
        throw new IllegalArgumentException("No ContentType found for id: " + typeId);
    }
}