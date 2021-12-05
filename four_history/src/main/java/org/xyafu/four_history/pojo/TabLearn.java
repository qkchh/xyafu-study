package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TabLearn {

    @TableId
    private Integer learnId;
    private String name;
}
