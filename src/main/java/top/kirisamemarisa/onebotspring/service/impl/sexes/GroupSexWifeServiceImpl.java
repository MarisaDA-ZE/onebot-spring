package top.kirisamemarisa.onebotspring.service.impl.sexes;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.mapper.sexes.GroupSexWifeMapper;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexWifeService;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: GroupSexWifeServiceImpl.描述
 * @Date: 2024/2/21
 */
@Service
public class GroupSexWifeServiceImpl
        extends ServiceImpl<GroupSexWifeMapper, GroupSexWife>
        implements IGroupSexWifeService {


    @Override
    public List<GroupSexWife> getGroupWifeByQQ(String groupId, String userQq, String name) {
        LambdaQueryWrapper<GroupSexWife> sexWifeWrapper = new LambdaQueryWrapper<>();
        sexWifeWrapper.eq(GroupSexWife::getGroupId, groupId);
        sexWifeWrapper.eq(GroupSexWife::getUserQq, userQq);
        sexWifeWrapper.eq(GroupSexWife::getWifeQq, name)
                .or()
                .like(GroupSexWife::getLoveName, name);
        return baseMapper.selectList(sexWifeWrapper);
    }
}
