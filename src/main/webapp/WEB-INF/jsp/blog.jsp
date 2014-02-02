<%@page import="net.afnf.blog.bean.AppConfig"%>
<c:import url="/WEB-INF/jsp/common/template.jsp">

	<c:param name="title">
		<c:if test="${entry != null}">${f:xml(entry.title)}</c:if>
		<c:if test="${currentTag != null}">${f:xml(currentTag)}</c:if>
		<c:if test="${currentMonth != null}">${f:xml(currentMonth)}</c:if>
	</c:param>

	<c:param name="body_content">

		<div class="headerdiv">
			<h1>
				<a href="<c:url value="/"/>"><%=AppConfig.getInstance().getTitle()%></a>
			</h1>
		</div>

		<div class="container mainparent">
			<div class="col-md-9 maindiv">

				<%-- 一覧表示 --%>
				<c:if test="${entry == null}">

					<c:if test="${currentTag != null}">
						<div class="matched_label">
							<div class="label label-success">${f:xml(currentTag)}(${pagingList.totalCount})</div>
						</div>
						<script>
							afnfblog.pagingPath = "/t/${f:ue(currentTag)}?page=";
						</script>
					</c:if>
					<c:if test="${currentMonth != null}">
						<div class="matched_label">
							<div class="label label-success">${f:xml(currentMonth)}(${pagingList.totalCount})</div>
						</div>
					</c:if>

					<div class="summary_entries_container">
						<c:forEach var="entry" items="${pagingList.entries}">
							<%@include file="/WEB-INF/jsp/common/entry_flagment.jsp"%>
						</c:forEach>
					</div>

					<%@include file="/WEB-INF/jsp/common/pagination.jsp"%>
				</c:if>

				<%-- 単一表示 --%>
				<c:if test="${entry != null}">
					<%@include file="/WEB-INF/jsp/common/entry_flagment.jsp"%>

					<div class="comments_container">
						<hr />
						<c:forEach var="comment" items="${comments}" varStatus="varStatus">
							<div class="comment">
								<c:if test="${comment.state == 0}">
									<span data-t="blog.wait">(承認待ち)</span>
									<span class="subinfo indent">${f:date(comment.postdate)}</span>
								</c:if>
								<c:if test="${comment.state == 1}">
									${f:xml(comment.name)} 
									<span class="subinfo indent">${f:date(comment.postdate)}</span>
									<div class="comment_content">${f:comment(comment.content)}</div>
								</c:if>
								<hr />
							</div>
						</c:forEach>

						<form:form modelAttribute="comment" cssClass="ajaxform form-horizontal validate">
							<div class="form-group">
								<label data-t="blog.yourname">お名前</label>
								<form:input path="name" cssClass="form-control input_short_text required" size="16" minlength="2" maxlength="100" />
								<input type="submit" name="post" value="submit" class="btn btn-primary" data-t="blog.submit">
							</div>
							<form:textarea path="content" cssClass="form-control textarea_comment required" rows="3" minlength="2" maxlength="2000" />
						</form:form>
					</div>
				</c:if>
			</div>

			<%@include file="/WEB-INF/jsp/common/sidebar.jsp"%>
		</div>
	</c:param>

	<c:param name="footer_script">
		<script>
			afnfblog.ajaxSuccess = function(thisform, data) {
				thisform[0].reset();
				afnfblog.alert(t("blog.comment_ok"), function() {
					location.reload(false);
				});
			};

			(function(i, s, o, g, r, a, m) {
				i['GoogleAnalyticsObject'] = r;
				i[r] = i[r] || function() {
					(i[r].q = i[r].q || []).push(arguments)
				}, i[r].l = 1 * new Date();
				a = s.createElement(o), m = s.getElementsByTagName(o)[0];
				a.async = 1;
				a.src = g;
				m.parentNode.insertBefore(a, m)
			})(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');
			ga('create', 'UA-46276114-3', 'afnf.net');
			ga('send', 'pageview');
		</script>
	</c:param>

</c:import>
