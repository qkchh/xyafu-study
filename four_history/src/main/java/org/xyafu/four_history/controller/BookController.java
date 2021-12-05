package org.xyafu.four_history.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xyafu.four_history.mapper.TabBookMapper;
import org.xyafu.four_history.pojo.BookSummary;
import org.xyafu.four_history.pojo.TabBook;

import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private TabBookMapper bookMapper;

    @GetMapping("selectAllBookSummary")
    public List<BookSummary> selectAllBookSummary(){
        return bookMapper.selectAllBookSummary();
    }

    @PostMapping("getBookDetail")
    public TabBook getBookDetail(@RequestBody String bookId){
        return bookMapper.selectById(bookId);
    }
}
