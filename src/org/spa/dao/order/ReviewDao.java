package org.spa.dao.order;

import org.spa.vo.report.SalesSearchVO;

import java.util.List;
import java.util.Map;

public interface ReviewDao {
   Long getCount(SalesSearchVO salesSearchVO);
   List<Map<String,Object>> getListing(SalesSearchVO salesSearchVO);
   Long getNpsSum(SalesSearchVO salesSearchVO);
}
