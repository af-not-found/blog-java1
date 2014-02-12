<c:import url="/WEB-INF/jsp/common/template.jsp">

	<c:param name="title">register</c:param>

	<c:param name="body_content">

		<div class="login_container">

			<div class="login_info" data-t="blog.reg_admin">reg_admin</div>

			<form:form modelAttribute="user" cssClass="ajaxform form-horizontal validate">

				<div class="form-group">
					<label>name</label> <input type="text" name="username" class="form-control input_short_text required" minlength="4" />
				</div>

				<div class="form-group">
					<label>password</label> <input type="password" name="password" class="form-control input_short_text required" minlength="4" />
				</div>

				<div>
					<input type="submit" class="btn btn-primary" name="post" value="register" />
				</div>
			</form:form>
		</div>
	</c:param>

	<c:param name="footer_script">
		<script>
			afnfblog.ajaxSuccess = function(thisform, data) {
				afnfblog.alert(t("blog.update_ok"), afnfblog.reloadPage);
			};
		</script>
	</c:param>
</c:import>
