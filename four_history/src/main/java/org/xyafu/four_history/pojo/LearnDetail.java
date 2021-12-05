package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class LearnDetail {

    @TableId
    private Integer learnDetailId;
    private String title,channelName,link,thumbnail,date;
    private Integer learnId;
    @TableField(exist = false)
    private Boolean study;
}
