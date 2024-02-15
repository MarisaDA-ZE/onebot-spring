package top.kirisamemarisa.onebotspring.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;
import top.kirisamemarisa.onebotspring.service.IBotService;
import top.kirisamemarisa.onebotspring.service.IScheduledTaskService;
import top.kirisamemarisa.onebotspring.utils.BertUtils;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;

import java.util.*;

import static top.kirisamemarisa.onebotspring.common.Constant.CONFIG_SUFFIX;

/**
 * @Author: MarisaDAZE
 * @Description: BotServiceImpl.描述
 * @Date: 2024/1/21
 */
@Component
public class BotServiceImpl implements IBotService {
    @Resource
    private IBotConfigService botConfigService;

    @Resource
    private IScheduledTaskService scheduledTaskService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${mrs-bot.bv2.client}")
    private String bv2URL;   // 机器人ID

    @Value("${mrs-bot.bv2.model-name}")
    private String audioModelName;// 语音模型名称

    @Value("${mrs-bot.bv2.audio-service}")
    private String audioFileURL;// 模型生成的语音文件的路径

    @Value("${mrs-bot.other-api.sex-image-url}")
    private String sexURL_AllAge;  // 色图接口（正常向）

    private Integer temperature = -1;// 空调温度


    @Override
    public void mainFun(JSONObject data) {
        String messageType = data.getString("message_type");
        JSONArray messageList = data.getJSONArray("message");

        String botId = data.getString("self_id");
        BotConfig config = null;
        if ("group".equals(messageType)) {  // 群聊消息
            String sourceId = data.getString("group_id");
            try {
                config = (BotConfig) redisTemplate.opsForValue().get(sourceId + CONFIG_SUFFIX);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ObjectUtils.isEmpty(config)) config = botConfigService.getBotConfigByTargetId(sourceId);
            if (ObjectUtils.isEmpty(config)) {
                System.out.println("当前Q群暂无任何配置,正在新建默认配置...");
                config = botConfigService.createConfig(botId, sourceId, messageType);
            }

            String targetGroup = config.getUserId();
            String clientURL = config.getClientUrl();


            // 群聊只监听at机器人的消息
            boolean atFlag = false;
            System.out.println("消息列表: " + messageList);
            String sender = data.getString("user_id");
            if (botId.equals(sender)) {
                System.out.println("机器人发送消息，不必管它...");
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
                if ("测试".equals(context)) {
                    String s = "测试消息,目标Q群: " + targetGroup;
                    String template = MassageTemplate.groupTextTemplateSingle(targetGroup, s);
                    HttpUtils.post(url, template);
                    return;
                }
                if (context.contains("来点骚话")) {
                    System.out.println("发送一句骚话...");
                    String template = MassageTemplate.groupTextTemplateSingle(targetGroup, saoStr);
                    System.out.println(template);
//                    HttpUtils.post(url, template);
                } else if (context.contains("来点骚话语音") || context.contains("来点语音骚话")) {
                    sendAudioMassage(clientURL, targetGroup, saoStr);
                } else if (context.contains("复读：") || context.contains("复读:")) {
                    String cmd = context.substring(3);
                    sendAudioMassage(clientURL, targetGroup, cmd);
                } else if (context.contains("复读文本：") || context.contains("复读文本:")) {
                    String cmd = context.substring(5);
                    String template = MassageTemplate.groupTextTemplateSingle(targetGroup, cmd);
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
                    sendAudioMassage(clientURL, targetGroup, con);
                } else if (context.contains("查询温度")) {
                    String con = "当前群空调温度为" + temperature + "度。";
                    if (temperature == -1) {
                        con = "请先打开空调。";
                    }
                    sendAudioMassage(clientURL, targetGroup, con);
                } else if (context.contains("开空调")) {
                    String con = "成功开启空调，默认温度为26度。";
                    temperature = 26;
                    sendAudioMassage(clientURL, targetGroup, con);
                } else if (context.contains("关空调")) {
                    String con = "已成功关闭空调。";
                    temperature = -1;
                    sendAudioMassage(clientURL, targetGroup, con);
                } else if (context.contains("来点二次元")) {
                    System.out.println("发送随机二次元图片...");
                    System.out.println(context);
                    String template = MassageTemplate.groupImageTemplateSingle(targetGroup, sexURL_AllAge);
                    HttpUtils.post(url, template);
                } else if (context.contains("创建定时任务")) {
                    int i = context.indexOf('：');
                    String str = context.substring(i + 1);
                    String[] split = str.split("&");
                    String cron = "";
                    String template = "";
                    String remark = "";
                    for (String s : split) {
                        String[] array = s.split("=");
                        System.out.println("array: " + Arrays.toString(array));
                        if ("cron".equals(array[0])) cron = array[1];
                        if ("template".equals(array[0])) template = array[1];
                        if ("remark".equals(array[0])) remark = array[1];
                    }
                    String[] templates = template.split(",");
                    List<String> msgList = new ArrayList<>();
                    for (String tmp : templates) {
                        String s = MassageTemplate.paragraphTextTemplate(tmp);
                        msgList.add(s);
                    }
                    String img = "https://img1.baidu.com/it/u=2206336034,3499768820&fm=253&fmt=auto&app=138&f=JPEG?w=952&h=500";
                    String image = MassageTemplate.paragraphImageTemplate(img);
                    msgList.add(image);
                    System.out.println("cron: " + cron);
                    boolean created = scheduledTaskService.createTextTask(cron, sender, targetGroup, msgList.toString(), remark);
                    String msg = MassageTemplate.groupTextTemplateSingle(targetGroup, "定时任务创建成功...");
                    if (created) HttpUtils.post(url, msg);
                } else if (context.contains("启动定时任务")) {
                    scheduledTaskService.startTextTaskByUserId(sender);
                    String msg = MassageTemplate.groupTextTemplateSingle(targetGroup, "定时任务启动成功...");
                    // HttpUtils.post(url, msg);
                } else if (context.contains("帮助") || context.contains("help")) {
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
                    String template = MassageTemplate.groupTextTemplateSingle(targetGroup, s);
                    System.out.println("模板: "+template);
                    HttpUtils.post(url, template);
                }

            }

        }
        // friend、other
        // 暂时处理不了私聊和朋友消息，因为获取不到发送者QQ号
        // else if ("friend".equals(messageType)) {// 朋友私聊
        //     String sourceId = data.getString("group_id");
        //     try {
        //         config = (BotConfig) redisTemplate.opsForValue().get(sourceId + CONFIG_SUFFIX);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        //     if (ObjectUtils.isEmpty(config)) {
        //         config = botConfigService.getBotConfigByTargetId(sourceId);
        //     }
        //     if (ObjectUtils.isEmpty(config)) {
        //         System.out.println("当前Q群暂无任何配置,正在新建默认配置...");
        //         config = botConfigService.createConfig(botId, sourceId, messageType);
        //     }
        //     System.out.println("配置信息: " + config);
        //     String targetGroup = config.getUserId();
        //     String clientURL = config.getClientUrl();
        //     String url = clientURL + "/send_msg";
        //     String text = "我只是个机器人，请不要私信我。";
        //      String template = MassageUtil.friendTextTemplateSingle(targetGroup, text);
        //      HttpUtils.post(url, template);
        // }
    }

    /**
     * 固定模板，将文本转为语音发送到对应的群聊
     *
     * @param groupId 群聊地址
     * @param text    文本
     */
    private void sendAudioMassage(String clientURL, String groupId, String text) {
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
                String audioTemplate = MassageTemplate.groupVoiceTemplate(groupId, audioFileURL + urlPath);
                System.out.println(audioTemplate);
                HttpUtils.post(cURL, audioTemplate);
            }
        }
    }
}
