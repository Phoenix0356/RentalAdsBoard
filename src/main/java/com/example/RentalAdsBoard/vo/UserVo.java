package com.example.RentalAdsBoard.vo;

import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.util.DataUtil;
import lombok.Data;

@Data
public class UserVo {
    Integer userId;
    Integer role;
    String username;
    String email;
    String avatarBase64;
    String newPassword;
    String originPassword;

    public UserVo(){}
    public void setUserVo(User user){
        this.setAvatarBase64(DataUtil.pictureToBase64(user.getAvatarPath()));
        this.setUserId(user.getUserId());
        this.setEmail(user.getEmail());
        this.setUsername(user.getUsername());
        this.setRole(user.getRole());
    }
}
