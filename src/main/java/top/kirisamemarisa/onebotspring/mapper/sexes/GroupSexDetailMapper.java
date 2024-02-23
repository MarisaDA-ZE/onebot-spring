package top.kirisamemarisa.onebotspring.mapper.sexes;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexDetail;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: GroupSexDetailMapper.描述
 * @Date: 2024/2/23
 */

@Mapper
public interface GroupSexDetailMapper extends BaseMapper<GroupSexDetail> {

    /**
     * 获取上一次涩涩的记录
     *
     * @param groupId 群号
     * @param userId  操作者ID
     * @param wifeId  老婆ID
     * @return .
     */
    GroupSexDetail getBeforeSexDetail(String groupId, String userId, String wifeId);
}
