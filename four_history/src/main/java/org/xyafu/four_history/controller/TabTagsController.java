package org.xyafu.four_history.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyafu.four_history.mapper.TabTagsMapper;
import org.xyafu.four_history.pojo.TabTags;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TabTagsController {

    @Autowired
    private TabTagsMapper tabTagsMapper;

    @GetMapping("getTags")
    private List<TabTags> getTags(){
        return tabTagsMapper.selectList(null);
    }
}
