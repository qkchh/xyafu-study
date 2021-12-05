package org.xyafu.four_history.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xyafu.four_history.mapper.CategoryDetailMapper;
import org.xyafu.four_history.mapper.TabCategoryMapper;
import org.xyafu.four_history.pojo.CategoryDetail;
import org.xyafu.four_history.pojo.TabCategory;

import java.util.List;

@RestController
@RequestMapping("cats")
public class CategoryController {

    @Autowired
    private TabCategoryMapper categoryMapper;

    @Autowired
    private CategoryDetailMapper categoryDetailMapper;


    @GetMapping("getCatsData")
    public List<TabCategory> getCatsData(){
        return categoryMapper.selectList(null);
    }


    @PostMapping("getIntroduceData")
    public CategoryDetail getIntroduceData(@RequestBody String categoryDetailId){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("category_id",categoryDetailId);
        return categoryDetailMapper.selectOne(wrapper);
    }

}
