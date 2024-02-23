package top.kirisamemarisa.onebotspring.service.impl.sexes;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexDetail;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.mapper.BotConfigMapper;
import top.kirisamemarisa.onebotspring.mapper.sexes.GroupSexDetailMapper;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexDetailService;

import java.util.List;

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
