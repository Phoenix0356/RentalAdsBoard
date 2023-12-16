package com.example.RentalAdsBoard.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity<Ad> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name="role")
    private Integer role;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name = "avatar_path")
    private String avatarPath;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Ad> ads = new ArrayList<>();


    @Override
    public Integer getId() {
        return userId;
    }

    @Override
    public List<Ad> getList() {
        return ads;
    }

    @Override
    public boolean equals(Object object){
        //compare the reference
        if (this==object) return true;

        if (object==null||object.getClass()!= User.class)return false;

        //compare the value of String
        return username.equals(((User) object).getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }


}
