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
var PriceTable = function () {
	var table = $('#price_setting');
	var oTable;	
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
	           {data: "id"  },
	           {data: "groupName" },
	           {data: "apiName"},
	           {data: "descr" },
	           {data: "price" },	           
	           {'render':function(data,status,row){
	   				var tem = row.adminId;
	   				var str='<div class="text-center"><button class="btn btn-sm blue" data-toggle="modal"  href="#edit_price">修改价格</button></div>'
					return str;
				}
	           },
	           
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"admin/priceList?rand="+Math.random(),	        
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
    
    //充值
    table.on('click', 'tbody tr button',function(){
       var data = oTable.api().row($(this).parents('tr')).data();       
       $("#apiId").val(data.id);
       $("#groupName").val(data.groupName);
       $("#apiName").val(data.apiName);
       $("#descr").val(data.descr);
       $("#price").val(data.price);   
    });
    //修改操作
	var editPrice = function(){		
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"admin/editPrice", 
         "data": $('#editPriceForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 handleAlerts("修改价格成功.","success","");		            	 
				 }
				 else{
					 handleAlerts("修改价格失败："+resp.info,"danger","");						 
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
		$("#apiId").val("");
		$('#edit_price').modal('hide');
    };
    
    var editPriceValidation = function() {
        var form = $('#editPriceForm');
        var errorDiv = $('.alert-danger', form);            
        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {                	
            	price: {required: true,number:true},        	 

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
            	editPrice();
            }
        });
    };  

    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
            handleTable();
            editPriceValidation();
        }

    };

}();