package com.hjb.elastic;

import com.hjb.elastic.model.Goods;
import com.hjb.elastic.model.Query;


import java.util.List;

public interface EsService {

    Boolean createIndex(String index, String mapping, String setting);

    Boolean deletedIndex(String index);

    Boolean insertData(String index , Long id, Object object);

    Boolean deleteData(String index , Long id);

    Boolean bulkIndex(String index, List<Object> list);

    List<Goods> search(Query query);

    List<Goods> recommend(Long id);

    List<Goods> query(String keyword);


}
