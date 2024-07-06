package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 礼物 （仅发送）
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E7%A4%BC%E7%89%A9">go-cqhttp 参考</a></p>
 * <p>onebot-11 不支持</p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQGift extends CQData {

    /**
     * <p>go-cqhttp: 接收礼物的成员</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "qq")
    private String qq;

    /**
     * <p>go-cqhttp: 礼物的类型</p>
     * <p>
     * 0	甜 Wink
     * 1	快乐肥宅水
     * 2	幸运手链
     * 3	卡布奇诺
     * 4	猫咪手表
     * 5	绒绒手套
     * 6	彩虹糖果
     * 7	坚强
     * 8	告白话筒
     * 9	牵你的手
     * 10	可爱猫咪
     * 11	神秘面具
     * 12	我超忙的
     * 13	爱心口罩
     * </p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "id")
    private Integer id;

}
