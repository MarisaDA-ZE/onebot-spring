package top.kirisamemarisa.onebotspring.entity.sexes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: 群涩涩用户绑定群老婆
 * @Date: 2024/2/21
 */
@Data
@ToString
public class GroupSexWife {
    @TableId(type = IdType.AUTO)
    private String id;          // 主键ID
    private String userId;      // 用户ID
    private String userQq;      // 用户QQ号
    private String wifeId;      // 群老婆ID
    private String wifeQq;      // 群老婆QQ号
    private String nickName;    // 群老婆昵称
    private String callName;    // 群老婆称呼
    private Date createTime;    // 创建时间
    private String createBy;    // 创建人
    private Date updateTime;    // 更新时间
    private String updateBy;    // 更新人

}
