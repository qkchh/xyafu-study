package org.xyafu.four_history.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xyafu.four_history.mapper.LearnDetailMapper;
import org.xyafu.four_history.mapper.TabLearnMapper;
import org.xyafu.four_history.mapper.TabUserLearnMapper;
import org.xyafu.four_history.pojo.Insert;
import org.xyafu.four_history.pojo.LearnDetail;
import org.xyafu.four_history.pojo.TabLearn;
import org.xyafu.four_history.pojo.TabUserLearn;

import java.util.List;

@RestController
@RequestMapping("learn")
public class LearnController {

    @Autowired
    private TabLearnMapper learnMapper;

    @Autowired
    private LearnDetailMapper learnDetailMapper;

    @Autowired
    private TabUserLearnMapper userLearnMapper;

    @PostMapping("insert")
    private void insert(@RequestBody List<Insert> insertList){
        insertList.forEach(System.out::println);
        for (Insert insert : insertList) {
            LearnDetail learnDetail = new LearnDetail();

            learnDetail.setDate(insert.getPubDate());
            learnDetail.setLearnDetailId(0);
            learnDetail.setLink(insert.getLink());
            learnDetail.setThumbnail(insert.getFocusImg());
            learnDetail.setTitle(insert.getTitle());
            learnDetail.setChannelName(insert.getChannelName());
            learnDetail.setLearnId(5);
            learnDetailMapper.insert(learnDetail);
        }
    }

    @GetMapping("getCats")
    public List<TabLearn> getCats(){
        return learnMapper.selectList(null);
    }

    @PostMapping("getPostsById")
    public List<LearnDetail> getPostsById(@RequestBody TabUserLearn userLearn){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("learn_id",userLearn.getLearnId());
        wrapper.orderByDesc("date");
        List<LearnDetail> learnDetailList = learnDetailMapper.selectList(wrapper);
        for (LearnDetail learnDetail : learnDetailList) {
            QueryWrapper<TabUserLearn> tabUserLearnQueryWrapper = new QueryWrapper<>();
            tabUserLearnQueryWrapper.eq("learn_detail_id",learnDetail.getLearnDetailId());
            tabUserLearnQueryWrapper.eq("user_id",userLearn.getUserId());
            if(userLearnMapper.selectOne(tabUserLearnQueryWrapper)!=null){
                learnDetail.setStudy(true);
            }
        }
        return learnDetailList;
    }

    @PostMapping("study")
    public int study(@RequestBody TabUserLearn userLearn){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userLearn.getUserId());
        wrapper.eq("learn_detail_id",userLearn.getLearnDetailId());

        if(userLearnMapper.selectOne(wrapper)!=null){
            return -1;
        }
        TabUserLearn tabUserLearn = new TabUserLearn();
        tabUserLearn.setUserLearnId(0);
        tabUserLearn.setLearnDetailId(userLearn.getLearnDetailId());
        tabUserLearn.setUserId(userLearn.getUserId());
        tabUserLearn.setStudy(true);

        return userLearnMapper.insert(tabUserLearn);
    }
}
