package com.shmily.summary.vo;

import lombok.Data;

/**
 * 最大值最小值VO
 * create by kevin.ma on 2019/8/11 上午10:59
 **/
@Data
public class MaxMinVO<T extends Comparable> {

    private T min;
    private T max;
}
