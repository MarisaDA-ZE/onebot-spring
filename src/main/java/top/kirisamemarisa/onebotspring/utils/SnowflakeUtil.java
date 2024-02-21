package top.kirisamemarisa.onebotspring.utils;

import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: MarisaDAZE
 * @Description: 雪花ID工具类
 * @Date: 2024/1/23
 */
public class SnowflakeUtil {
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private static final Random random = new Random();

    // 实例
    private static SnowflakeUtil instance;

    private SnowflakeUtil(long workerId, long datacenterId) {
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 生成雪花ID
     *
     * @return .
     */
    public synchronized String next() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        long twepoch = 1288834974657L;
        long datacenterIdShift = sequenceBits + workerIdBits;
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        long l = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << sequenceBits) | sequence;
        return String.valueOf(l);
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 生成一个雪花ID
     *
     * @return .
     */
    public static String nextId() {
        if (ObjectUtils.isEmpty(instance)) {
            int i1 = random.nextInt(31);
            int i2 = random.nextInt(31);
            instance = new SnowflakeUtil(i1, i2);
        }
        return instance.next();
    }

    public static void main(String[] args) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String id = nextId();
            if (ids.contains(id)) {
                System.out.println("出现重复的了...");
            } else {
                ids.add(id);
            }
        }
    }
}