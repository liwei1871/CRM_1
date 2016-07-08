<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 
	attribute定义属性，使用该标签时传入的属性，在这里可以使用
	name:属性名
	type:表示属性名需要传入的属性值的类型（全类名）
	required:表示该属性是否为必须
	rtexprvalue：表示能否接收运行表达式的值。
	
	
	在页面上引用自定义标签：
	
 -->
<%@ attribute name="page" type="com.atguigu.crm.models.Page" required="true" rtexprvalue="true"%>
<%@ attribute name="queryString" type="java.lang.String" required="false" rtexprvalue="true" %>


<div style="text-align: right; padding: 6px 6px 0 0;">

	☆☆共 ${page.totalRecordNo } 条记录 &nbsp;&nbsp; 当前第 ${page.pageNo } 页/共
	${page.totalPageNo } 页 &nbsp;&nbsp;

	<c:if test="${page.hasPrev }">
		&nbsp;&nbsp;
		<a href="?pageNo=1${queryString }">首页</a>
		&nbsp;&nbsp;
		<a href="?pageNo=${page.prev }${queryString }">上一页</a>
	</c:if>

	<c:if test="${page.hasNext }">
		&nbsp;&nbsp;
		<a href="?pageNo=${page.next }${queryString }">下一页</a>
		&nbsp;&nbsp;
		<a href="?pageNo=${page.totalPageNo }${queryString }">末页</a>
	</c:if>

	&nbsp;&nbsp; 转到 <input id="pageNo" size='1' /> 页 &nbsp;&nbsp;

</div>

<script type="text/javascript" src="${ctp}/static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript">

	$(function(){
		
		$("#pageNo").change(function(){
			
			var pageNo = $(this).val();
			var reg = /^\d+$/;
			if(!reg.test(pageNo)){
				$(this).val("");
				alert("输入的页码不合法");
				return;
			}
			
			var pageNo2 = parseInt(pageNo);
			if(pageNo2 < 1 || pageNo2 > parseInt("${page.totalPageNo }")){
				$(this).val("");
				alert("输入的页码不合法");
				return;
			}
			//查询条件需要放入到 class='condition' 的隐藏域中. 
			window.location.href = window.location.pathname + "?pageNo=" + pageNo2 +"${queryString }";
			
		});
	})
</script>