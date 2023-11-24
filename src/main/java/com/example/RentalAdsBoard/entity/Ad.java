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
    Integer adId;
    @Column(name = "user_id")
    Integer userId;
    @Column(name = "title")
    String title;
    @Column(name = "address")
    String address;
    @Column(name = "description")
    String description;


}
