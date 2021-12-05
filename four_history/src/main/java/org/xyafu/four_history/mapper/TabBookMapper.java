package org.xyafu.four_history.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.xyafu.four_history.pojo.BookSummary;
import org.xyafu.four_history.pojo.TabBook;

import java.util.List;

@Service
public interface TabBookMapper extends BaseMapper<TabBook> {

    @Select("select book_id,name,brief,photo from tab_book")
    List<BookSummary> selectAllBookSummary();
}
