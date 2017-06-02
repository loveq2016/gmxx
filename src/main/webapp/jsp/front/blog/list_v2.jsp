<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>${blogType}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <style type="text/css">
    .spinner {
    width: 200px;
    height: 200px;
    position: relative;
    top: 500px;
    margin: 100px auto;
    }

    .double-bounce1,
    .double-bounce2 {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #3ABCFF;
    opacity: 0.6;
    position: absolute;
    top: 0;
    left: 0;
    -webkit-animation: bounce 2.0s infinite ease-in-out;
    animation: bounce 2.0s infinite ease-in-out;
    }

    .double-bounce2 {
    -webkit-animation-delay: -1.0s;
    animation-delay: -1.0s;
    }

    @-webkit-keyframes bounce {
    0%,
    100% {
    -webkit-transform: scale(0.0)
    }
    50% {
    -webkit-transform: scale(1.0)
    }
    }

    @keyframes bounce {
    0%,
    100% {
    transform: scale(0.0);
    -webkit-transform: scale(0.0);
    }
    50% {
    transform: scale(1.0);
    -webkit-transform: scale(1.0);
    }
    }
    </style>
</head>

<body>
    <div class="loading" style="display:block; width:100%; height:100%; position:fixed; left:0px; top:0px; z-index:9999; background-color: #fff">
    <div class="spinner">
    <div class="double-bounce1"></div>
    <div class="double-bounce2"></div>
    </div>
    </div>
    <!-- 代码开始 -->
    <div id="fix-both">
        <ul>
            <c:forEach var="item" varStatus="status" items="${menu.parent.children}">
		 	<c:if test="${!item.hide}"><li  ><a <c:if test="${item.name==blogType}">class="cur"</c:if> href="<%=basePath%>front/blog/list_v2.htm?blogType=${item.name}">${item.name}</a></li></c:if>
		 </c:forEach>
        </ul>
        <a class="ui-navigator-fixright" href="http://gmxx.goldskyer.com/front/index.htm">
          <img src="/images/home.png" /> 
        </a> <!-- 如果只需要左侧固定，请删除这个a标签 -->
    </div>
   
    <!--must content ul li,or shoupi-->
    <div class="newsdiv">
      <ul class="newslist">
          <c:forEach var="blog" items="${blogs}" varStatus="items">
		        <a href="<%=basePath%>front/blog/view.htm?id=${blog.id}">
		        <li class="newscontent">
		          <img class="newsimage" src="${blog.thumbImage}" />
		          <p class="newstitle">${blog.title}</p>
		          <p class="newsdate"><fmt:formatDate value="${blog.updateDate}" pattern="yyyy-MM-dd"/></p>
		        </li>
		        </a>
	        </c:forEach>   
      </ul>
    </div>
    
   <%@ include file="/jsp/front/widgets/list_load.jsp"%> 

    
    <script src="/dist/common.js"></script>
    <script src="/dist/schoolnews.js"></script>
</body>
</html>
