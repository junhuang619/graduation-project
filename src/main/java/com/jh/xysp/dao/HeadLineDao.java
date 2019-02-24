package com.jh.xysp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.HeadLine;

public interface HeadLineDao {
	List<HeadLine> selectHeadLineList(@Param("headLineConditon") HeadLine headLineConditon);
}
