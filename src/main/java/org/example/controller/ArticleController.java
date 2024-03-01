package org.example.controller;

import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //添加文章
    @PostMapping
    public Result addArticle(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result list(Integer pageNum, Integer pageSize,
                       @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String state) {

        PageBean<Article> pageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }

    //查询文章详情
    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Article article = articleService.findArticleById(id);
        return Result.success(article);
    }

    //修改文章
    @PutMapping
    public Result updateArticle(@RequestBody @Validated(Article.Update.class) Article article) {
        articleService.update(article);
        return Result.success();
    }

    //删除文章
    @DeleteMapping
    public Result deleteArticle(@RequestParam Integer id) {
        articleService.delete(id);
        return Result.success();
    }


}
