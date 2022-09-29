package com.open.project.batch;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 16:16
 * @Description
 */
@TableName("student")
@Data
public class Student {

    private Integer id;
    private String teacher;

}
