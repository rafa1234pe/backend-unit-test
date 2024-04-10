package com.upc.TuCine.TuCine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AvailableFilm")
public class AvailableFilm {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "FK_AVAILABLEFILM_BUSINESS"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false, foreignKey = @ForeignKey(name = "FK_AVAILABLEFILM_FILM"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Film film;

    @Column(name = "custom_notice")
    @Size(max = 80)
    private String customNotice;

    @Column(name = "is_available", nullable = false)
    private Character isAvailable;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false, foreignKey = @ForeignKey(name = "FK_AVAILABLEFILM_PROMOTION"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Promotion promotion;
}
