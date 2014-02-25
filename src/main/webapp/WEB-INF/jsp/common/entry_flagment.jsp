
<c:set var="path">
	<c:if test="${isAdminPage==true}">_admin/entries/</c:if>
</c:set>

<%-- 一覧表示 --%>
<c:if test="${isSummaryPage == true}">
	<div class="summary_entry_container">
		<h3 class="summary_entry_title">
			<a href='<c:url value="/${path}${entry.id}"/>'>${f:xml(entry.title)}</a>
		</h3>

		<%@include file="/WEB-INF/jsp/common/entry_meta_flagment.jsp"%>

		<c:if test="${isAdminPage == false && entry.commentCount > 0}">
			<div class="text-right subinfo cmcount">
				<span data-t="blog.comments">comments</span>(${entry.commentCount})
			</div>
		</c:if>
	</div>
</c:if>

<%-- 単一表示 --%>
<c:if test="${isSummaryPage == false}">
	<div class="entry_container">
		<h3 class="entry_title">
			<a href='<c:url value="/${path}${entry.id}"/>'>${f:xml(entry.title)}</a>
		</h3>

		<%@include file="/WEB-INF/jsp/common/entry_meta_flagment.jsp"%>

		<div class="entry_content border_table_container">${entry.contentHtml}</div>

		<%@include file="/WEB-INF/jsp/common/entry_meta_flagment.jsp"%>

		<div style="margin-top: 40px" class="cmcount">
			<span data-t="blog.comments">comments</span>(${entry.commentCount})
		</div>
	</div>
</c:if>
