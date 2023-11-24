package com.example.RentalAdsBoard.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ad_id")
    Integer adId;
    @Column(name = "title")
    String title;
    @Column(name = "address")
    String address;
    @Column(name = "description")
    String description;
    @Column(name = "user_id")
    Integer userId;
}
