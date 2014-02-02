<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="net.afnf.blog.bean.AppConfig"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>${f:title(param.title)}</title>

<link rel="stylesheet" type="text/css" href="${as:url('/static/blog.min.css')}" />
<link rel="shortcut icon" type="image/x-icon" href="${as:url('/static/img/favicon.ico')}" />
<link title="<%= AppConfig.getInstance().getTitle() %>" rel="alternate" type="application/rss+xml" href="<c:url value='/rss.xml'/>" />
<meta name="author" content="afnf" />

<style type="text/css">
.headerdiv {
	background-image: url('${as:url("/static/img/top_800.jpg")}');
}

@media ( max-width : 700px) {
	.headerdiv {
		background-image: url('${as:url("/static/img/top_540.jpg")}');
	}
}

@media ( max-width : 540px) {
	.headerdiv {
		background-image: url('${as:url("/static/img/top_440.jpg")}');
	}
}

@media ( max-width : 330px) {
	.headerdiv {
		background-image: url('${as:url("/static/img/top_330.jpg")}');
	}
}
</style>

<!--[if lt IE 9]>
<script src="//oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="//oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->
<script>
	var afnfblog = {};
	afnfblog.contextRoot = "<%=request.getContextPath()%>";
</script>
</head>
<body>

	${param.body_content}

	<div class="footerdiv">
		<div class="subinfo">
			<a href="https://github.com/af-not-found/blog-java1" target="_blank">blog-java1</a> engine (build:<%=AppConfig.getInstance().getBuildDate()%>)
		</div>
	</div>

	<script src="${as:url('/static/jquery-1.10.2.min.js')}"></script>
	<script src="${as:url('/static/blog.min.js')}"></script>
	<script>
		$( document ).ready(function () {
			<c:if test="${pagingList != null}">
			    setTimeout(function(){
		   		    <%-- 遅延させないとwindow sizeが取れない？ --%>
			    	afnfblog.pagenator(${pagingList.totalPageCount}, ${pagingList.thisPage});
			    }, 30);
			</c:if>
			afnfblog.highlight();
			afnfblog.bindform();
		});
	</script>

	${param.footer_script}

	<div id="lean_overlay"></div>
	<div id="alert_modal">
		<div id="alert_text"></div>
		<button id="close_modal" class="btn btn-primary">close</button>
	</div>
</body>
</html>
