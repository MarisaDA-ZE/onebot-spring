package top.kirisamemarisa.onebotspring.mapper.mrspixiv;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.kirisamemarisa.onebotspring.entity.mrspixiv.Illustration;
import top.kirisamemarisa.onebotspring.entity.mrspixiv.vo.IllusParams;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: 插画图片映射
 * @Date: 2024/6/25
 */

@Mapper
@DS("mrs-pixiv")
public interface IllustrationMapper extends BaseMapper<Illustration> {

    /**
     * 随机查询一张图
     *
     * @param tags 搜索关键字
     * @param isR18 是否包含r18
     * @return .
     */
    Illustration selectRandomOne(String tags, Integer isR18);

}
