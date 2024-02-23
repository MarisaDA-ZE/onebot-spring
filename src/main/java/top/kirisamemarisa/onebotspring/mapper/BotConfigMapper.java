package top.kirisamemarisa.onebotspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: BotConfigMapper.描述
 * @Date: 2024/1/23
 */

@Mapper
public interface BotConfigMapper extends BaseMapper<BotConfig> {
}
