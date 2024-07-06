package top.kirisamemarisa.onebotspring.entity.onebot.sexes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 命令参数
 * @Date: 2024/3/18
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CmdPreParam {

    private String userId;          // 用户ID
    private int count;              // 操作次数
    private String error = null;   // 错误信息，为空时表示成功

}
