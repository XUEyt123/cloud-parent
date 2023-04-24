package org.example.cloudentity.domain;

import lombok.Data;

@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = -1;

    private R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> ok(Integer code, String msg, T data) {
        return new R<T>(code, msg, data);
    }

    public static <T> R<T> ok(T data) {
        return ok(SUCCESS, "请求成功", data);
    }
    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> fail(Integer code, String msg, T data) {
        return new R<T>(code, msg, data);
    }

    public static <T> R<T> fail(T data) {
        return fail(FAIL, "请求失败", data);
    }
    public static <T> R<T> fail() {
        return fail(null);
    }
}

