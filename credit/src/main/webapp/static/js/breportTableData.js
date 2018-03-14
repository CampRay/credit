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
	           {'render':function(data,status,row){
	   				return 'HK$ '+row.amount;		
				}},	
	           {data: "createdTimeStr",defaultContent:""},	           
	           {'render':function(data,status,row){	   				
	   				return 'HK$ '+row.balance;					
				}},	
	           {data: "descr",defaultContent:"" },
	           {'render':function(data,status,row){
	   				var tem = row.type;
	   				var str='实际充值'
	   				if(tem){
	   					str="申请充值";
	   				}
					return str;
				}
	           },
	           
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"admin/balanceList?rand="+Math.random(),
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
