package controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;

/**
* @author YeChunBo
* @time 2019年4月1日 
*
* 类说明 
*/

import javax.servlet.http.HttpServletRequest;  

import org.springframework.web.bind.annotation.*;

import serviceImpl.ElasticServiceImpl;    
    
@Controller  
public class HtmlController {    
     
    @GetMapping("/indexView")  
    String test(HttpServletRequest request) { 
    	ElasticServiceImpl elsService = new ElasticServiceImpl();
		ArrayList<String> indexList = elsService.getAllIndex();
		indexList.add("all index");
        //逻辑处理  
        request.setAttribute("indexList", indexList);  
        return "indexView";  // 与templates 中html名一样
    }    
    
}    


