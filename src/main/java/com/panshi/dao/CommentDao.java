package com.panshi.dao;

        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import com.panshi.entity.BackComment;
        import com.panshi.entity.Comment;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;

public interface CommentDao extends BaseMapper<Comment> {
    public List<BackComment> queryall(@Param("begin") Integer begin, @Param("end") Integer end);
    public List<BackComment> querytow(String interactId);
}
