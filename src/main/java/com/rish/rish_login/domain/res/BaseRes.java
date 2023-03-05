package com.rish.rish_login.domain.res;


import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 *
 * @param <T>
 */
@Data
public class BaseRes<T> implements Serializable {

    private Integer code; // 编码：1成功，0和其它数字为失败

    private String msg; // 错误信息

    private T data; // 数据

    private Map<String, Object> map = new HashMap<>(); // 动态数据

    public static <T> BaseRes<T> success(T object) {
        BaseRes<T> baseRes = new BaseRes<>();
        baseRes.data = object;
        baseRes.code = 1;
        return baseRes;
    }

    public static <T> BaseRes<T> error(String msg) {
        BaseRes<T> baseRes = new BaseRes<>();
        baseRes.msg = msg;
        baseRes.code = 0;
        return baseRes;
    }

    public BaseRes<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}