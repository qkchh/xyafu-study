package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TabBook {

    @TableId
    private Integer bookId;
    private String name,brief,catalogue,appreciate,photo;
}
