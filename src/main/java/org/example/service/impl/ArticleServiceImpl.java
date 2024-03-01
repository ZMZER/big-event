package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.ArticleMapper;
import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.service.ArticleService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {

        Map<String, Object> map = ThreadLocalUtil.get();
        article.setCreateUser((Integer) map.get("id"));

        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建pageBean对象封装查询好的数据
        PageBean<Article> pageBean = new PageBean<>();

        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        List<Article> articles = articleMapper.list(categoryId, state, userId);

        //使用PageHelper后 会返回Page对象 Page中提供了方法可以获取分页查询后的总记录条数和当前页数据
        Page<Article> page = (Page<Article>) articles;

        //把数据填充到PageBean中
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());

        return pageBean;
    }

    @Override
    public Article findArticleById(Integer id) {
        return articleMapper.findArticleById(id);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
