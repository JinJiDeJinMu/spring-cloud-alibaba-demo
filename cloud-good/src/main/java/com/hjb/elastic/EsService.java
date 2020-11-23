package com.hjb.elastic;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;

import java.util.List;

public interface EsService {

    /**
     * 创建索引
     * @param index
     * @param mapping
     * @param setting
     * @return
     */
    CreateIndexResponse createIndex(String index, String mapping, String setting);

    /**
    * 删除索引
    * @param index
    * @return
    */
    Boolean deletedIndex(String index);

    /**
     * 插入文档
     * @param index
     * @param type
     * @param id
     * @param object
     * @return
     */
    IndexResponse insertIndex(String index, String type , String id, Object object);

    /**
     * 删除文档
     * @param index
     * @param id
     * @return
     */
    DeleteResponse deleteDoc(String index , String id);

    /**
    * 批量插入文档
    * @param index
    * @param list
    * @return
    */
     BulkResponse bulkIndex(String index, List<Object> list);

    /**
     * 查询
     * @param index
     * @param source 查询字段
     * @param keyword
     * @return
     */
    SearchResponse search(String index, String source,String keyword);
}
