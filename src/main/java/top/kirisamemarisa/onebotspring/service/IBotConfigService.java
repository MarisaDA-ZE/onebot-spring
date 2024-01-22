package top.kirisamemarisa.onebotspring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;

/**
 * @Author: MarisaDAZE
 * @Description: IBotConfigService.描述
 * @Date: 2024/1/23
 */
public interface IBotConfigService extends IService<BotConfig> {

    /**
     * 根据用户QQ号、QQ群号查询对应的配置信息
     *
     * @param userId QQ号、Q群号
     * @return 配置信息
     */
    BotConfig getBotConfigByUserId(String userId);
}
