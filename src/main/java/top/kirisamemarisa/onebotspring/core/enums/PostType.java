package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 上报类型的枚举
 * @Date: 2024/1/20
 */
public enum PostType {
    MESSAGE("message"); // 私聊消息

    private final String postType;

    PostType(String postType) {
        this.postType = postType;
    }

    public String getPostType() {
        return postType;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static PostType translate(String sType) {
        PostType[] types = PostType.values();
        for (PostType type : types) {
            int index = type.ordinal();
            String val = type.getPostType();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
