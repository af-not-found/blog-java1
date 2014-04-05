<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="net.afnf.blog.bean.AppConfig"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>${f:title(param.title)}</title>

<!--[if lt IE 9]>
<script src="//oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="//oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->
<script src="${as:url('/static/jquery-1.11.0.min.js')}"></script>
<script src="${as:url('/static/blog.min.js')}"></script>
<script>
	afnfblog.contextRoot = "<%=request.getContextPath()%>";
	<c:if test="${pagingList != null}">
		afnfblog.totalPageCount = ${pagingList.totalPageCount};
		afnfblog.thisPage = ${pagingList.thisPage};
	</c:if>
</script>

<link rel="stylesheet" type="text/css" href="${as:url('/static/blog.min.css')}" />
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

<link rel="shortcut icon" type="image/x-icon" href="${as:url('/static/img/favicon.ico')}" />

<link title="<%=AppConfig.getInstance().getTitle()%>" rel="alternate" type="application/rss+xml" href="<c:url value='/rss.xml'/>" />
<meta name="author" content="afnf" />

</head>
<body>

	${param.body_content}

	<div class="footerdiv">
		<div class="subinfo">
			<a href="https://github.com/af-not-found/blog-java1" target="_blank">blog-java1</a> engine (build:<%=AppConfig.getInstance().getBuildDate()%>)
		</div>
	</div>

	${param.footer_script}

	<div style="display: none;" id="lean_overlay"></div>
	<div style="display: none;" id="alert_modal">
		<div id="alert_text"></div>
		<button id="close_modal" class="btn btn-primary">close</button>
	</div>
</body>
</html>
