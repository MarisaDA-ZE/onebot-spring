package top.kirisamemarisa.onebotspring.service.onebot.impl.sexes;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupSexDetail;
import top.kirisamemarisa.onebotspring.mapper.onebot.sexes.GroupSexDetailMapper;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupSexDetailService;

/**
 * @Author: MarisaDAZE
 * @Description: GroupSexDetailServiceImpl.描述
 * @Date: 2024/2/21
 */
@Service
public class GroupSexDetailServiceImpl
        extends ServiceImpl<GroupSexDetailMapper, GroupSexDetail>
        implements IGroupSexDetailService {

    @Resource
    private GroupSexDetailMapper groupSexDetailMapper;

    @Override
    public GroupSexDetail getBeforeSexDetail(String groupId, String userId, String wifeId) {
        return groupSexDetailMapper.getBeforeSexDetail(groupId, userId, wifeId);
    }
}
