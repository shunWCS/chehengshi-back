package com.dingguan.cheHengShi.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * 用于生成订单号
 *
 * @author carfield
 */
public class Sequences {

    private static final Integer MAX_POSITION = 99999;
    private static final int SEQUENCE_LEN = 5;

    private static Long lastTimeOffset = 0L;
    private static Integer position = 0;

    private final static Long OFFSET = 1356969600000L;

    private static char clusterId;

    static {
        clusterId = '1';
    }

    public synchronized static String get() {
        StringBuilder sb = new StringBuilder();
        Long curTime = getCurrentTimeOffset();
        sb.append(clusterId).append(curTime).append(getCurPosition(curTime));
        return sb.toString();
    }

    /**
     * 返回在当前时间(秒)范围内的序列号
     *
     * @param timeOffsetSection 当前时间偏移量，精确到秒
     */
    private static String getCurPosition(Long timeOffsetSection) {
        Integer curPos;
        if (timeOffsetSection.equals(lastTimeOffset)) {
            if (++position > MAX_POSITION) {
                position = 1;
            }
        } else {
            lastTimeOffset = timeOffsetSection;
            position = 1;
        }
        curPos = position;
        return StringUtils.leftPad(curPos.toString(), SEQUENCE_LEN, '0');
    }

    private static Long getCurrentTimeOffset() {
        return (System.currentTimeMillis() - OFFSET) / 1000;
    }




}