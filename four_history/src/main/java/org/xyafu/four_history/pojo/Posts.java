package org.xyafu.four_history.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Posts {
    private Integer detailId;
    private String title,comments,views,likes,time;
    private Boolean type;
    @TableField(exist = false)
    private List<TabThumbnail> tabThumbnailList;
}
