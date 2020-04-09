package com.kstarrain.pojo;


import com.kstarrain.exception.BusinessException;

/**
 * @author: DongYu
 * @create: 2019-11-29 16:32
 * @description: 订单状态
 */
public enum RepayOrderStatusEnum {

    SUCCESS("支付成功"),
    REFUND("转入退款"),
    NOTPAY("未支付"),
    CLOSED("已关闭"),
    REVOKED("已撤销（刷卡支付）"),
    USERPAYING("用户支付中"),
    PAYERROR("支付失败(其他原因，如银行返回失败)"),
    UNKNOW("未知状态");

    private String desc;

    RepayOrderStatusEnum(String desc) {
        this.desc = desc;
    }


    public static RepayOrderStatusEnum findByName(String name) {
        RepayOrderStatusEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RepayOrderStatusEnum repayOrderStatusEnum = var1[var3];
            if (name.toLowerCase().equals(repayOrderStatusEnum.name().toLowerCase())) {
                return repayOrderStatusEnum;
            }
        }
        throw new BusinessException("错误的微信支付状态");
    }


    public String getDesc() {
        return this.desc;
    }
}
