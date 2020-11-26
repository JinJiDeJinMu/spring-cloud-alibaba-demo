package com.hjb.elastic;

import com.hjb.elastic.model.Query;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EsServiceImpl implements com.hjb.elastic.EsService {

    @Autowired
    private EsTools esTools;

    @Override
    public Boolean createIndex(String index, String mapping, String setting) {
        return esTools.createIndex(index, mapping, setting).isAcknowledged();
    }

    @Override
    public Boolean deletedIndex(String index) {
        return esTools.deletedIndex(index);
    }

    @Override
    public Boolean insertData(String index, Long id, Object object) {
        if(id == null){
            return Boolean.FALSE;
        }
        esTools.insertData(index, String.valueOf(id), object);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteData(String index, Long id) {
        if(id == null){
            return Boolean.FALSE;
        }
        esTools.deleteData(index,String.valueOf(id));
        return Boolean.TRUE;
    }

    @Override
    public Boolean bulkIndex(String index, List<Object> list) {
        esTools.bulkIndex(index, list);
        return Boolean.TRUE;
    }

    @Override
    public List<Map<String, Object>> search(Query query) {

        List<Map<String, Object>> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        boolQueryBuilder.must(QueryBuilders.matchQuery("goodsName",query).operator(Operator.AND));
        boolQueryBuilder.must(QueryBuilders.matchQuery("goodsDesc",query).operator(Operator.AND));
        boolQueryBuilder.must(QueryBuilders.matchQuery("keyword",query).operator(Operator.AND));

        sourceBuilder.query(boolQueryBuilder);

        searchRequest.source(sourceBuilder);
        searchRequest.indices("goodsku");
        SearchResponse response = esTools.query(searchRequest);
        System.out.println("查询结果" + response);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            result.add(hit.getSourceAsMap());
        }
        return result;
    }
}
