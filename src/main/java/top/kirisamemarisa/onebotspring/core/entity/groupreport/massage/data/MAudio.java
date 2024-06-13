package top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.annotation.MessageField;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;

/**
 * @Author: MarisaDAZE
 * @Description: 语音信息
 * <p>语音消息内容存在问题，目前是unknown</p>
 * <p>data（本类）是空对象，以下是推测会有的字段</p>
 * @Date: 2024/2/15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class MAudio extends MData {

    // 文件ID
    @JSONField(name = "file_id")
    @MessageField("file_id")
    private String fileId;

    // 文件路径（磁盘绝对路径）C:\\...
    @JSONField(name = "path")
    @MessageField("path")
    private String path;

    // 文件路径（本地web访问）file:///C:\\...
    @JSONField(name = "file")
    @MessageField("file")
    private String file;

    // 不知道这个字段有没有
    @JSONField(name = "voice")
    @MessageField("voice")
    private String voice;
}
