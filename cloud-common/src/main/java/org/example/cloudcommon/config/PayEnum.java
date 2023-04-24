package org.example.cloudcommon.config;



/**
 * 支付枚举类型
 *
 * @author zed
 * @date 2023/03/20
 */
public enum PayEnum {
    ALIPAY(1),WXPAY(2),CARDPAY(3);

    int value;

    PayEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
