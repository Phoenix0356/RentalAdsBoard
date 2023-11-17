package com.example.RentalAdsBoard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean {
    private Integer stateCode;
    private Object obj;

    public ResultBean success(){return new ResultBean(200,null);}
    public ResultBean success(Object obj){return new ResultBean(200,obj);}
    public ResultBean error(){return new ResultBean(500,null);}
    public ResultBean error(Object obj){return new ResultBean(500,obj);}

}
