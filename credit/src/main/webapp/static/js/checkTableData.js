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
var CheckTable = function () {
	var oTable;		
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
	           {data: "amount" },
	           {data: "createdTimeStr",defaultContent:""},	           
	           {data: "descr",defaultContent:"" },
	           {'render':function(data,status,row){
	   				var rid = row.id;
	   				var rtime = row.createdTime;
	   				var str='<a data-toggle="modal" href="#open_img"><img width="100" src="'+'upload/'+rid+rtime+'.jpg" onerror="this.src=\'\'"/></a>';	   				
					return str;
				}
	           },
	           {'render':function(data,status,row){
	   				var tem = row.type;
	   				var str='<div class="text-center"><button class="btn btn-sm blue add_money" data-toggle="modal" href="#add_money">审核后充值</button>';
	   				str=str+'<button class="btn btn-sm blue del_record" data-toggle="modal" href="#delete_record">关闭申请</button></div>';
					return str;
				}
	           },
	           
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"check/checkList?rand="+Math.random(),	        
		});				 				       		 
	};			
	
	//点击查看凭证图片
    table.on('click', 'tbody tr img',function(){
       var data = oTable.api().row($(this).parents('tr')).data();       
       $("#checkImg").attr("src","upload/"+data.id+data.createdTime+".jpg");           
    });
	
	//点击关闭充值按钮
    table.on('click', 'tbody tr button.del_record',function(){
       var data = oTable.api().row($(this).parents('tr')).data();       
       $("#delId").val(data.id);           
    });
	
	//删除充值申请记录
	$('#deleteBtn').on('click', function (e) {		
		$.ajax( {
         "dataType": 'json', 
         "type": "POST", 
         "url": rootURI+"check/delete/"+$("#delId").val(), 
         "success": function(data,status){
        	 if(status == "success"){					
				 if(data.status){					 					 
	            	 oTable.api().draw();
	            	 oTable.$('th span').removeClass();
	            	 handleAlerts("充值申请记录删除成功.","success","");
				 }
				 else{
					 handleAlerts("充值申请记录删除失败，错误原因："+data.info,"danger","");
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
    }); 
	
	
	//充值
    table.on('click', 'tbody tr button.add_money',function(){
       var data = oTable.api().row($(this).parents('tr')).data();
       $("#checkId").val(data.id);
       $("#addMoneyId").val(data.userId);
       $("#addMoneyAmount").val("");       
    });
    //添加操作
	var AddMoney = function(){		
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"check/addMoney", 
         "data": $('#addMoneyForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 handleAlerts("为用户充值成功.","success","");		            	 
				 }
				 else{
					 handleAlerts("为用户充值失败："+resp.info,"danger","");						 
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
		$("#addMoneyId").val("");
		$('#add_money').modal('hide');
    };
    
    var AddMoneyValidation = function() {
        var form = $('#addMoneyForm');
        var errorDiv = $('.alert-danger', form);            
        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {
            	userId: {required: true,},
                amount: {required: true,number:true},        	 
            },
           invalidHandler: function (event, validator) { //display error alert on form submit              
                errorDiv.show();                    
            },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },
            onfocusout:function(element){
            	$(element).valid();
            },
            submitHandler: function (form) { 
            	errorDiv.hide();
            	AddMoney();
            }
        });
    };
    
    //提示信息处理方法（是在页面中指定位置显示提示信息的方式）
	var handleAlerts = function(msg,msgType,position) {         
        Metronic.alert({
            container: position, // alerts parent container(by default placed after the page breadcrumbs)
            place: "prepent", // append or prepent in container 
            type: msgType,  // alert's type (success, danger, warning, info)
            message: msg,  // alert's message
            close: true, // make alert closable
            reset: true, // close all previouse alerts first
            focus: false, // auto scroll to the alert after shown
            closeInSeconds: 10, // auto close after defined seconds, 0 never close
            icon: "warning" // put icon before the message, use the font Awesone icon (fa-[*])
        });        

    };

    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable(); 
        	AddMoneyValidation();
        }

    };

}();
