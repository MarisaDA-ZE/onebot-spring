package top.kirisamemarisa.onebotspring.commands.base;

import top.kirisamemarisa.onebotspring.entity.Massage;

/**
 * 基础命令
 */
public interface MrsCommand {

    /**
     * 触发条件
     *
     * @param massage 消息
     * @return 是否执行对应的action
     */
    boolean trigger(Massage massage);

    /**
     * 对应trigger的动作
     *
     * @param massage 消息
     */
    void action(Massage massage);
}
