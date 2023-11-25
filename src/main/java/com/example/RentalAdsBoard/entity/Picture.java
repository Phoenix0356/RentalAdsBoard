package com.example.RentalAdsBoard.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "picture_id")
    private Integer pictureId;

    @Column(name = "ad_id")
    private Integer adId;

    @Column(name = "path")
    private String path;

}
