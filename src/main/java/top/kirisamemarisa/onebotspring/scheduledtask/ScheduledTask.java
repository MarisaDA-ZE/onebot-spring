package top.kirisamemarisa.onebotspring.scheduledtask;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.common.Constant;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexUser;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupWife;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexUserService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupWifeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: MarisaDAZE
 * @Description: 定时任务
 * @Date: 2024/2/21
 */
@Component
public class ScheduledTask {


    @Resource
    private IGroupWifeService groupWifeService;

    @Resource
    private IGroupSexUserService groupSexUserService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedRate = Constant.MILLIS_15) // 每隔15分钟执行一次
    public void fixedRateTask() {
        System.out.println("补充精力...");
        List<GroupWife> wifeList = groupWifeService.list();
        List<GroupSexUser> sexUserList = groupSexUserService.list();
        int recoverStep = 15;
        int maxEnergy = 100;

        // 回复群老婆的精力值
        List<GroupWife> wifeListUpdate = new ArrayList<>();
        wifeList.forEach(e -> {
            GroupWife groupWife = new GroupWife();
            groupWife.setId(e.getId());
            int energy = e.getRemainingEnergy();
            energy += recoverStep;
            if (energy > maxEnergy) energy = maxEnergy;
            groupWife.setRemainingEnergy(energy);
            wifeListUpdate.add(groupWife);
        });

        // 回复自己的精力值
        List<GroupSexUser> sexUserListUpdate = new ArrayList<>();
        sexUserList.forEach(e -> {
            GroupSexUser groupSexUser = new GroupSexUser();
            groupSexUser.setId(e.getId());
            int energy = e.getRemainingEnergy();
            energy += recoverStep;
            if (energy > maxEnergy) energy = maxEnergy;
            groupSexUser.setRemainingEnergy(energy);
            sexUserListUpdate.add(groupSexUser);
        });
        Date current = new Date();
        redisTemplate.opsForValue().set("last_recover_energy_time", current, Constant.MILLIS_15, TimeUnit.MILLISECONDS);
        // 更新（只更新精力字段）
        if (wifeListUpdate.size() > 0) groupWifeService.updateBatchById(wifeListUpdate);
        if (sexUserListUpdate.size() > 0) groupSexUserService.updateBatchById(sexUserListUpdate);
    }
}
