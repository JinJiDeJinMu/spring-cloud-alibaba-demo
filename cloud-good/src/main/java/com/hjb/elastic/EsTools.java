package com.hjb.elastic;

import com.alibaba.fastjson.JSON;
import com.hjb.elastic.model.Goods;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.client.ml.PostDataRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class EsTools {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 修改mapping设置
     * @param index
     * @param mapping
     * @return
     */
    public AcknowledgedResponse putMapping(String index, String mapping){
        PutMappingRequest mappingRequest = new PutMappingRequest(index);
        try {
            XContentBuilder builder = JsonXContent.contentBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("content")
                    .field("type","text")
                    .field("analyzer","ik_max_word")
                    .field("index","analyzed")
                    .endObject()
                    .endObject()
                    .endObject();

            mappingRequest.source(builder);

            AcknowledgedResponse response = restHighLevelClient.indices().putMapping(mappingRequest, RequestOptions.DEFAULT);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * 创建索引
     * @param index
     * @param mapping
     * @param setting
     * @return
     */
    public CreateIndexResponse createIndex(String index, String mapping, String setting) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        if(!StringUtils.isEmpty(mapping)){
            createIndexRequest.mapping(mapping, XContentType.JSON);
        }
        if(!StringUtils.isEmpty(setting)){
            createIndexRequest.settings(setting,XContentType.JSON);
        }
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            log.info("create index={}, response ={}",index, createIndexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createIndexResponse;
    }

    /**
     * 删除索引
     * @param index
     * @return
     */
    public Boolean deletedIndex(String index) {
        DeleteIndexRequest deleteindexRequest = new DeleteIndexRequest(index);
        deleteindexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
        Boolean result = false;
        try {
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteindexRequest, RequestOptions.DEFAULT);
            result = delete.isAcknowledged();
            log.info("delete index={}, reponse={}",index, delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加文档数据
     * @param index
     * @param id
     * @param object
     * @return
     */
    public IndexResponse insertData(String index, String id, Object object) {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index(index);
        if(!StringUtils.isEmpty(id)){
            indexRequest.id(id);
        }
        indexRequest.source(JSON.parseObject(JSON.toJSONString(object), Map.class));
        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("insert index={}, response={}", index, indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexResponse;
    }

    /**
     * 删除指定文档数据
     * @param index
     * @param id
     * @return
     */
    public DeleteResponse deleteData(String index, String id) {

        DeleteRequest deleteRequest = new DeleteRequest(index,id);
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
            log.info("delete index={}, id={}, response={}", index,id, deleteResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleteResponse;
    }

    /**
     * 批量插入文档数据
     * @param index
     * @param list
     * @return
     */
    public BulkResponse bulkIndex(String index, List<Object> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(e->{
            Goods goods = (Goods) e;
            IndexRequest indexRequest = new IndexRequest(index).id(goods.getId().toString());
            indexRequest.source(JSON.toJSONString(e), XContentType.JSON);
            request.add(indexRequest);
        });
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            log.info("bulk insert index={}, params={}, response={}",index, list, bulkResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bulkResponse;
    }

    public SearchResponse search(String index, String source , String keyword) {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery(source,keyword));
        //sourceBuilder.query(QueryBuilders.multiMatchQuery(keyword,source,"goodDesc"));



      /*  //queryStringQuery全文查询
        sourceBuilder.query(QueryBuilders.queryStringQuery("xx"));

        //短语匹配 ,和match查询类似，match_phrase查询首先解析查询字符串来产生一个词条列表。
        // 然后会搜索所有的词条，但只保留包含了所有搜索词条的文档，并且词条的位置要邻接。
        // 一个针对短语“中华共和国”的查询不会匹配“中华人民共和国”，因为没有含有邻接在一起的“中华”和“共和国”词条。
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("xx","yy"));

        //multi_match多个字段匹配某字符串
        sourceBuilder.query(QueryBuilders.multiMatchQuery("xx","yy","zz"));

        //完全包含查询,之前的查询中，当我们输入“我天”时，ES会把分词后所有包含“我”和“天”的都查询出来
        //如果我们希望必须是包含了两个字的才能被查询出来，那么我们就需要设置一下Operator。and或者or
        sourceBuilder.query(QueryBuilders.matchQuery("xx","yy").operator(Operator.AND));*/

        sourceBuilder.query(boolQueryBuilder);

        //高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(new HighlightBuilder.Field("goodsName"));
        highlightBuilder.preTags("<span style=\"color:red;size:50px\">");   //高亮设置
        highlightBuilder.postTags("</span>");
        highlightBuilder.fragmentSize(50);
        highlightBuilder.numOfFragments(0);//从第一个分片开始获取高亮片段
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);
        searchRequest.indices(index);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("search index={}, params={}, response={}",index, keyword, searchResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    public SearchResponse query(SearchRequest searchRequest) {
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("search response={}", searchResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return searchResponse;
    }
}
