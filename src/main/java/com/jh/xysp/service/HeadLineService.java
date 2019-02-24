package com.jh.xysp.service;

import java.io.IOException;
import java.util.List;

import com.jh.xysp.entity.HeadLine;

public interface HeadLineService {
	public static final String HEADLINEKEY = "headline";
	List<HeadLine> getHeadLineList(HeadLine headLineConditon)throws IOException;
}
