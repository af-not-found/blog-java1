<%@page import="net.afnf.blog.bean.AppConfig"%>
<c:import url="/WEB-INF/jsp/common/template.jsp">

	<c:param name="title">login</c:param>

	<c:param name="body_content">

		<div class="container login_container">

			<c:if test="${param.faliled != null}">
				<div class="login_info" data-t="blog.login_failed">login failed</div>
			</c:if>

			<form name="f" action="<c:url value="/_admin/pub/auth"/>" class="form-horizontal validate" method="POST">

				<div class="form-group">
					<label>id</label> <input type="text" name="username" class="form-control input_short_text required" minlength="4" />
				</div>

				<div class="form-group">
					<label>password</label> <input type="password" name="password" class="form-control input_short_text required" minlength="4" />
				</div>

				<c:set var="isDevelopment"><%=AppConfig.getInstance().isDemoSite()%></c:set>
				<c:if test="${isDevelopment == true}">
					<div class="demo_password" data-t="blog.demo_password"></div>
				</c:if>

				<div>
					<input type="submit" class="btn btn-primary" name="post" value="login" />
				</div>
			</form>
		</div>
	</c:param>
</c:import>
