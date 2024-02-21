package top.kirisamemarisa.onebotspring.core.command;

import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;

/**
 * 命令对象
 */
public interface MrsCommand {

    /**
     * 触发条件
     *
     * @param groupReport 消息
     * @return 是否执行对应的action
     */
    boolean trigger(GroupReport groupReport);

    /**
     * 对应trigger的动作
     *
     * @param groupReport 消息
     */
    void action(GroupReport groupReport);
}
