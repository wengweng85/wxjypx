package com.insigma.mvc.serviceimp.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SolrQueryBean;
import com.insigma.mvc.service.solr.SolrService;

/**
 * 搜索服务
 * @author wengsh
 *
 */
@Service
public class SolrServiceImpl extends MvcHelper implements SolrService {


	Log log=LogFactory.getLog(SolrServiceImpl.class);
	
	@Value("${solr_url}")
	private String solrurl;
	/**
	 * 搜索
	 * 
	 * @param collection
	 * @param q
	 * @param type
	 * @param start
	 * @param rows
	 * @return
	 */
	@Override
	public void search_lucene_info(SolrQueryBean solrQueryBean)  throws IOException,SolrServerException{
		String query_param = "*:*";
		if (!solrQueryBean.getQ().equals("")) {
			query_param = "f_title_ik:" + solrQueryBean.getQ() + " or f_value_ik:" + solrQueryBean.getQ();
		}
		HttpSolrClient client = new HttpSolrClient(solrurl);
		client.setConnectionTimeout(30000);
		client.setDefaultMaxConnectionsPerHost(100);
		client.setMaxTotalConnections(100);
		client.setSoTimeout(30000);
		SolrQuery query = new SolrQuery();
		query.setQuery(query_param);
		query.setFields("id", "f_info_id_ik","f_title_ik", "f_value_ik");
		query.setStart(new Integer(solrQueryBean.getStart()).intValue());
		query.setRows(new Integer(solrQueryBean.getRows()).intValue());
		query.addHighlightField("f_title_ik");
		query.addHighlightField("f_value_ik");
		query.setHighlight(true);
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		List<HashMap> list = new ArrayList<HashMap>();
		QueryResponse rsp = client.query(query);
		SolrDocumentList docs = rsp.getResults();
		//高亮数据  第一个Map的键是文档的ID，第二个Map的键是高亮显示的字段名 
		Map<String, Map<String, List<String>>> highlightmap= rsp.getHighlighting();
		//返回查询时间
		log.info("查询内容:" + query);
		log.info("文档数量：" + docs.getNumFound());
		log.info("查询花费时间:" + rsp.getQTime());
		for (SolrDocument doc : docs) {
			HashMap map = new HashMap();
			String id = (String) doc.getFieldValue("id");
			String f_title_ik = (String) doc.getFieldValue("f_title_ik");
			String f_value_ik = (String) doc.getFieldValue("f_value_ik");
			String f_info_id_ik = (String) doc.getFieldValue("f_info_id_ik");
			map.put("id", id);
			if(f_value_ik.length()>200){
				f_value_ik=f_value_ik.substring(0,200)+"...";
			}
			map.put("f_info_id_ik", f_info_id_ik);
			if(highlightmap.get(id).get("f_title_ik")==null){
				map.put("f_title_ik",f_title_ik);
				map.put("f_value_ik", f_value_ik);
			}else{
				map.put("f_title_ik",highlightmap.get(id).get("f_title_ik"));
				map.put("f_value_ik",highlightmap.get(id).get("f_value_ik") );
			}
			list.add(map);
		}
		//查询结果
		solrQueryBean.setDatalist(list);
		solrQueryBean.setQttime (rsp.getQTime());
		solrQueryBean.setNumfound(docs.getNumFound());
	}
}