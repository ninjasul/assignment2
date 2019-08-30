package com.assignment.support.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
@Builder
public class Region {
    @Id
    private String code;
    private String name;

    public Region(String code, String name) {
        this.code = code;
        this.name = name;
    }
}