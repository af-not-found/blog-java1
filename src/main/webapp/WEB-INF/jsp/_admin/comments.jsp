<c:import url="/WEB-INF/jsp/common/template.jsp">

	<c:param name="title">comments</c:param>

	<c:param name="body_content">
		<%@include file="/WEB-INF/jsp/common/admin_menu.jsp"%>
		<div class="container admindiv">
			<script>
				afnfblog.pagingPath = "/_admin/comments?page=";
			</script>
			<%@include file="/WEB-INF/jsp/common/pagination.jsp"%>

			<hr />

			<div class="comments_container">
				<c:forEach var="comment" items="${pagingList.comments}" varStatus="varStatus">
					<div id='c${comment.id}'>
						${f:xml(comment.name)} <span class="subinfo indent">${f:date(comment.postdate)}</span>
						<c:choose>
							<c:when test="${comment.state==0}">
								<c:set var="cssval">waiting_comment</c:set>
								<c:set var="checked1"></c:set>
								<c:set var="checked2"></c:set>
							</c:when>
							<c:when test="${comment.state==1}">
								<c:set var="cssval">normal_comment shortened_comment</c:set>
								<c:set var="checked1">checked="checked"</c:set>
								<c:set var="checked2"></c:set>
							</c:when>
							<c:when test="${comment.state==2}">
								<c:set var="cssval">deleted_comment shortened_comment</c:set>
								<c:set var="checked1"></c:set>
								<c:set var="checked2">checked="checked"</c:set>
							</c:when>
						</c:choose>

						<div class='comment_content ${cssval}'>${f:comment(comment.content)}</div>
						<div class="subinfo">${f:xml(comment.clientinfo)}</div>

						<form:form modelAttribute="comment" cssClass="ajaxform">
							<span style="padding-right: 10px"> <a href='<c:url value="/${comment.entryid}"/>'>&nbsp;${comment.entryid}&nbsp;</a>-
								${comment.id}
							</span>
							<input type="hidden" name="id" value="${comment.id}" />
							<input type="radio" name="state" id="c${comment.id}_r1" value="1" class="form-control radio_common stateRadio" ${checked1} />
							<label for="c${comment.id}_r1">approve</label>
							<input type="radio" name="state" id="c${comment.id}_r2" value="2" class="form-control radio_common stateRadio" ${checked2} />
							<label for="c${comment.id}_r2">delete</label>
							<span class="ajaxret"></span>
						</form:form>

						<hr />
					</div>
				</c:forEach>
			</div>

			<%@include file="/WEB-INF/jsp/common/pagination.jsp"%>
		</div>

		<script src="${as:url('/static/admin.min.js')}"></script>
	</c:param>

	<c:param name="footer_script">
		<script>
			afnfblog.admin_comment_init();
		</script>
	</c:param>
</c:import>
