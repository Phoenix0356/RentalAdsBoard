package com.example.RentalAdsBoard.entity;

import java.util.List;

public abstract class BaseEntity<T> {
    public abstract Integer getId();
    public abstract List<T> getList();
}
