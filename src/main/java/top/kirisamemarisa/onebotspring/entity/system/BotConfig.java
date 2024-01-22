package top.kirisamemarisa.onebotspring.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: BotConfig.描述
 * @Date: 2024/1/23
 */
@Data
@ToString
public class BotConfig {
    @TableId(type = IdType.AUTO)
    private String id;  // id
    private String userId;  // 用户ID
    private String context; // 配置信息(JSON)
}
