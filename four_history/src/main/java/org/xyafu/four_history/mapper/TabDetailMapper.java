package org.xyafu.four_history.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import org.xyafu.four_history.pojo.Posts;
import org.xyafu.four_history.pojo.TabComment;
import org.xyafu.four_history.pojo.TabDetail;

import java.util.List;

@Service
public interface TabDetailMapper extends BaseMapper<TabDetail> {

    @Select("select detail_id,title,comments,views,likes,time,type FROM tab_detail")
    List<Posts> selectPosts();

    @Select("select detail_id,title,comments,views,likes,time,type FROM tab_detail where category_id=#{categoryId}")
    List<Posts> selectPostsByCategoryId(Integer categoryId);

    @Select("select detail_id,title,comments,views,likes,time,type FROM tab_detail where detail_id=#{detailId}")
    Posts selectOnePosts(Integer detailId);

    @Update("update tab_detail set views=#{views} where detail_id=#{detailId}")
    int updateDetailByViews(int views,Integer detailId);

    @Update("update tab_detail set likes=#{likes} where detail_id=#{detailId}")
    int updateDetailByLikes(int likes,Integer detailId);


    @Select("select detail_id,title,comments,views,likes,time,type FROM tab_detail WHERE  title LIKE '%${search}%' ")
    List<Posts> selectPostsByLike(String search);
}
