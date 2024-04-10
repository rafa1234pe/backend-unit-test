package com.upc.TuCine.TuCine.dto;

import com.upc.TuCine.TuCine.model.BusinessType;
import com.upc.TuCine.TuCine.user.domain.model.User;
import lombok.Data;

@Data
public class BusinessDto {
    private Integer id;
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

    private User user;
    private BusinessType businessType;
}
