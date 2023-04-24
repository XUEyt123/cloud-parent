package org.example.cloudcoupon.config;


import lombok.extern.slf4j.Slf4j;
import org.example.cloudentity.domain.R;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
@Order(1)
public class MyExceptionHandler {

    // 兜底方案 优先级低
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        log.info("出现异常了，异常信息是:{}", e.getMessage());
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            List<FieldError> errors = result.getFieldErrors();
            if (!CollectionUtils.isEmpty(errors)) {
                // 返回所有字段的错误提示
                List<String> messages = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
                return R.fail(messages);
            }
        }
        return R.fail("其他异常");
    }

}

