package org.xyafu.four_history.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xyafu.four_history.mapper.*;
import org.xyafu.four_history.pojo.*;
import org.xyafu.four_history.utils.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("detail")
public class DetailController {

    @Autowired
    private TabDetailMapper detailMapper;
    @Autowired
    private TabUserDetailMapper userDetailMapper;
    @Autowired
    private TabCommentMapper commentMapper;
    @Autowired
    private TabUserMapper userMapper;
    @Autowired
    private TabThumbnailMapper thumbnailMapper;


    @GetMapping("selectPosts")
    public List<Posts> selectPosts() {
        List<Posts> postsList = detailMapper.selectPosts();
        for (Posts posts : postsList) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("detail_id",posts.getDetailId());
            posts.setTabThumbnailList(thumbnailMapper.selectList(wrapper));
        }
        return postsList;
    }

    @PostMapping("selectPostsByCategoryId")
    public List<Posts> selectPostsByCategoryId(@RequestBody TabDetail detail) {
        List<Posts> postsList = detailMapper.selectPostsByCategoryId(detail.getCategoryId());
        for (Posts posts : postsList) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("detail_id",posts.getDetailId());
            posts.setTabThumbnailList(thumbnailMapper.selectList(wrapper));
        }
        return postsList;
    }

    @PostMapping("selectDetail")
    public TabDetail selectDetail(@RequestBody TabDetail detail) {
        QueryWrapper<TabDetail> tabDetailQueryWrapper = new QueryWrapper<>();
        tabDetailQueryWrapper.eq("detail_id", detail.getDetailId());
        TabDetail tabDetail = detailMapper.selectOne(tabDetailQueryWrapper);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("detail_id",tabDetail.getDetailId());
        tabDetail.setTabThumbnailList(thumbnailMapper.selectList(wrapper));
        return tabDetail;
    }

    //更改浏览次数
    @PostMapping("updateDetailByViews")
    public int updateDetailByViews(@RequestBody TabDetail detailId) {
        TabDetail tabDetail = detailMapper.selectById(detailId.getDetailId());
        int views = Integer.parseInt(tabDetail.getViews()) + 1;
        return detailMapper.updateDetailByViews(views, tabDetail.getDetailId());
    }

    @PostMapping("getUserIsLikesIsFav")
    public TabUserDetail getUserIsLikesIsFav(@RequestBody TabUserDetail userDetail) {
        QueryWrapper<TabUserDetail> tabUserDetailQueryWrapper = new QueryWrapper<>();
        tabUserDetailQueryWrapper.eq("detail_id", userDetail.getDetailId());
        tabUserDetailQueryWrapper.eq("user_id", userDetail.getUserId());
        return userDetailMapper.selectOne(tabUserDetailQueryWrapper);
    }

    //    点赞上传数据到数据库
    @PostMapping("insertIsLike")
    public int insertIsLike(@RequestBody TabUserDetail userDetail) {
        QueryWrapper<TabUserDetail> tabUserDetailQueryWrapper = new QueryWrapper<>();
        tabUserDetailQueryWrapper.eq("detail_id", userDetail.getDetailId());
        tabUserDetailQueryWrapper.eq("user_id", userDetail.getUserId());
        TabUserDetail tabUserDetail = userDetailMapper.selectOne(tabUserDetailQueryWrapper);
        TabUserDetail userDetail1 = new TabUserDetail();
        if (tabUserDetail == null) {
            userDetail1.setUserDetailId(0);
            userDetail1.setDetailId(userDetail.getDetailId());
            userDetail1.setUserId(userDetail.getUserId());
            userDetail1.setIslike(true);
//            设置点赞数的增加
            userDetailMapper.insert(userDetail1);
            return updateDetailByLikes(userDetail.getDetailId());
        } else {
            if (tabUserDetail.getIslike() == null) {
                tabUserDetail.setIslike(true);
            } else {
                tabUserDetail.setIslike(!tabUserDetail.getIslike());
            }
            userDetailMapper.updateById(tabUserDetail);
            return updateDetailByLikes(userDetail.getDetailId());
        }
    }

    @PostMapping("insertIsFav")
    public int insertIsFav(@RequestBody TabUserDetail userDetail) {
        QueryWrapper<TabUserDetail> tabUserDetailQueryWrapper = new QueryWrapper<>();
        tabUserDetailQueryWrapper.eq("detail_id", userDetail.getDetailId());
        tabUserDetailQueryWrapper.eq("user_id", userDetail.getUserId());
        TabUserDetail tabUserDetail = userDetailMapper.selectOne(tabUserDetailQueryWrapper);
        TabUserDetail userDetail1 = new TabUserDetail();
        if (tabUserDetail == null) {
            userDetail1.setUserDetailId(0);
            userDetail1.setDetailId(userDetail.getDetailId());
            userDetail1.setUserId(userDetail.getUserId());
            userDetail1.setIsfav(true);
            return userDetailMapper.insert(userDetail1);
        } else {
            if (tabUserDetail.getIsfav() == null) {
                tabUserDetail.setIsfav(true);
            } else {
                tabUserDetail.setIsfav(!tabUserDetail.getIsfav());
            }
            return userDetailMapper.updateById(tabUserDetail);
        }
    }


    //    获取评论
    @PostMapping("getComment")
    public List<TabComment> getComment(@RequestBody TabComment comment) {
        List<TabComment> commentList = commentMapper.selectCommentDetail(comment.getDetailId());
        for (TabComment tabComment : commentList) {
            QueryWrapper<TabComment> tabCommentQueryWrapper = new QueryWrapper<>();
            tabCommentQueryWrapper.eq("reply_id", tabComment.getCommentId());
            List<TabComment> commentList1 = commentMapper.selectList(tabCommentQueryWrapper);
            for (TabComment tabComment1 : commentList1) {
                tabComment1.setUser(userMapper.selectById(tabComment1.getUserId()));
            }
            tabComment.setUser(userMapper.selectById(tabComment.getUserId()));
            tabComment.setTabComments(commentList1);
        }
        return commentList;
    }

    //    添加评论
    @PostMapping("insertComment")
    public List<TabComment> insertComment(@RequestBody TabComment comment) {
        String uuid = UuidUtil.getUuid();
//        这是添加评论
        if (comment.getCommentId() == "") {
            comment.setCommentId(uuid);
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            comment.setDate(ft.format(new Date()));
            comment.setContent(comment.getContent());
            commentMapper.insert(comment);
        } else {
//            这是回复评论
            TabComment tabComment = commentMapper.selectById(comment.getCommentId());
            tabComment.setCommentId(uuid);
            tabComment.setContent(comment.getContent());
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tabComment.setDate(ft.format(new Date()));
            tabComment.setReplyId(comment.getCommentId());
            tabComment.setUserId(comment.getUserId());
            commentMapper.insert(tabComment);
        }
        List<TabComment> commentList = commentMapper.selectCommentDetail(comment.getDetailId());
        for (TabComment tabComment : commentList) {
            QueryWrapper<TabComment> tabCommentQueryWrapper = new QueryWrapper<>();
            tabCommentQueryWrapper.eq("reply_id", tabComment.getCommentId());
            List<TabComment> commentList1 = commentMapper.selectList(tabCommentQueryWrapper);
            for (TabComment tabComment1 : commentList1) {
                tabComment1.setUser(userMapper.selectById(tabComment1.getUserId()));
            }
            tabComment.setUser(userMapper.selectById(tabComment.getUserId()));
            tabComment.setTabComments(commentList1);
        }
        TabDetail tabDetail = detailMapper.selectById(comment.getDetailId());
        Integer comments = Integer.parseInt(tabDetail.getComments()) + 1;
        tabDetail.setComments(comments.toString());
        detailMapper.updateById(tabDetail);
        return commentList;
    }


    //    更改评论数
    public int updateDetailByLikes(Integer detailId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("islike", 1);
        wrapper.eq("detail_id", detailId);
        Integer count = userDetailMapper.selectCount(wrapper);
        return detailMapper.updateDetailByLikes(count, detailId);
    }


    // 获取post信息
    @PostMapping("selectPostsByLike")
    public List<Posts> selectPostsByLike(@RequestBody String search) {
        List<Posts> postsList = detailMapper.selectPostsByLike(search);
        for (Posts posts : postsList) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("detail_id",posts.getDetailId());
            posts.setTabThumbnailList(thumbnailMapper.selectList(wrapper));
        }
        return postsList;
    }

}
