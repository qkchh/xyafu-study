package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CategoryDetail {

    @TableId
    private Integer categoryDetailId;
    private String title,content;
    private Integer categoryId;
}
