package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TabUserLearn {
    @TableId
    private Integer userLearnId;
    private Boolean study;
    private Integer userId,learnDetailId;
    @TableField(exist = false)
    private Integer learnId;
}
