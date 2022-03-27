package cn.zeroclian.sharehub.base.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应封装
 *
 * @author ZeroClian
 * @date 2022-03-26 6:10 下午
 */
@Data
public class Result<T> implements Serializable {

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;

    private int code;
    private String message;
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS, "操作成功", data);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(ERROR, message, null);
    }

}
