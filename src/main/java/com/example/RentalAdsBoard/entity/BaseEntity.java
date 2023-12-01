package com.example.RentalAdsBoard.entity;
import java.util.List;

public abstract class BaseEntity<T>{
    List<T> list;
    String path;
    public List<T> getList() {
        return list;
    }
    public String getPath(){
        return path;
    }

}
