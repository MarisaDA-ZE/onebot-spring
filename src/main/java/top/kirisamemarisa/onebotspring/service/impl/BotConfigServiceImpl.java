package top.kirisamemarisa.onebotspring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.mapper.BotConfigMapper;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;

/**
 * @Author: MarisaDAZE
 * @Description: BotConfigServiceImpl.描述
 * @Date: 2024/1/23
 */
@Service
public class BotConfigServiceImpl extends ServiceImpl<BotConfigMapper, BotConfig> implements IBotConfigService {

    @Resource
    private BotConfigMapper botConfigMapper;

    @Override
    public BotConfig getBotConfigByUserId(String userId) {
        QueryWrapper<BotConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID", userId);
        BotConfig config = botConfigMapper.selectOne(queryWrapper);
        System.out.println("数据库中的配置信息: " + config);
        IPage<BotConfig> p = new Page<>(1,10);
        IPage<BotConfig> page = baseMapper.selectPage(p, new QueryWrapper<>());
        System.out.println(page);

        if (ObjectUtils.isEmpty(config)) return null;
        return config;
    }
}
