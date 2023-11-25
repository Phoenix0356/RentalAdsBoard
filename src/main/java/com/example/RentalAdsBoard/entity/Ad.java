package com.example.RentalAdsBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ads")
public class Ad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ad_id")
    private Integer adId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "title")
    private String title;
    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;


}
