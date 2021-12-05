package org.xyafu.four_history.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.InputStream;

@Data
public class TabUser {

    @TableId
    private Integer userId;
    private String username,password,description,nickName,avatarUrl;
}
