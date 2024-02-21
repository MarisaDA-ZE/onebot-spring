package top.kirisamemarisa.onebotspring.utils.images.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @Author Marisa
 * @Description 表情包文字
 * @Date 2024/2/19
 */
@Data
@ToString
public class EmojiText {
    private String text;        // 文本内容
    private int fontSize;       // 文字大小
    private int bold;           // 文字加粗
    private String color;       // 文字颜色
    private Position position;  // 文字位置
    private float rotate;       // 旋转角度
    private String Family;      // 字体
}
