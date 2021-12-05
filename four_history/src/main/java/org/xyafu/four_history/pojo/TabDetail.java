package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class TabDetail {

    @TableId
    private Integer detailId;
    private String title,author,views,content,likes,comments,time;
    private Integer categoryId;
    private Boolean type;
    @TableField(exist = false)
    private List<TabThumbnail> tabThumbnailList;

}
