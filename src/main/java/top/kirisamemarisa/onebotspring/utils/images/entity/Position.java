package top.kirisamemarisa.onebotspring.utils.images.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Marisa
 * @Description Position.描述
 * @Date 2024/2/4
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private float top;  // 距离顶部
    private float left; // 左侧距离
}
