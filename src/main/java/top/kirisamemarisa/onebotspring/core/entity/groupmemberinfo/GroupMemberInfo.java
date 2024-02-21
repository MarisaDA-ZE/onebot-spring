package top.kirisamemarisa.onebotspring.core.entity.groupmemberinfo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 获取群成员信息对象
 * @Date: 2024/2/21
 */
@Data
@ToString
public class GroupMemberInfo {

    // 状态码
    @JSONField(name = "status")
    private int status;

    // 返回码
    @JSONField(name = "retcode")
    private int retcode;

    // 用户信息
    @JSONField(name = "data")
    private GroupMemberDetail data;

    // 信息
    @JSONField(name = "massage")
    private String massage;
}
