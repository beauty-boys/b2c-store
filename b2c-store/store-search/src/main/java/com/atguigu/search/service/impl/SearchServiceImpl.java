package com.atguigu.search.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.atguigu.param.ProductSearchParam;
import com.atguigu.pojo.Product;
import com.atguigu.search.service.SearchService;
import com.atguigu.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public R search(ProductSearchParam productParamsSearch) {

        SearchRequest searchRequest = new SearchRequest("product");
        String search = productParamsSearch.getSearch();

        if (StringUtils.isEmpty(search)){
            //如果为null,查询全部
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }else{
            //不为空 all字段进行搜索
            searchRequest.source().query(QueryBuilders.matchQuery("all",search));
        }

        //设置分页参数
        searchRequest.source().from((productParamsSearch.getCurrentPage()-1)*productParamsSearch.getPageSize());
        searchRequest.source().size(productParamsSearch.getPageSize());

        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //结果集解析
        //获取集中的结果
        SearchHits hits = response.getHits();
        //获取符合的数量
        long total = hits.getTotalHits().value;

        SearchHit[] items = hits.getHits();

        List<Product> productList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for (SearchHit item : items) {
            //获取单挑json数据
            String json = item.getSourceAsString();
            Product product = null;
            try {
                product = objectMapper.readValue(json, Product.class);
            } catch (JsonProcessingException e) {
               e.printStackTrace();
            }
            productList.add(product);
        }
        R ok = R.ok(null, productList, total);
        log.info("SearchServiceImpl.search业务结束，结果：{}",ok);
        return ok;
    }
}
