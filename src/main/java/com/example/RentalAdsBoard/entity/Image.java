package com.example.RentalAdsBoard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Images")
public class Image extends BaseEntity<Image> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "ad_id", insertable = false, updatable = false)
    private Integer adId;

    @Column(name = "path")
    private String path;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Override
    public Integer getId() {
        return imageId;
    }

    @Override
    public List<Image> getList() {
        return null;
    }
}




