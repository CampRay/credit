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
var ManagersTable = function () {
	var oTable;	
	var selected = [];
	var table=$('#adminusers_table');
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
               {'render':function(data,type,row){
	               	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
	               }
               },
	           {data: "adminId"  },
	           {data: "email" },
	           {data: "createdTimeStr",defaultContent:""},
	           {data: "balance" },
	           {'render':function(data,status,row){
	        				var tem = row.status;
	        				var str = '';
	        				if(tem==1){
	        					str = '可用';
	        				}else if(tem==0){
	        					str = '禁用';
	        				}
	        				return str;
	        			}
	           },
	           {'render':function(data,status,row){
	   				var tem = row.adminId;
	   				var str='<div class="text-center"><button class="btn btn-sm blue" data-toggle="modal"  href="#add_money">充值</button></div>'
					return str;
				}
	           },
	           
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"manager/managersList?rand="+Math.random(),
	        "fnDrawCallback":function(oSetting){
	        	selected=[];
	        }
		});				 				       
		 
	};
	
	$("#openActiveadminsModal").on("click",function(event){
		if(selected.length==0){
			handleAlerts("请先选择想要启用的记录.","warning","");				
			return false;
		}
	});
	
	$("#openDeactiveadminsModal").on("click",function(event){
		if(selected.length==0){
			handleAlerts("请先选择想要禁用的记录.","warning","");				
			return false;
		}
	});
	
	//激活规则
	$('#activateBtn').on('click', function (e) {
		$.ajax( {
         "dataType": 'json', 
         "type": "POST", 
         "url": rootURI+"manager/activateusers/"+selected.join(), 
         "success": function(data,status){
        	 if(status == "success"){					
				 if(data.status){
					 selected=[];						 
	            	oTable.api().draw();
	            	oTable.$('th span').removeClass();
	            	 handleAlerts("账号启用成功.","success","");
				 }
				 else{
					 alert(data.info);
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
    }); 
	//禁用规则
	$('#deactivateBtn').on('click', function (e) {
		$.ajax( {
         "dataType": 'json', 
         "type": "POST", 
         "url": rootURI+"manager/deactivateusers/"+selected.join(), 
         "success": function(data,status){
        	 if(status == "success"){					
				 if(data.status){
					 selected=[];						 
	            	 oTable.api().draw();
	            	 oTable.$('th span').removeClass();
	            	 handleAlerts("账号禁用成功.","success","");
				 }
				 else{
					 alert(data.info);
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
    }); 
	//搜索表单提交操作
	$("#searchForm").on("submit", function(event) {
		event.preventDefault();
		var jsonData=$(this).serializeJson();
		var jsonDataStr=JSON.stringify(jsonData);			
		oTable.fnFilter(jsonDataStr);
		return false;
	});			
			           
	//全选
	
	$(".group-checkable").on('change',function () {
        var set = jQuery(this).attr("data-set");
        var checked = jQuery(this).is(":checked");
        selected=[];
        if(checked){            	
            var api=oTable.api();            
            jQuery(set).each(function () {            	
            	var data = api.row($(this).parents('tr')).data();
            	var ids=data.adminId;
                var index = $.inArray(ids, selected);
                selected.push( ids );
                $(this).attr("checked", true);
                $(this).parents('tr').addClass("active");
                $(this).parents('span').addClass("checked");
            });
        }
        else{
        	jQuery(set).removeAttr("checked");
        	jQuery(set).parents('tr').removeClass("active");
        	jQuery(set).parents('span').removeClass("checked");
        }
        jQuery.uniform.update(set);
    });
        
    
    //单选
    table.on('change', 'tbody tr .checkboxes', function () {
        $(this).parents('tr').toggleClass("active");            
        var data = oTable.api().row($(this).parents('tr')).data();
        var id = data.adminId;
        var index = $.inArray(id, selected);     
        if ( index === -1 ) {
            selected.push( id );
            $(this).parents('span').addClass("checked");
            $(this).attr("checked","checked");
        } else {
            selected.splice( index, 1 );
            $(this).parents('span').removeClass("checked");
            $(this).removeAttr("checked");
        }
    });
               	
	
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
       $("#addMoneyId").val(data.adminId);
       $("#addMoneyAmount").val("");       
    });
    //添加操作
	var AddMoney = function(){		
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"manager/addMoney", 
         "data": $('#addMoneyForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 handleAlerts("用户充值成功.","success","");		            	 
				 }
				 else{
					 handleAlerts("用户充值失败："+resp.info,"danger","");						 
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
             adminId: {required: true,},
             balance: {required: true,number:true},        	 

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
    
    //添加操作
	var AddUsers = function(){		
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"manager/addUsers", 
         "data": $('#addUsersForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 handleAlerts("添加用户成功.","success","");		            	 
				 }
				 else{
					 handleAlerts("添加用户失败："+resp.info,"danger","");						 
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
		$('#add_users').modal('hide');
    };
    
    var AddUsersValidation = function() {
        var form = $('#addUsersForm');
        var errorDiv = $('.alert-danger', form);            
        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {
             adminId: {
            	required: true,
            	minlength:5,
            	maxlength:20,
                		},
             password: {
        		
        		required: true,
        		minlength:6,
        		maxlength:20,        	
    				},
        	 email: {        		
        		required: true,
        		email:true,        		
    				}

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
            	AddUsers();
            }
        });
    };
    
	//编辑表单提交操作
	var EditUsers= function() {
	  $.ajax( {
         "dataType": 'json', 
         "type": "POST", 
         "url": rootURI+"manager/editUsers", 
         "data" :$('#editUsersForm').serializeJson(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){
					 selected=[];
	            	 oTable.api().draw();
	            	 handleAlerts("编辑用户数据成功.","success","");
				 }
				 else{
					 handleAlerts("编辑用户数据失败:"+resp.info,"danger","");
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
	  $('#edit_users').modal('hide');
	};
		
            
	var EditUsersValidation = function() {
		var form = $('#editUsersForm');
		var errorDiv = $('.alert-danger', form);            
		form.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block help-block-error', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",  // validate all fields including form hidden input                
			rules: {
				adminId: {
					required: true,
					minlength:5,
					maxlength:20,
            			},
            	password: {
            		minlength:6,
            		maxlength:20,
					},
				email: {
					required: true,
					email:true,
				}

		   },
        invalidHandler: function (event, validator) { //display error alert on form submit              
            errorDiv.show();                    
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error'); 
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error'); 
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
        	EditUsers();
        }
    });
  };
    

    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable();  
        	AddUsersValidation();
        	EditUsersValidation();  
        	AddMoneyValidation();
        }

    };

}();
