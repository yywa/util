package com.yyw.util;


import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author yyw
 * @date 2019-01-04 10:59
 **/
@Slf4j
public class CommonUtil {

    /**
     * 生日校验限制的生日起始时间
     */
    public static final String BIRTHDAY_START_YEAR = "19000101";


    /**
     * 校验生日是否合法
     *
     * @param birthday 出生日期
     */
    public static void checkBirthday(String birthday) {
        // 1900<年份<系统日期年份
        LocalDate today = LocalDate.now();
        LocalDate startYear = LocalDate.parse(BIRTHDAY_START_YEAR, DateTimeFormatter.ofPattern(DateUtil.YYYYMMDD));
        LocalDate birth = null;
        try {
            birth = LocalDate.parse(birthday, DateTimeFormatter.ofPattern(DateUtil.YYYYMMDD));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert birth != null;
        if (birth.isBefore(startYear) || birth.isAfter(today)) {

        }
    }
}
