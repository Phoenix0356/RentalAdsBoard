package com.example.RentalAdsBoard.vo;

import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.util.DataUtil;
import lombok.Data;

@Data
public class UserVo {
    String username;
    String email;
    String avatarBase64;
    String role;
    String newPassword;
    String originPassword;

    public UserVo(){}
    public UserVo setUserVo(User user){
        this.setAvatarBase64(DataUtil.pictureToBase64(user.getAvatarPath()));
        this.setRole(String.valueOf(user.getRole()));
        this.setEmail(user.getEmail());
        this.setUsername(user.getUsername());
        return this;
    }
}
