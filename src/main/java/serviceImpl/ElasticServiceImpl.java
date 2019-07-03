package serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import service.ElasticService;

/**
* @author YeChunBo
* @time 2019年3月31日 
*
* 类说明 
*/
//@Service("elasticService")
public class ElasticServiceImpl implements ElasticService{

	private static final Logger logger = LogManager.getLogger(ElasticServiceImpl.class);

	@Override
	public String createIndex(String indexName) {
//		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("xx.xx.199.213", 9200, "http")));//初始化
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("hdp06", 8577, "http")));// 初始化
		try{
			// 其实应该先判断是否存在对应的索引，懒得写咯...
			CreateIndexRequest requestIndex = new CreateIndexRequest(indexName.toLowerCase());// 创建索引
			// 创建的每个索引都可以有与之关联的特定设置。设置副本数与刷新时间对于索引数据效率有不小的提升
			requestIndex.settings(Settings.builder().put("index.number_of_shards", 5)
					.put("index.number_of_replicas", 0)
					.put("index.refresh_interval", "-1"));
			CreateIndexResponse createIndexResponse = client.indices().create(requestIndex, RequestOptions.DEFAULT);
			logger.info("isAcknowledged:" + createIndexResponse.isAcknowledged());
			logger.info("isShardsAcknowledged:" + createIndexResponse.isShardsAcknowledged());
		} catch(Exception e) {
			//TODO
		} finally{
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return indexName;
	}

	@Override
	public String deleteIndex(String indexName) {
//		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("nn01", 9200, "http")));// 初始化
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("hdp06", 8577, "http")));// 初始化
		 try{
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        request.timeout(TimeValue.timeValueMinutes(20));
        request.masterNodeTimeout(TimeValue.timeValueMinutes(10));
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
        logger.info("isAcknowledged:" + deleteIndexResponse.isAcknowledged());
        System.out.println("isAcknowledged:" + deleteIndexResponse.isAcknowledged());
		 } catch(Exception e){
			 
		 } finally {
			 
			 try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }

		return " delete index .....";
	}

	@Override
	public ArrayList<String> getAllIndex() {
		ArrayList<String> arrList = new ArrayList<String>();
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("hdp06", 8577, "http")));// 初始化
		GetIndexRequest request = new GetIndexRequest().indices("_all");
        try{
        	 GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
        	 String[] indices = getIndexResponse.getIndices();
        	 for (String str : indices) {
        		 if(!str.trim().startsWith(".")) {
        			 arrList.add(str);
        		 }
			}
        	 logger.info("arrList:" + arrList.toString());
        } catch(Exception e) {
        	
        } finally{
        	 try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
		return arrList;
	}

	@Override
	public Boolean bulkData2Es(String indexName) {
		long startTime = System.currentTimeMillis();
		BulkProcessImpl bulk = new BulkProcessImpl();
		bulk.writeMysqlDataToES(indexName);

		logger.info(" use time: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
		return true;
	}

	@Override
	public ArrayList<String> queryData(String queryParm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIndexDataCount(String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		ElasticServiceImpl elasticServiceImpl = new ElasticServiceImpl();
		elasticServiceImpl.getAllIndex();
	}
}
