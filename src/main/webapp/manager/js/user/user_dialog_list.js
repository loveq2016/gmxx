curNode = new Object(); // 定义当前选中的节点
curNode.id="";
var setting = {
	check : {
		enable : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			curNode = treeNode;
			oTable.api().ajax.reload();
		}
	}
};

$(function() {

	$.fn.zTree.init($("#treeDemo"), setting, zData);
	zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.expandAll(true);

	oTable = $('#paper-table')
			.dataTable(
					{
						"processing" : false,
						"serverSide" : true,
						rowId : "[0]",
						"ajax" : {
							"url" : "/manager/user/dialog/list_data.json",
							data : {
								"dept" : function() {
									return curNode.id;
								}
							}
						},
						"language" : {
							"url" : "http://cdn.datatables.net/plug-ins/1.10.11/i18n/Chinese.json"
						},
						"columnDefs" : [ {
							"render" : function(data, type, row) {

								return '<input type="checkbox" class="checkBtn" name="'+row[2]+'" value="'
										+ data+ '" />';
							},
							"targets" : 0
						} ]
					});
	$('.checkBtn').live("click",function(){
		if($(this).attr("checked")=="checked"){
			$('.accountIds').find('span[sId="'+$(this).attr("value")+'"]').remove();
			$('.accountIds').append('<span class="label label-success"  sId="'+$(this).attr("value")+'">'+$(this).attr('name')+'</span>');
		}else{
			$('.accountIds').find('span[sId="'+$(this).attr("value")+'"]').remove();
		}		
	});
	
	$('#mysave').live('click',function(){
		 append="";
		$('.accountIds span').each(function(index,e){
				
			append+=$(this).attr('sid')+",";
		});
		console.log(append);
		if(append.length>0)
		{
			append=append.substring(0,append.length-1);
		}
		$(".poped",parent.document).val(append);
		top.$.jBox.close("chooseAccountDialog");
	});
});