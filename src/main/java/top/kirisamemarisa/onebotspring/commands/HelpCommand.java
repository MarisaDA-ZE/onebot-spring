package top.kirisamemarisa.onebotspring.commands;

import top.kirisamemarisa.onebotspring.commands.base.MrsCommand;
import top.kirisamemarisa.onebotspring.entity.Massage;

public class HelpCommand implements MrsCommand {
    @Override
    public boolean trigger(Massage massage) {
        return false;
    }

    @Override
    public void action(Massage massage) {

    }
}
