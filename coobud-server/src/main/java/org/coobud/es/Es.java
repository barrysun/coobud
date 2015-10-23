package org.coobud.es;

import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.Delete.Builder;

public class Es {
	// http://blog.csdn.net/qian_348840260/article/details/20644445

	private static final String PWDKEY = "X-SCE-ES-PASSWORD";
	private static final long ES_INDEX_PASSWORD = 0;

	public static JestClient getJestClient() {
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(
				"http://10.16.0.211:9200").multiThreaded(true).build());
		JestClient client = factory.getObject();
		return client;
	}

	/**
	 * 创建索引
	 * 
	 * @param indexName
	 */
//	public void createIndex(String indexName) {
//		CreateIndex.Builder builder = new CreateIndex.Builder(indexName);
//		CreateIndex createIndex = new CreateIndex(builder);
//		try {
//			JestClient jestClient = getJestClient();
//			JestResult result = jestClient.execute(createIndex);
//
//			if (result == null || !result.isSucceeded()) {
//				throw new Exception(result.getErrorMessage() + "创建索引失败");
//
//			}
//
//		} catch (Exception e) {
//			throw e;
//		}
//	}

	/**
	 * 手动创建类型(map一旦定义创建，field只能新增，不能修改)
	 * 
	 * @param indexName
	 * @param indexType
	 * @param mappingString
	 * @throws Exception
	 */
	public void createType(String indexName, String indexType,
			String mappingString) throws Exception {
		PutMapping.Builder builder = new PutMapping.Builder(indexName,
				indexType, mappingString);
		builder.setHeader(PWDKEY, getSecret());
		builder.refresh(true);
		try {
			JestClient jestClient = getJestClient();
			JestResult result = jestClient.execute(builder.build());
			if (result == null || !result.isSucceeded()) {
				throw new RuntimeException(result.getErrorMessage()
						+ "创建索引类型失败");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取索引类型mapping
	 * 
	 * @param indexName
	 * @param typeName
	 * @return
	 * @throws Exception
	 */
	public String getMapping(String indexName, String typeName)
			throws Exception {
		GetMapping.Builder builder = new GetMapping.Builder();
		builder.addIndex(indexName).addType(typeName);
		builder.setHeader("X-SCE-ES-PASSWORD", getSecret());
		try {
			JestClient jestClient = getJestClient();
			JestResult result = jestClient.execute(builder.build());
			if (result != null || !result.isSucceeded()) {
				throw new Exception(result.getErrorMessage());
			}
			return result.getSourceAsObject(JsonObject.class).toString();
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 删除文档
	 * @param indexId
	 * @param indexName
	 * @param indexType
	 * @throws Exception
	 */
	public void deleteDoc(String indexId, String indexName, String indexType)
			throws Exception {
		Delete.Builder builder = new Delete.Builder(indexId);
		builder.setHeader(PWDKEY, getSecret());
		builder.id(indexId);
		builder.refresh(true);
		Delete delete = builder.index(indexName).type(indexType).build();
		try{
			JestClient jestClient=getJestClient();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 删除类型
	 * @param indexName
	 * @param indexType
	 * @throws Exception
	 */
	public void deleteType(String indexName,String indexType) throws Exception{
		Delete.Builder builder=new Delete.Builder(indexName);
		builder.setHeader(PWDKEY,getSecret());
		builder.refresh(true);
		Delete delete=builder.index(indexName).type(indexType).build();
		try{
			JestClient jestClient=getJestClient();
			JestResult result=jestClient.execute(delete);
			if(result ==null || !result.isSucceeded()){
				throw new RuntimeException(result.getErrorMessage());
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 删除索引
	 * @param indexName
	 * @throws Exception
	 */
	public void deleteIndex(String indexName) throws Exception{
		Delete.Builder builder= new Delete.Builder(indexName);
		builder.setHeader(PWDKEY,getSecret());
		builder.refresh(true);
		Delete delete=builder.index(indexName).build();
		try{
			JestClient jestClient=getJestClient();
			JestResult result=jestClient.execute(delete);
			if(result==null || !result.isSucceeded()){
				throw new RuntimeException(result.getErrorMessage());
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 插入或更新文档
	 * @param indexId
	 * @param indexObject
	 * @param indexName
	 * @param indexType
	 * @throws Exception
	 */
	public void insertOrUpdateDoc(String indexId,Object indexObject,String indexName, String indexType )throws Exception{
		Index.Builder builder=new Index.Builder(indexObject);
		builder.setHeader(PWDKEY,getSecret());
		builder.id(indexId);
		builder.refresh(true);
		Index index=builder.index(indexName).type(indexType).build();
		try{
			JestClient jestClient=getJestClient();
			JestResult result=jestClient.execute(index);
			if(result == null || !result.isSucceeded()){
				throw new RuntimeException(result.getErrorMessage());
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	

	/**
	 * 
	 * @param indexName
	 * @param indexType
	 */
//	public void search(String indexName,String indexType){
//		
//		Search.Builder builder=new Search.Builder(query);
//		builder.addIndex(indexName).addType(indexType);
//		builder.setHeader(PWDKEY,getSecret());
//		builder.setParameter("from","");
//		builder.setParameter("size","");
//		
//		JestClient jestClient=getJestClient();
//		JestResult result=jestClient.execute(builder.build());
//		int hitCount=result.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt();
//		
//	}

	private String getQueryString(String query) {
		return "{\"query\":" + query + "}";
	}

	protected static String getSecret() {
		long time = System.currentTimeMillis() / 1000;
		return time
				+ ","
				+ DigestUtils.md5Hex(time + ES_INDEX_PASSWORD + "")
						.toUpperCase();
	}
	
	public static void main(String[] args) throws IOException{
		JestClient client=getJestClient();
		String query = "{\n" +
	            "    \"query\": {\n" +
	            "        \"filtered\" : {\n" +
	            "            \"query\" : {\n" +
	            "                \"query_string\" : {\n" +
	            "                    \"query\" : \"Lord\"\n" +
	            "                }\n" +
	            "            }\n"+
	            "        }\n" +
	            "    }\n" +
	            "}";
	 
	Search search = (Search) new Search.Builder(query)
	                // multiple index or types can be added.
	                .addIndex("articles")
	                .addType("article")
	                .build();
	 
	 JestResult result = client.execute(search);
	}

}
