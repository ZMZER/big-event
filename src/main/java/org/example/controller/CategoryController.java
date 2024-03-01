package org.example.controller;

import org.example.pojo.Category;
import org.example.pojo.Result;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //添加分类
    @PostMapping
    public Result add(@RequestBody @Validated Category category) {
        categoryService.add(category);
        return Result.success();
    }

    //查询分类列表
    @GetMapping
    public Result list() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

    //查询分类详情
    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Category category = categoryService.findCategoryById(id);
        return Result.success(category);
    }

    //修改分类
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    //删除分类
    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        categoryService.delete(id);
        return Result.success();
    }

}
