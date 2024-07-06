package top.kirisamemarisa.onebotspring.service.onebot.impl.sexes;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupSexUser;
import top.kirisamemarisa.onebotspring.mapper.onebot.sexes.GroupSexUserMapper;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupSexUserService;

/**
 * @Author: MarisaDAZE
 * @Description: GroupSexUserServiceImpl.描述
 * @Date: 2024/2/21
 */
@Service
public class GroupSexUserServiceImpl
        extends ServiceImpl<GroupSexUserMapper, GroupSexUser>
        implements IGroupSexUserService {

    @Override
    public GroupSexUser getGroupUserByQQ(String groupId, String userQq) {
        LambdaQueryWrapper<GroupSexUser> sexUserWrapper = new LambdaQueryWrapper<>();
        sexUserWrapper.eq(GroupSexUser::getGroupId, groupId);
        sexUserWrapper.eq(GroupSexUser::getUserQq, userQq);
        return baseMapper.selectOne(sexUserWrapper);
    }
}
