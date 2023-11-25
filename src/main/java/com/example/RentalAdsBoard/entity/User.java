package com.example.RentalAdsBoard.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
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

}
