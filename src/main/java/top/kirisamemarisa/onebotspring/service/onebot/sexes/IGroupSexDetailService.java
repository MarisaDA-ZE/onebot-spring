package top.kirisamemarisa.onebotspring.service.onebot.sexes;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupSexDetail;

/**
 * @Author: MarisaDAZE
 * @Description: IGroupSexDetailService.描述
 * @Date: 2024/2/21
 */
public interface IGroupSexDetailService extends IService<GroupSexDetail> {

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
