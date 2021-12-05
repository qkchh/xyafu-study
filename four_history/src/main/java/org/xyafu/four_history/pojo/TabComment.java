package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class TabComment {
    @TableId
    private String commentId;
    private String content,date,replyId;
    private Integer userId,detailId;
    @TableField(exist = false)
    private List<TabComment> tabComments;
    @TableField(exist = false)
    private TabUser user;
    @TableField(exist = false)
    private String thumbnail;
}
