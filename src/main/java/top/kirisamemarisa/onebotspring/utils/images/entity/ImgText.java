package top.kirisamemarisa.onebotspring.utils.images.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Marisa
 * @Description ImgText.描述
 * @Date 2024/1/29
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ImgText {
    private String text;        //
    private float top;          //
    private float left;         //
    private float rotate;       //
    private int fontSize;       //
    private String color;       //
    private boolean vertical;   // 竖向排版


}
