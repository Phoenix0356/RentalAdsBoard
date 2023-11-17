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
    Integer userId;
    @Column(name="role")
    Integer role;
    @Column(name="username")
    String username;
    @Column(name="password")
    String password;
    @Column(name="email")
    String email;

}
