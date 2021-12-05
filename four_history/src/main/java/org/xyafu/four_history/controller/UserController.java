package org.xyafu.four_history.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xyafu.four_history.mapper.*;
import org.xyafu.four_history.pojo.*;
import org.xyafu.four_history.utils.AlibabaOss;
import org.xyafu.four_history.utils.MailUtils;
import org.xyafu.four_history.utils.RandomCode;
import org.xyafu.four_history.utils.UuidUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private TabUserMapper userMapper;
    @Autowired
    private TabUserDetailMapper userDetailMapper;
    @Autowired
    private TabCommentMapper commentMapper;
    @Autowired
    private TabDetailMapper detailMapper;
    @Autowired
    private TabThumbnailMapper thumbnailMapper;

    @PostMapping("login")
    public TabUser login(@RequestBody TabUser user) {
        QueryWrapper<TabUser> tabUserQueryWrapper = new QueryWrapper<>();
        tabUserQueryWrapper.eq("username", user.getUsername());
        if(userMapper.selectOne(tabUserQueryWrapper)==null){
            user.setUserId(-1);
            return user;
        }
        tabUserQueryWrapper.eq("password", user.getPassword());
        TabUser tabUser = userMapper.selectOne(tabUserQueryWrapper);
        return tabUser;
    }

    @PostMapping("register")
    public String register(@RequestBody TabUser user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",user.getUsername());
        if(userMapper.selectOne(wrapper)!=null){
            return "该邮箱已经注册，请尝试登录";
        }
        user.setUserId(0);
        userMapper.insert(user);
        return "注册成功";
    }

    @PostMapping("sendEmail")
    public String sendEmail(@RequestBody String email){
        String code = RandomCode.createCode();
       if(MailUtils.sendEmail(email, code)){
           return code;
       }
       return "error";
    }


    @PostMapping("updateUserData")
    public TabUser updateUserData(@RequestBody TabUser user) {
        TabUser tabUser = userMapper.selectById(user.getUserId());
        if(user.getNickName()!="") {
            tabUser.setNickName(user.getNickName());
        }
        if(user.getDescription()!=""){
            tabUser.setDescription(user.getDescription());
        }
        userMapper.updateById(tabUser);
        return tabUser;
    }

    //    获取点赞的作品数
    @PostMapping("selectCount")
    public Account selectCount(@RequestBody TabUser user) {
        Account account = new Account();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("islike", 1);
        wrapper.eq("user_id", user.getUserId());
        account.setLikes(userDetailMapper.selectCount(wrapper));
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("isfav", 1);
        wrapper1.eq("user_id", user.getUserId());
        account.setFavs(userDetailMapper.selectCount(wrapper1));
        QueryWrapper<TabComment> tabCommentQueryWrapper = new QueryWrapper<>();
        tabCommentQueryWrapper.eq("user_id", user.getUserId());
        account.setComments(commentMapper.selectCount(tabCommentQueryWrapper));
        return account;
    }

    //    获取我的喜欢内容
    @PostMapping("getMyLikes")
    public List<Posts> getMyLikes(@RequestBody TabUser user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("islike", 1);
        wrapper.eq("user_id", user.getUserId());
        List<TabUserDetail> userDetails = userDetailMapper.selectList(wrapper);
        List<Posts> postsList = new ArrayList<>();
        for (TabUserDetail userDetail : userDetails) {
            Posts posts = detailMapper.selectOnePosts(userDetail.getDetailId());
            QueryWrapper<TabThumbnail> tabThumbnailQueryWrapper = new QueryWrapper<>();
            tabThumbnailQueryWrapper.eq("detail_id",posts.getDetailId());
            posts.setTabThumbnailList(thumbnailMapper.selectList(tabThumbnailQueryWrapper));
            postsList.add(posts);
        }
        return postsList;
    }

    //    获取我的收藏内容
    @PostMapping("getMyCollect")
    public List<Posts> getMyCollect(@RequestBody TabUser user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("isfav", 1);
        wrapper.eq("user_id", user.getUserId());
        List<TabUserDetail> userDetails = userDetailMapper.selectList(wrapper);
        List<Posts> postsList = new ArrayList<>();
        for (TabUserDetail userDetail : userDetails) {
            Posts posts = detailMapper.selectOnePosts(userDetail.getDetailId());
            QueryWrapper<TabThumbnail> tabThumbnailQueryWrapper = new QueryWrapper<>();
            tabThumbnailQueryWrapper.eq("detail_id",posts.getDetailId());
            posts.setTabThumbnailList(thumbnailMapper.selectList(tabThumbnailQueryWrapper));
            postsList.add(posts);
        }
        return postsList;
    }

    //    获取我的评论内容
    @PostMapping("getMyComment")
    public List<TabComment> getMyComment(@RequestBody TabUser user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getUserId());
        List<TabComment> commentList = commentMapper.selectList(wrapper);
        TabUser me=userMapper.selectById(user.getUserId());
        for (TabComment tabComment : commentList) {
            tabComment.setUser(me);
            QueryWrapper<TabThumbnail> tabThumbnailQueryWrapper = new QueryWrapper<>();
            tabThumbnailQueryWrapper.eq("detail_id",tabComment.getDetailId()).last("limit 1");
            TabThumbnail tabThumbnail = thumbnailMapper.selectOne(tabThumbnailQueryWrapper);
            tabComment.setThumbnail(tabThumbnail.getImage());
        }
        return commentList;
    }

//    删除评论
    @PostMapping("deleteComment")
    public List<TabComment> deleteComment(@RequestBody String commentId){
        TabComment tabComment = commentMapper.selectById(commentId);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("reply_id",commentId);
        int delete = commentMapper.delete(wrapper);
        TabDetail tabDetail = detailMapper.selectById(tabComment.getDetailId());
        int result=Integer.parseInt(tabDetail.getComments())-delete-1;
        tabDetail.setComments(String.valueOf(result));
        commentMapper.deleteById(commentId);
        detailMapper.updateById(tabDetail);
        TabUser user = new TabUser();
        user.setUserId(tabComment.getUserId());
        return getMyComment(user);
    }


//    上传头像
    @PostMapping("uploadHeadPortrait")
    public TabUser uploadHeadPortrait(@RequestPart("headPortrait") MultipartFile headPortrait, @RequestParam("userId") String userId){
//        https://wallpaper-one.oss-cn-beijing.aliyuncs.com/head/headportrait.png
        if(!headPortrait.isEmpty()){
            TabUser tabUser = userMapper.selectById(userId);
            String imageName=UuidUtil.getUuid()+"."+headPortrait.getContentType().split("/")[1];
            try {
                // 创建临时文件
                File file = null;
                try {
                    file = File.createTempFile("tmp", null);
                    headPortrait.transferTo(file);
                    file.deleteOnExit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AlibabaOss.uploadImages(file,imageName);
                tabUser.setAvatarUrl("https://wallpaper-one.oss-cn-beijing.aliyuncs.com/head/"+imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
             userMapper.updateById(tabUser);
            return tabUser;
        }
        return null;
    }


//    @PostMapping("getUserById")
//    public String getUserById(@RequestBody TabUser user){
//
//    }

    @PostMapping("updatePassword")
    public String updatePassword(@RequestBody TabUser user){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",user.getUsername());
        TabUser tabUser = userMapper.selectOne(wrapper);
        if(tabUser==null){
            return "邮箱还未注册，请先注册";
        }
        tabUser.setPassword(user.getPassword());
        userMapper.updateById(tabUser);
        return "修改成功";
    }
}
