package top.kirisamemarisa.onebotspring.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.service.IBotService;
import top.kirisamemarisa.onebotspring.utils.BertUtils;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: MarisaDAZE
 * @Description: BotServiceImpl.描述
 * @Date: 2024/1/21
 */
@Service
public class BotServiceImpl implements IBotService {

    @Value("${mrs-bot.bot-id}")
    private String botId;   // 机器人ID

    @Value("${mrs-bot.client-url}")
    private String clientURL;   // 机器人ID

    @Value("${mrs-bot.bv2-url}")
    private String bv2URL;   // 机器人ID
    @Value("${mrs-bot.target-group}")
    private String targetGroup;   // 目标群号

    @Value("${mrs-bot.bert-vits2-model}")
    private String audioModelName;// 语音模型名称

    @Value("${mrs-bot.bv2-audio-url}")
    private String audioFileURL;// 模型生成的语音文件的路径

    @Value("${mrs-bot.other-api.sex-image-url}")
    private String sexURL_AllAge;  // 色图接口（正常向）

    private Integer temperature = -1;// 空调温度

    @Override
    public void mainFun(JSONObject data) {
        String messageType = data.getString("message_type");
        JSONArray messageList = data.getJSONArray("message");
        if ("group".equals(messageType)) {  // 群聊消息
            // 群聊只监听at机器人的消息
            boolean atFlag = false;
            System.out.println("消息列表: " + messageList);
            String sender = data.getString("user_id");
            if (botId.equals(sender)) {
                System.out.println("机器人回调，不必管它...");
                return;
            }

            for (Object msg : messageList) {
                Map<String, Object> massage = (Map) msg;
                String mType = (String) massage.get("type");
                if ("at".equals(mType)) {
                    Map<String, String> mContent = (Map) massage.get("data");
                    String target = mContent.get("mention");
                    if (botId.equals(target)) {
                        atFlag = true;
                        break;
                    }
                }
            }

            if (atFlag) {
                StringBuilder sb = new StringBuilder();
                for (Object x : messageList) {
                    Map<String, Object> massage = (Map) x;
                    String mType = (String) massage.get("type");
                    if ("text".equals(mType)) {
                        Map<String, String> mContent = (Map) massage.get("data");
                        System.out.println(mContent);
                        sb.append(mContent.get("text"));
                    }
                }
                String context = (sb.toString()).trim();
                System.out.println("somebody @me content is:" + context);
                String url = clientURL + "/send_msg";
                String saoStr = "这是场漫长的旅途，但很快，我们就都将抵达各自的终点。";
                if (context.contains("来点骚话")) {
                    System.out.println("发送一句骚话...");
                    String template = MassageUtil.groupTextTemplateSingle(targetGroup, saoStr);
                    HttpUtils.post(url, template);
                } else if (context.contains("来点骚话语音") || context.contains("来点语音骚话")) {
                    sendAudioMassage(targetGroup, saoStr);
                } else if (context.contains("复读：") || context.contains("复读:") ) {
                    String cmd = context.substring(3);
                    sendAudioMassage(targetGroup, cmd);
                } else if (context.contains("复读文本：") || context.contains("复读文本:") ) {
                    String cmd = context.substring(5);
                    String template = MassageUtil.groupTextTemplateSingle(targetGroup, cmd);
                    HttpUtils.post(url, template);
                } else if (context.contains("设置温度")) {
                    String tpt = context.substring("设置温度".length());
                    String con;
                    try {
                        int i = Integer.parseInt(tpt);
                        if (temperature == -1) {
                            con = "请先开启空调哦~";
                        } else if (i == 114514) {
                            con = "这空调怎么这么臭？";
                        } else if (i < 5 || i > 40) {
                            con = "请设置正确的空调温度（支持5至40度的空调温度）";
                        } else {
                            temperature = i;
                            con = "成功设置群空调温度为" + i + "度。";
                        }
                    } catch (Exception e) {
                        con = "请设置正确的空调温度。";
                    }
                    sendAudioMassage(targetGroup, con);
                } else if (context.contains("查询温度")) {
                    String con = "当前群空调温度为" + temperature + "度。";
                    if (temperature == -1) {
                        con = "请先打开空调。";
                    }
                    sendAudioMassage(targetGroup, con);
                } else if (context.contains("开空调")) {
                    String con = "哔~，成功开启空调，默认温度为26度。";
                    temperature = 26;
                    sendAudioMassage(targetGroup, con);
                } else if (context.contains("关空调")) {
                    String con = "已成功关闭空调。";
                    temperature = -1;
                    sendAudioMassage(targetGroup, con);
                } else if (context.contains("来点二次元")) {
                    System.out.println("发送随机二次元图片...");
                    System.out.println(context);
                    String template = MassageUtil.groupImageTemplateSingle(targetGroup, sexURL_AllAge);
                    HttpUtils.post(url, template);
                } else if (context.contains("帮助") || context.contains("help") ) {
                    System.out.println("帮助...");
                    String s =
                            """
                                    帮助菜单
                                    ============
                                    @我 <命令><参数>即可使用。
                                    -发骚话（文本）：@我 来点骚话
                                    -发骚话（语音）：@我 来点语音骚话
                                    -复读机（语音）：@我 复读：<内容>
                                    -复读机（文本）：@我 复读文本：<内容>
                                    -随机图片：@我 来点二次元
                                    -开空调：@我 开空调
                                    -闭空调：@我 关空调
                                    -设置温度：@我 设置温度<温度>
                                    -查询温度：@我 查询温度
                                    -帮助：@我 帮助
                                    -帮助：@我 help
                                    ============""";
                    System.out.println(s);
                    String template = MassageUtil.groupTextTemplateSingle(targetGroup, s);
                    HttpUtils.post(url, template);
                }

            }

        } else if ("friend".equals(messageType)) {// 朋友私聊
            String url = clientURL + "/send_msg";
            String text = "我只是个机器人，请不要私信我。";
            // String template = MassageUtil.friendTextTemplateSingle(targetGroup, text);
            // HttpUtils.post(url, template);
        }
    }

    /**
     * 固定模板，将文本转为语音发送到对应的群聊
     *
     * @param groupId 群聊地址
     * @param text    文本
     */
    private void sendAudioMassage(String groupId, String text) {
        System.out.println("发送语音消息...");
        String cURL = clientURL + "/send_msg";
        String bertURL = bv2URL + "/run/predict";
        String saoText = BertUtils.bertDefaultTemplate(text, audioModelName);
        String res = HttpUtils.post(bertURL, saoText);
        System.out.println("推理返回值: ");
        System.out.println(res);
        JSONObject resObject = JSONObject.parseObject(res);
        if (!ObjectUtils.isEmpty(resObject)) {
            JSONArray dataArray = resObject.getJSONArray("data");
            if ("Success".equals(dataArray.getString(0))) {
                JSONObject pathObject = dataArray.getJSONObject(1);
                String fileAbsolutePath = pathObject.getString("name");
                System.out.println(fileAbsolutePath);
                String urlPath = fileAbsolutePath.substring(fileAbsolutePath.length() - 50);
                urlPath = "/" + urlPath.replaceAll("\\\\", "/");
                String audioTemplate = MassageUtil.groupVoiceTemplate(groupId, audioFileURL + urlPath);
                HttpUtils.post(cURL, audioTemplate);
            }
        }
    }
}
