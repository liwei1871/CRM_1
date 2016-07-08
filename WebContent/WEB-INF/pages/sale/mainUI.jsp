<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="atguigu"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售机会管理</title>
<script type="text/javascript">
	$(function(){
		
		$("#new").click(function(){
			
			window.location.href="${ctp}/sale/chance/";
			return false;
			
		});
		
		$("img[id^='delete-']").click(function(){
			var lable = $(this).next(":hidden").val();
			var flag = confirm("确认是否删除"+lable+"的信息?")
			
			if(!flag){
				return ;
			}
			
			var id = this.id.split("-")[1];
			alert(id);
			
			//删除
			$("#hiddenForm").attr("action","${ctp}/sale/chance/" + id + "?pageNo=${page.pageNo}");
			
			$("#_method").val("DELETE");
			
			$("#hiddenForm").submit();
			
		});
		
	});
</script>

</head>
<body class="main">
	<form action="${ctp }/sale/chance/list" method="post">
		<div class="page_title">销售机会管理</div>

		<div class="button_bar">
			<button class="common_button" id="new">新建</button>
			<button class="common_button" onclick="document.forms[1].submit();">
				查询</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">客户名称</th>
				<td class="input_content">
					<input type="text" name="search_LIKES_custName" />
				</td>
				<th class="input_title">概要</th>
				<td class="input_content">
					<input type="text" name="search_LIKES_title" />
				</td>
				<th class="input_title">联系人</th>
				<td class="input_content">
					<input type="text" name="search_LIKES_contact" />
				</td>
			</tr>
		</table>

		<br />

		<c:if test="${empty page.list }">
			没有任何记录
		</c:if>
		<c:if test="${!empty page.list }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>编号</th>
					<th>客户名称</th>
					<th>概要</th>
					<th>联系人</th>
					<th>联系人电话</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${page.list }" var="saleChance">
					<tr>
						<td class="list_data_number">${saleChance.id }</td>
						<td class="list_data_text">${saleChance.custName }</td>
						<td class="list_data_text">${saleChance.title }</td>
						<td class="list_data_text">${saleChance.contact }</td>
						<td class="list_data_text">${saleChance.contactTel }</td>
						<td class="list_data_text"><fmt:formatDate
								value="${saleChance.createDate }" pattern="yyyy-MM-dd" /></td>
						<td class="list_data_op">
							<img onclick="window.location.href='${ctp}/chance/dispatch?id=2000'"
								title="指派" src="${ctp}/static/images/bt_linkman.gif"
								class="op_button" /> 
							<img onclick="window.location.href='${ctp}/sale/chance/${saleChance.id }?pageNo=${page.pageNo }'"
								title="编辑" src="${ctp}/static/images/bt_edit.gif"
								class="op_button" /> 
							<img id="delete-${saleChance.id }"
								title="删除" src="${ctp}/static/images/bt_del.gif"
								class="op_button" />
							<input type="hidden" value="${saleChance.custName }"/>
						</td>
					</tr>
				</c:forEach>
			</table>
			<atguigu:pagetag page="${page }"></atguigu:pagetag>
		</c:if>

	</form>

</body>
</html>