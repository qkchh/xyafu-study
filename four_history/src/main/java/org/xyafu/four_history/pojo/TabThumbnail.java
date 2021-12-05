package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TabThumbnail {

    @TableId
    private Integer thumbnailId;
    private String image;
    private Integer detailId;
}
