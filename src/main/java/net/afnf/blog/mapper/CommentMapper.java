package net.afnf.blog.mapper;

import java.util.List;

import net.afnf.blog.domain.Comment;
import net.afnf.blog.domain.CommentExample;

import org.apache.ibatis.annotations.Param;

public interface CommentMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int countByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int deleteByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int insert(Comment record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int insertSelective(Comment record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    List<Comment> selectByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    Comment selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table comments
     * @mbggenerated  Tue Dec 31 19:53:25 JST 2013
     */
    int updateByPrimaryKey(Comment record);
}