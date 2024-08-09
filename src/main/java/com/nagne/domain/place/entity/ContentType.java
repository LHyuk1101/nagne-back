package com.nagne.domain.place.entity;

import lombok.Getter;

@Getter
public enum ContentType {
    A("76", "관광지"),
    B("80", "숙소"),
    C("82", "맛집"),
    D("85", "축제");

    private final String type;
    private final String name;

    private ContentType(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
