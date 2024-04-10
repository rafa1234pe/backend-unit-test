package com.upc.TuCine.TuCine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.upc.TuCine.TuCine.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Business")
public class Business {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "social_reason", length = 200, nullable = false)
    private String socialReason;
    @Column(name = "ruc", length = 11, nullable = false)
    private String ruc;
    @Column(name = "phone", length = 9, nullable = false)
    private String phone;
    @Column(name = "logo_src")
    private String logoSrc;
    @Column(name = "banner_src")
    private String bannerSrc;
    @Column(name = "description")
    private String description;
    @Column(name = "address")
    private String address;
    @Column(name = "state")
    private String state;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "opening_hours")
    private String openingHours;

    @ManyToOne
    @JoinColumn(name = "User_id",nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @ManyToOne
    @JoinColumn(name = "BusinessType_id",nullable = false, foreignKey = @ForeignKey(name = "FK_DISTRICT_ID"))
    private BusinessType businessType;
}
