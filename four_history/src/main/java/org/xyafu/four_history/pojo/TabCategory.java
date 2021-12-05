package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TabCategory {
    @TableId
    private Integer categoryId;
    private String name;
    private Boolean except;
}
