package top.kirisamemarisa.onebotspring.utils;

import com.alibaba.fastjson.JSONObject;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: MassageUtil.描述
 * @Date: 2024/1/21
 */
public class MassageTemplate {

    /**
     * 快速构建一个对群聊的单一段落的模板
     *
     * @param friendId QQ号
     * @param text     内容
     * @return .
     */
    public static String friendTextTemplateSingle(String friendId, String text) {
        long fid = Long.parseLong(friendId);
        return "{" +
                "\"message_type\": \"private\"," +
                "\"user_id\": " + fid + "," +
                "\"message\": [" +
                "{" +
                "\"type\": \"text\"," +
                "\"data\": {" +
                "\"text\": \"" + text + "\"" +
                "}}]," +
                "\"auto_escape\": false" +
                "}";
    }

    /**
     * 快速构建一个对群聊的单一段落的模板
     *
     * @param groupId 群号
     * @param text    内容
     * @return .
     */
    public static String groupTextTemplateSingle(String groupId, String text) {
        long gid = Long.parseLong(groupId);
        String res = "{" +
                "\"message_type\": \"group\"," +
                "\"group_id\": " + gid + "," +
                "\"message\": [" +
                "{" +
                "\"type\": \"text\"," +
                "\"data\": {" +
                "\"text\": \"" + text + "\"" +
                "}}]}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    /**
     * 快速构建一个对群聊的多段落模板
     *
     * @param groupId  群号
     * @param textList 内容
     * @return .
     */
    public static String groupTextListTemplate(String groupId, List<String> textList) {
        long gid = Long.parseLong(groupId);
        StringBuilder sb = new StringBuilder("[");
        textList.forEach(s -> {
            String str = "{" +
                    "\"type\": \"text\"," +
                    "\"data\": {\"text\": \"" + s + "\"}}";
            sb.append(str).append(",");
        });
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        String res = "{" +
                "\"message_type\": \"group\"," +
                "\"group_id\": " + gid + "," +
                "\"message\": [" + sb + "]}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    /**
     * 快速构建一个对群聊的语音模板
     *
     * @param groupId  群号
     * @param audioURL 内容
     * @return .
     */
    public static String groupVoiceTemplate(String groupId, String audioURL) {
        long gid = Long.parseLong(groupId);
        return "{" +
                "\"message_type\": \"group\"," +
                "\"group_id\": " + gid + "," +
                "\"message\": [" +
                "{" +
                "\"type\": \"voice\"," +
                "\"data\": {" +
                "\"file\": \"" + audioURL + "\"" +
                "}}]," +
                "\"auto_escape\": true" +
                "}";
    }


    /**
     * 快速构建一个对群聊的图片模板
     *
     * @param groupId 群号
     * @param path    图片地址
     * @return .
     */
    public static String groupImageTemplateSingle(String groupId, String path) {
        long gid = Long.parseLong(groupId);
        String res = "{" +
                "\"message_type\": \"group\"," +
                "\"group_id\": " + gid + "," +
                "\"message\": [" +
                "{" +
                "\"type\": \"image\"," +
                "\"data\": {" +
                "\"file\": \"" + path + "\"" +
                "}}]}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    /**
     * 群聊消息模板
     *
     * @param groupId     群号
     * @param massageList 消息列表
     * @return .
     */
    public static String customGroupTemplate(String groupId, List<String> massageList) {
        long gid = Long.parseLong(groupId);
        String res = "{" +
                "\"message_type\": \"group\"," +
                "\"group_id\": " + gid + "," +
                "\"message\": " + massageList.toString() + "}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    /**
     * 群聊消息模板
     *
     * @param groupId  群号
     * @param massages 消息信息
     * @return .
     */
    public static String customGroupTemplate(String groupId, String... massages) {
        List<String> massageList = Arrays.asList(massages);
        return customGroupTemplate(groupId, massageList);
    }


    public static String paragraphTemplate(String content, ContentType type) {
        String res = "{" +
                "\"type\": \"image\"," +
                "\"data\": {" +
                "\"file\": \"\"" +
                "}\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    /**
     * at某人
     *
     * @param qqNum qq号
     * @return .
     */
    public static String paragraphAtTemplate(String qqNum) {
        long qqNo = Long.parseLong(qqNum);
        String res = "{" +
                "\"type\": \"at\"," +
                "\"data\": {\"qq\": \"" + qqNo + "\"}" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    /**
     * 文本段落
     *
     * @param text 文本内容
     * @return .
     */
    public static String paragraphTextTemplate(String text) {
        String res = "{" +
                "\"type\": \"text\"," +
                "\"data\": {\"text\": \"" + text + "\"}" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    /**
     * 图片段落
     *
     * @param url 图片地址
     * @return .
     */
    public static String paragraphImageTemplate(String url) {
        String res = "{" +
                "\"type\": \"image\"," +
                "\"data\": {\"file\": \"" + url + "\"}" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    /**
     * 回复某人
     *
     * @param qqNum 回复对象
     * @return .
     */
    public static String paragraphReplyTemplate(String qqNum) {
        long qqNo = Long.parseLong(qqNum);
        String res = "{" +
                "\"type\": \"reply\"," +
                "\"data\": {\"id\": \"" + qqNo + "\"}" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.toJSONString();
    }

    public static void main(String[] args) {
        String at = paragraphAtTemplate("3038488053");
        String text1 = paragraphTextTemplate(" 你好，这是一条测试消息。");
        String url = "https://img1.baidu.com/it/u=2206336034,3499768820&fm=253&fmt=auto&app=138&f=JPEG?w=952&h=500";
        String image = paragraphImageTemplate(url);
        String text2 = paragraphTextTemplate("这是测试消息的第二个段落");
//        System.out.println("at: " + at);
//        System.out.println("text1: " + text1);
//        System.out.println("image: " + image);
//        System.out.println("text2: " + text2);
        List<String> massageList = Arrays.asList(at, text1, image, text2);
        String s = massageList.toString();
        System.out.println(s);
        List<?> list = JSONObject.parseObject(s, List.class);
        List<String> strings = new ArrayList<>();
        list.forEach(e -> {
            if(e instanceof String) strings.add((String) e);
        });

        System.out.println(list);

//        String massage = customGroupTemplate("701339984", massageList);
//        System.out.println("massage: " + massage);
    }
}
