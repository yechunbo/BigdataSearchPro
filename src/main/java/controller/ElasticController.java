package controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import serviceImpl.ElasticServiceImpl;

/**
 * @author YeChunBo
 * @time 2019年3月31日
 *
 *       类说明
 */

@RestController
@EnableAutoConfiguration  
@SpringBootApplication   // 加载其他controller类
public class ElasticController {

	private static final Logger logger = LogManager.getLogger(ElasticController.class);

	@RequestMapping("/createIndex/{indexName}")
	@ResponseBody
	public String createIndex(@PathVariable String indexName) {
		ElasticServiceImpl elsService = new ElasticServiceImpl();
		elsService.createIndex(indexName);
		System.out.println("-------------");
		logger.info(" createIndex methon start .... ");
		return "--- createIndex finish ....";
	}
	
	/**
	 * 批量导数据至ES中
	 * @param indexName
	 * @return
	 */
	@RequestMapping("/bulk/{indexName}")
	@ResponseBody
	public String bulkDataToEs(@PathVariable String indexName) {
		ElasticServiceImpl elsService = new ElasticServiceImpl();
		elsService.bulkData2Es(indexName);
		System.out.println("-------------");
		logger.info(" bulkDataToES methon end .... ");
		return "--- bulk data to es ....";
	}

	@RequestMapping("/deleteIndex/{indexName}")
	@ResponseBody
	public String deleteIndex(@PathVariable String indexName) {
		ElasticServiceImpl elsService = new ElasticServiceImpl();
		elsService.deleteIndex(indexName);
		System.out.println("-------------");
		return "--- delete data from es ....";
	}

	@RequestMapping("/getAllIndex")
	@ResponseBody
	public ArrayList<String> getAllIndex() {
		ElasticServiceImpl elsService = new ElasticServiceImpl();
		ArrayList<String> indexList = elsService.getAllIndex();
		logger.info("-------------" + indexList.toArray().toString());
		return indexList;
	}

	public static void main(String[] args) {
		SpringApplication.run(ElasticController.class, args);
	}
}
