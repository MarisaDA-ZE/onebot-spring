package top.kirisamemarisa.onebotspring.core.command;


import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;

/**
 * 命令对象
 */
public interface MrsCommand {

    /**
     * 触发条件
     *
     * @param report 上报消息
     * @return 是否执行对应的action
     */
    boolean trigger(MrsReport report);

    /**
     * 对应trigger的动作
     *
     * @param report 上报消息
     */
    void action(MrsReport report);
}
