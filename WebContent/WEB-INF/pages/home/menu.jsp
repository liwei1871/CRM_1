<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctp }/static/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctp }/static/jquery/themes/icon.css">
<script type="text/javascript" src="${ctp }/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctp }/static/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function(){
		
		$('#tt').tree({
			onClick: function(node){
				//alert(node.text);  // 在用户点击的时候提示
				
				if(node.url){
					//如果存在URL
					//alert(node.attributes.url);
					//改变兄弟窗口（frame）content的src的值为这个URL
					//id为content的frame
					window.parent.document.getElementById("content").src = node.url;
				}
				
			}
		});
		
	});
</script>
</head>
<body>
	<ul id="tt" class="easyui-tree" data-options="url:'${ctp }/user/navigate',method:'get',animate:true"></ul>
</body>
</html>