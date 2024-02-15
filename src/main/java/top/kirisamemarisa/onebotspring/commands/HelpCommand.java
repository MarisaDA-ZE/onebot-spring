package top.kirisamemarisa.onebotspring.commands;

import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.GroupReport;

@BotCommand
public class HelpCommand implements MrsCommand {
    @Override
    public boolean trigger(GroupReport groupReport) {
        System.out.println(groupReport);

        return true;
    }

    @Override
    public void action(GroupReport groupReport) {
        System.out.println(">>>>");

    }
}
