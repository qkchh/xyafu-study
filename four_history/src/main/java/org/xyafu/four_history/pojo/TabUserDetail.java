package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TabUserDetail {
    @TableId
    private Integer userDetailId;
    private Boolean islike,isfav;
    private Integer detailId,userId;
}
