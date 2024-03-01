package org.example.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time)" +
            "values (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, now(), now())")
    void add(Article article);

    List<Article> list(Integer categoryId, String state, Integer userId);

    @Select("select * from article where id = #{id}")
    Article findArticleById(Integer id);

    @Select("update article set title = #{title}, content = #{content}, cover_img = #{coverImg}, state = #{state}," +
            " category_id = #{categoryId}, update_time = now() where id = #{id}")
    void update(Article article);

    @Delete("delete from article where id = #{id}")
    void delete(Integer id);
}
