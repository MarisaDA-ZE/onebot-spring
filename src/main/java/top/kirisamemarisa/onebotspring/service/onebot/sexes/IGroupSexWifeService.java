package top.kirisamemarisa.onebotspring.service.onebot.sexes;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupSexWife;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: IGroupSexWifeService.描述
 * @Date: 2024/2/21
 */
public interface IGroupSexWifeService extends IService<GroupSexWife> {

    /**
     * 根据群老婆QQ、爱称、群号和用户QQ查询符合条件的群老婆
     *
     * @param groupId 群号
     * @param userQq  用户QQ
     * @param name    爱称/群老婆QQ
     * @return 群老婆列表
     */
    List<GroupSexWife> getGroupWifeByQQ(String groupId, String userQq, String name);
}
