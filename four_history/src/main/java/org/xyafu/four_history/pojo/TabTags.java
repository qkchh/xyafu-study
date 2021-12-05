package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TabTags {
    @TableId
    private Integer tagId;
    private String name;
}
