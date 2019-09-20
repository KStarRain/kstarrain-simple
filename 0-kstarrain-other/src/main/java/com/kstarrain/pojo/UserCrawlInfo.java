package com.kstarrain.pojo;

import com.kstarrain.utils.Base64Utils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

@Data
public class UserCrawlInfo {

    /** 主键 */
    private Long id;

    /** 用户id */
    private Integer userId;

    /** 爬取类型（1：通讯录  2：通话记录） */
    private String type;

    /** 爬取内容 */
    private String content;

    /** 操作系统 */
    private String opSystem;

    /** 创建时间 */
    private Date createDate;

    /** 创建者 */
    private String createUser;

    /** 更新时间 */
    private Date updateDate;

    /** 更新者 */
    private String updateUser;

    /** 数据存活标记 */
    private Integer aliveFlag;


    public static AddressBook instanceAddressBook() {
        return new AddressBook();
    }


    @Data
    public static class AddressBook {

        /** 联系人姓名 */
        private String name;

        /** 联系人姓名是否需要解码（因为如果有表情时，存入前会先进行编码） */
        private boolean decode;

        /** 联系人电话号 */
        private List<String> phone;


        public void encodeName(String name) {
            this.decode = true;
            if (StringUtils.isNotBlank(name)) {
                this.name = Base64Utils.encode(name.getBytes());
            }
        }


        public String decodeName() {
            if (StringUtils.isBlank(name) || !decode) {
                return name;
            } else {
                byte[] binaryContent = Base64Utils.decode(name);
                return new String(binaryContent);
            }
        }
    }
}