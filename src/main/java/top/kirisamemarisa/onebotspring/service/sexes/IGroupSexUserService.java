package top.kirisamemarisa.onebotspring.service.sexes;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexUser;

/**
 * @Author: MarisaDAZE
 * @Description: IGroupSexUserService.描述
 * @Date: 2024/2/21
 */
public interface IGroupSexUserService extends IService<GroupSexUser> {

    /**
     * 获取用户
     *
     * @param groupId 群号
     * @param userQq  QQ号
     * @return 封装后的授权用户
     */
    GroupSexUser getGroupUserByQQ(String groupId, String userQq);

}
