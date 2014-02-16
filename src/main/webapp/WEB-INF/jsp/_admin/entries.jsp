<c:import url="/WEB-INF/jsp/common/template.jsp">

	<c:param name="title">entries</c:param>

	<c:param name="body_content">
		<%@include file="/WEB-INF/jsp/common/admin_menu.jsp"%>

		<c:if test="${entry.id != null}">
			<div class="container mainparent entry_edit_parent_div">
				<div class="col-md-3 sidebar entry_edit_form_div">
					<form:form modelAttribute="entry" cssClass="ajaxform form-horizontal validate">
						<form:hidden path="id" />
						<form:hidden path="contentHtml" id="contentHtml" />
						<label>title</label>
						<form:input path="title" class="form-control input_entry_text required" minlength="2" />
						<br />
						<label>tags</label>
						<form:input path="tags" class="form-control input_entry_text" />
						<br />
						<form:textarea path="content" cssClass="form-control textarea_entry required" minlength="2" />
						<br />
						<form:radiobutton path="state" id="r0" value="0" cssClass="form-control radio_common stateRadio" />
						<label for="r0">draft</label>
						<form:radiobutton path="state" id="r1" value="1" cssClass="form-control radio_common stateRadio" />
						<label for="r1">normal</label>
						<form:radiobutton path="state" id="r2" value="2" cssClass="form-control radio_common stateRadio" />
						<label for="r2">delete</label>
						<br />
						<c:if test="${entry.postdate != null}">
							<span>${f:date(entry.postdate)}</span>
							<br />
						</c:if>

						<div style="margin-top: 20px">
							<input type="submit" class="btn btn-primary" name="post" value="post" id="submit_btn" /> <span class="ajaxret"></span>
						</div>
					</form:form>
				</div>
				<div class="col-md-9 maindiv preview_container">
					<button class="btn_width btn btn-default btn-primary">675px</button>
					<button class="btn_width btn btn-default">568px</button>
					<button class="btn_width btn btn-default">360px</button>
					<button class="btn_width btn btn-default">314px</button>
					<!-- 
					<button class="btn_width btn btn-default btn-primary">630px</button>
					<button class="btn_width btn btn-default">523px</button>
					<button class="btn_width btn btn-default">344px</button>
					<button class="btn_width btn btn-default">304px</button>
					 -->
					<div class="preview entry_content border_table_container"></div>
				</div>
			</div>
		</c:if>

		<c:if test="${entry.id == null}">
			<div class="container admindiv">
				<script>
					afnfblog.pagingPath = "/_admin/entries?page=";
				</script>
				<%@include file="/WEB-INF/jsp/common/pagination.jsp"%>

				<div class="summary_entries_container">
					<c:forEach var="entry" items="${pagingList.entries}">
						<%@include file="/WEB-INF/jsp/common/entry_flagment.jsp"%>
					</c:forEach>
				</div>

				<%@include file="/WEB-INF/jsp/common/pagination.jsp"%>
			</div>
		</c:if>

		<script src="${as:url('/static/admin.min.js')}"></script>
	</c:param>

	<c:param name="footer_script">
		<c:set var="btn_selector">
			<c:if test="${entry.id == -1}">.btn_new</c:if>
			<c:if test="${entry.id != -1}">.btn_entries</c:if>
		</c:set>
		<script>
			afnfblog.admin_entry_init("${btn_selector}");
		</script>
	</c:param>

</c:import>
