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

    public ResultVo success(){return new ResultVo(200,null);}
    public ResultVo success(Object obj){return new ResultVo(200,obj);}
    public ResultVo error(){return new ResultVo(500,null);}
    public ResultVo error(Object obj){return new ResultVo(500,obj);}

}
