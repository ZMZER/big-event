package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Category {
    //指定校验分组
    @NotNull(groups = Update.class)
    private Integer id;//主键ID

    @NotEmpty
    private String categoryName;//分类名称

    @NotEmpty
    private String categoryAlias;//分类别名

    private Integer createUser;//创建人ID

    //时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    //如果某个校验项没有指定分组，则默认属于Default分组
    //分组之间可以继承

    //设置校验分组
    public interface Update{
    }

}
