    var options={
    	url:"<c:url value='/job/querylist'/>",
    	formid:'query_form'
    }
  //��ʼ��
    $(function(){
    	$('#jobtable').bttable(options);
    });
  
    //�����༭
    function jobnameFormatter(value, row, index) {
        var tpl = $("#tpl").html();  
	  	//Ԥ����ģ��  
	  	var template = Handlebars.compile(tpl);  
	  	return template(row);
    }
    
    function queryParams(params) {
    	 var request_param=$.extend({},$('#query_form').serializeObject(),params);
         return request_param
    }
    
    //��ѯ
    function query(){
    	$('#jobtable').bootstrapTable('refresh', {url: "<c:url value='/job/querylist'/>"});
    }
    
  //���ݱ༭
    function edit(id){
    	var url = "<c:url value='/job/gotoedit'/>";
    	openwindow('editwindow',url+'/'+id); 
    }
     
    //��ͣ
    function pause(id){
    	var url = "<c:url value='/job/pause'/>"+"/"+id;
    	ajax(url,"ȷ����ͣ��������")
    }
  
    //�ָ�
    function resume(id){
    	var url = "<c:url value='/job/resume'/>"+"/"+id;
    	ajax(url,"ȷ���ָ���������")
    }
    
    //ɾ��
    function dd(id){
    	var url = "<c:url value='/job/delete'/>"+"/"+id;
    	ajax(url,"ȷ��ɾ����������")
    }
    
    function ajax(url,tip){
    	layer.confirm(tip,function(){
        	$.ajax({
                type : "get",
                url : url,
                dataType : "json",
                success:function(response,textStatus){
                	layer.msg(response.message);
                	query();
                },
                error : function() {
                    layer.msg('����������');
                }
            });
    	})
    }
    //����
    function add_page(){
    	layer.open({
	   		  type: 2,
	   		  title: '����ҳ��',
	   		  shadeClose: true,
	   		  shade: 0.8,
	   		  area: ['50%', '90%'],
	   		  content: "<c:url value='/job/toadd'/>" //iframe��url
   		});
    }    