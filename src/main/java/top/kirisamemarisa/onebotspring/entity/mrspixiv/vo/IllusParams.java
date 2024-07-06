package top.kirisamemarisa.onebotspring.entity.mrspixiv.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: 插画查询参数
 * @Date: 2024/6/26
 */
@Data
@ToString
public class IllusParams {

    /** 作品pixiv ID */
    Long pid;

    /** 是否为限制级(全年龄: 0, 限制级: 1, 暴力限制级: 2)*/
    String isR18;

    /** 作品标题 */
    String title;

    /** 作品标签列表 */
    List<String> tags;

    /** 点赞数量 */
    Integer likeCount;

    /** 收藏数量 */
    Integer bookmarkCount;

    /** 返回作品数量 */
    Integer count;
}
