package com.hjb.elastic;

import com.alibaba.fastjson.JSON;
import com.hjb.domain.GoodsInfo;
import com.hjb.elastic.model.EsGoods;
import com.hjb.elastic.model.Query;
import com.hjb.service.GoodsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EsServiceImpl implements com.hjb.elastic.EsService {

    @Autowired
    private EsTools esTools;

    @Autowired
    private GoodsInfoService goodsInfoService;

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
    public List<EsGoods> search(Query query) {
        List<EsGoods> result = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //全文检索字段推荐使用match，精确字段推荐term
        if(query.getBrandId() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("brandId",query.getBrandId()));

        }
        if(query.getCategoryId() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("categoryId",query.getCategoryId()));

        }
        //FunctionScoreQueryBuilder会在查询结束后对每一个匹配的文档进行一系列的重打分操作，最后以生成的最终分数进行排序
        List filterFunctionBuilders = new ArrayList<>();
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("goodsNames", query.getContent()),
                ScoreFunctionBuilders.weightFactorFunction(10)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("goodsDesc", query.getContent()),
                ScoreFunctionBuilders.weightFactorFunction(5)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("keywords", query.getContent()),
                ScoreFunctionBuilders.weightFactorFunction(3)));
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
        filterFunctionBuilders.toArray(builders);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
        boolQueryBuilder.must(functionScoreQueryBuilder);

        sourceBuilder.query(boolQueryBuilder);

        if(query.getSort() != null){
            Integer sort = query.getSort();
            //排序
            if(sort == 1){
                sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
            } else if(sort == 2){
                sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));
            }
        }

        searchRequest.source(sourceBuilder);
        searchRequest.indices(ElasticDocument.INDEX);
        SearchResponse response = esTools.query(searchRequest);

        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            result.add(JSON.parseObject(hit.getSourceAsString(),EsGoods.class));
        }
        return result;
    }

    @Override
    public List<EsGoods> recommend(Long id) {

        List<EsGoods> result = new ArrayList<>();
        GoodsInfo goodsInfo = goodsInfoService.getById(id);
        String goodsName = goodsInfo.getGoodsName();
        Long brandId = goodsInfo.getBrandId();
        Long categoryId = goodsInfo.getCategoryId();

        List filterFunctionBuilders = new ArrayList<>();
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("goodsName", goodsName),
                ScoreFunctionBuilders.weightFactorFunction(8)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("goodsDesc", goodsName),
                ScoreFunctionBuilders.weightFactorFunction(2)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("keyword", goodsName),
                ScoreFunctionBuilders.weightFactorFunction(2)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("brandId", brandId),
                ScoreFunctionBuilders.weightFactorFunction(5)));
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("categoryId", categoryId),
                ScoreFunctionBuilders.weightFactorFunction(3)));
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
        filterFunctionBuilders.toArray(builders);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
        //用于过滤掉相同的商品
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("id",id));
        boolQueryBuilder.must(functionScoreQueryBuilder);

        SearchRequest searchRequest = new SearchRequest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.indices(ElasticDocument.INDEX);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = esTools.query(searchRequest);
        for (SearchHit hit : response.getHits().getHits()) {
            EsGoods esGoods = JSON.parseObject(hit.getSourceAsString(),EsGoods.class);
            result.add(esGoods);
        }
        return result;
    }

    @Override
    public List<EsGoods> query(String keyword) {

        List<EsGoods> result = new ArrayList<>();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(StringUtils.isEmpty(keyword)){
            boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }else{
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(keyword,"goodsName","goodsDesc","keyword"));
        }
        // 品牌聚合（前20个）
        TermsAggregationBuilder brandAggBuilder = AggregationBuilders.terms(keyword).field("brandName").size(20);
        // 分类聚合（前20个）
        TermsAggregationBuilder categoryAggBuilder = AggregationBuilders.terms(keyword).field("categoryName").size(20);
        SearchSourceBuilder searchQuery = new SearchSourceBuilder();

        searchQuery.query(boolQueryBuilder);// 过滤条件
        searchQuery.aggregation(brandAggBuilder); // 品牌聚合
        searchQuery.aggregation(categoryAggBuilder);// 分类聚合

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchQuery);
        searchRequest.indices(ElasticDocument.INDEX);
        SearchResponse response = esTools.query(searchRequest);

        for (SearchHit hit : response.getHits().getHits()) {
            EsGoods esGoods = JSON.parseObject(hit.getSourceAsString(),EsGoods.class);
            result.add(esGoods);
        }
        return result;
    }
}
