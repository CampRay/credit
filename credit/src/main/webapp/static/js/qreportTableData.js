//jquery插件把表单序列化成json格式的数据start 
(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var str=this.serialize();
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

var rootURI="/";
var ReportTable = function () {
	var oTable;	
	var selected = [];
	var table=$('#breport_table');
	var handleTable = function () {		
		 oTable = table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"processing":true,        	
            "displayLength": 10,
            "dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",  
            "oLanguage": {
                "sProcessing": "正在加载中......",                
                "sZeroRecords":"对不起，查询不到相关数据！",
                "sEmptyTable": "表中无数据存在",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共  _TOTAL_ 条记录",
                "sInfoEmpty":"查询数据为空",
            },
            "columns": [               
	           {data: "userId"  },
	           {'render':function(data,type,row){
	        	   var api = row.apiId;
	        	   var str="";
	        	   if(api==1){
	        		   str="身份证认证(新颜)";
	        	   }
	        	   else if(api==2){
	        		   str="诚信信息核查(新颜)";
	        	   }
	        	   else if(api=="3"){
	        		   str="银行卡信息认证(新颜)";
	        	   }
	        	   else if(api=="4"){
	        		   str="身份证认证(元素)";
	        	   }
	        	   else if(api=="5"){
	        		   str="风险控制核查(元素)";
	        	   }
	        	   else if(api=="6"){
	        		   str="银行卡信息认证(元素)";
	        	   }
	        	   else if(api=="7"){
	        		   str="工商信息查询(元素)";
	        	   }
	               	return str;
               	  }
	           },	           
	           {data: "createdTimeStr",defaultContent:""},
	           {data: "amount" },
	           {data: "descr",defaultContent:"" }
	           
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"admin/queryList?rand="+Math.random(),
	        "fnDrawCallback":function(oSetting){
	        	selected=[];
	        }
		});				 				       
		 
	};
	
	//搜索表单提交操作
	$("#searchBtn").on("click", function(event) {
		event.preventDefault();
		var jsonData=$("#searchForm").serializeJson();
		var jsonDataStr=JSON.stringify(jsonData);			
		oTable.fnFilter(jsonDataStr);
		return false;
	});	
		
          
  //initialize datepicker
  var datePicker = function(){
  	$('.date-picker').datepicker({      
      autoclose: true
      });
   };

    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable();  
        	datePicker();
        }

    };

}();
