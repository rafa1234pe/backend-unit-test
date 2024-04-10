package com.upc.TuCine.TuCine.dto.save.Business;

import lombok.Data;

@Data
public class BusinessSaveDto {
    private String name;
    private String socialReason;
    private String ruc;
    private String phone;
    private String logoSrc;
    private String bannerSrc;
    private String description;
    private String address;
    private String state;
    private Integer capacity;
    private String openingHours;
    private BusinessOwnerSaveDto user;
    private BusinessTypeSaveDto businessType;
}
