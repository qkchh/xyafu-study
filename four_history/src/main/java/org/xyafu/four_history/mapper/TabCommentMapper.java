package org.xyafu.four_history.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.xyafu.four_history.pojo.TabComment;

import java.util.List;

@Service
public interface TabCommentMapper extends BaseMapper<TabComment> {
    @Select("SELECT * from tab_comment WHERE  ISNULL(reply_id) ORDER BY date desc")
    List<TabComment> selectComment();

    @Select("SELECT * from tab_comment WHERE detail_id=#{detail_id} and  ISNULL(reply_id) ORDER BY date desc")
    List<TabComment> selectCommentDetail(int detail_id);
}
