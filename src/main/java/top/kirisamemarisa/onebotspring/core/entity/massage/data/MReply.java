package top.kirisamemarisa.onebotspring.core.entity.massage.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.base.MData;

/**
 * @Author: MarisaDAZE
 * @Description: 回复消息
 * @Date: 2024/2/15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class MReply extends MData {

    // 被回复的massage_id
    @JSONField(name = "id")
    private long id;
}
