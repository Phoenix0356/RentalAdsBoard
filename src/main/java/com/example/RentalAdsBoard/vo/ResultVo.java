package com.example.RentalAdsBoard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo {
    private Integer stateCode;
    private Object obj;
    private String message;
    public ResultVo success(){return new ResultVo(200,null,"success");}
    public ResultVo success(Object obj){return new ResultVo(200,obj,"success");}
    public ResultVo error(String message){return new ResultVo(500,null,message);}
    public ResultVo error(Object obj,String message){return new ResultVo(500,obj,message);}

}
