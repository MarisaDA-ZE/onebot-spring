package top.kirisamemarisa.onebotspring.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 指令类型的枚举
 * @Date: 2024/2/29
 */
public enum CommandType {
    TYPE_1(1),  // @<target> /cmd [count次]
    TYPE_2(2);  // /cmd <target> [count次]

    private final int commandType;

    CommandType(int commandType) {
        this.commandType = commandType;
    }

    /**
     * 获取commandType的值
     *
     * @return .
     */
    public int getCommandType() {
        return commandType;
    }
}
