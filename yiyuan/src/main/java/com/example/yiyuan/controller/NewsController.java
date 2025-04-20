package com.example.yiyuan.controller;

import com.example.yiyuan.entity.po.News;
import com.example.yiyuan.entity.query.NewsQuery;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.service.NewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Description: Controller *
 * @author:??
 * @date:2025/03/18
 *
 */
@RestController
@RequestMapping("/news")
public class NewsController extends ABaseController {

	@Resource
	private NewsService newsService;

	@PostMapping("/search")
	public ResponseVO loadDataList(String keyWord) {
		NewsQuery newsQuery=new NewsQuery();
		newsQuery.setTitleFuzzy(keyWord);
		return getSuccessResponseVO(newsService.findListByParam(newsQuery));
	}


	/**
	 * 根据NewId查询
	 */
	@GetMapping("/getNewsByNewId")
	public ResponseVO getNewsByNewId(Integer newId) {

		return getSuccessResponseVO(this.newsService.getNewsByNewId(newId));
	}

	/**
	 * 根据NewId查询
	 */
	@GetMapping("/getAllNews")
	public ResponseVO getAllNews() {

		return getSuccessResponseVO(this.newsService.getAllNews());
	}



}