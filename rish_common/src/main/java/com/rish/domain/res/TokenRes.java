package com.rish.domain.res;

import lombok.Data;

import java.util.List;

@Data
public class TokenRes {
    /**
     * 是否有效
     */
    private String isState;

    /**
     * 角色拥有的权利
     */
    private List<String> rolePower;
}
