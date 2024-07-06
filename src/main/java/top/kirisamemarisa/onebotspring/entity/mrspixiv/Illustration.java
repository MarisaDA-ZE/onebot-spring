package top.kirisamemarisa.onebotspring.entity.mrspixiv;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: Illustration 描述
 * @Date: 2024/6/25
 */
@Data
@ToString
@DS("mrs-pixiv")
public class Illustration {

    /** ID */
    @TableId(type = IdType.AUTO)
    private String id;

    /** 作品pixiv ID */
    private Long pid;

    /** 作品标题 */
    private String title;

    /** 作品标签 [tag1, tag2, ...] */
    private String tags;

    /** 迷你图尺寸URL */
    private String urlMini;

    /** 拇指图尺寸URL */
    private String urlThumb;

    /** 小图尺寸URL */
    private String urlSmall;

    /** 默认尺寸URL */
    private String urlRegular;

    /** 原图尺寸URL */
    private String urlOriginal;

    /** 图片宽度（px） */
    private Integer width;

    /** 图片高度（px） */
    private Integer height;

    /** 是否为R-18（0.不是，1.是） */
    private Integer isR18;

    /** 点赞数 */
    private Integer likeCount;

    /** 收藏数 */
    private Integer bookmarkCount;

    /** 回复数 */
    private Integer responseCount;

    /** 作者pixiv ID */
    private Long authorPid;

    /** 作者昵称 */
    private String authorName;

    /** 作者pixiv 账号 */
    private String authorAccount;

    /** 作者头像 */
    private String authorAvatar;

    /** 创建时间 */
    private Date createDate;

    /** 上传日期 */
    private Date uploadDate;
}
