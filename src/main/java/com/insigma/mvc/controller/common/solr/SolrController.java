package com.insigma.mvc.controller.common.solr;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SolrQueryBean;
import com.insigma.mvc.service.solr.SolrService;

/**
 * 搜索服务
 * @author wengsh
 *
 */
@Controller
@RequestMapping(value = "/common")
public class SolrController   extends MvcHelper<SolrQueryBean>{

    @Resource
    private SolrService solrService;
    
	/**
	 * 搜索页面主页
	 * @param request
	 * @return
	 */
	@RequestMapping("/solrindex")
	public ModelAndView solrindex(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("solr/solrSearch");
        return modelAndView;
	}

    /**
     * 根据关键字搜索数据
     * @param solrQueryBean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/solrquery")
    @ResponseBody
    public AjaxReturnMsg<SolrQueryBean> solrquery( SolrQueryBean solrQueryBean) throws Exception {
    	solrService.search_lucene_info(solrQueryBean);
    	return this.success(solrQueryBean);
    }
}
