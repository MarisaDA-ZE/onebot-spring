package top.kirisamemarisa.onebotspring.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: MassageUtil.描述
 * @Date: 2024/1/21
 */
public class MassageUtil {

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

    public static void main(String[] args) {
        String s1 = groupTextTemplateSingle("3184397908", "123456");
        System.out.println(s1);
        List<String> strList = new ArrayList<>();
        strList.add("111");
        strList.add("222");
        String s2 = groupTextListTemplate("3184397908", strList);
        System.out.println(s2);
        String s3 = groupVoiceTemplate("3184397908", "http://192.168.10.101:8022/89614f469fbc28f3d33df339ce6f49d4959a8598/audio.wav");
        System.out.println(s3);
        JSONObject js3 = JSONObject.parseObject(s3);
        System.out.println(js3);
    }
}
