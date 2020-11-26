package com.hjb.elastic;

import com.hjb.elastic.model.EsGoods;
import com.hjb.elastic.model.Query;


import java.util.List;
import java.util.Map;

public interface EsService {

    Boolean createIndex(String index, String mapping, String setting);

    Boolean deletedIndex(String index);

    Boolean insertData(String index , Long id, Object object);

    Boolean deleteData(String index , Long id);

    Boolean bulkIndex(String index, List<Object> list);

    List<Map<String, Object>> search(Query query);

    List<EsGoods> recommend(Long id);

}
