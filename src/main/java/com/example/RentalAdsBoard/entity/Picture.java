package com.example.RentalAdsBoard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pictures")
public class Picture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "picture_id")
    private Integer pictureId;

//    @Column(name = "ad_id",insertable = false, updatable = false)
//    private Integer adId;

    @Column(name = "path")
    private String path;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_id")
    private Ad ad;
}
