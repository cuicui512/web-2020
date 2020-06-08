package com.example.one.mapper;

import com.example.one.Dto.QuestionQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExpendMapper {

    @Update(" update QUESTION set VIEW_COUNT= VIEW_COUNT + #{viewCount}  where ID = #{id}")
    void incViewCount(Question record);

    @Update("update QUESTION set COMMENT_COUNT = COMMENT_COUNT + #{commentCount} where ID = #{id}")
    void incCommentCount(Question record);

    @Update("update COMMENT set COMMENT_COUNT = COMMENT_COUNT + #{commentCount} where ID = #{id}")
    void incToCommentCount(Comment record);

    @Select("SELECT * FROM QUESTION WHERE id != #{id} AND tag regexp #{tag}")
    List<Question> selectRelated(Question question);

    @Select("SELECT COUNT(*) FROM QUESTION WHERE title regexp #{search}")
    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    @Select("SELECT * FROM QUESTION WHERE title regexp #{search} LIMIT #{page} , #{size}")
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

    @Update(" update COMMENT set LIKE_COUNT= LIKE_COUNT + #{likeCount}  where ID = #{id}")
    void incLikeCount(Comment record);

    @Update(" update QUESTION set LIKE_COUNT= LIKE_COUNT + #{likeCount}  where ID = #{id}")
    void incQuestionLikeCount(Question record);
}
