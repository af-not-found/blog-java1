
<c:set var="path">
	<c:if test="${isAdminPage==true}">_admin/entries/</c:if>
</c:set>


<%-- 一覧表示 --%>
<c:if test="${isSummaryPage == true}">
	<div class="summary_entry_container">
		<h3 class="summary_entry_title">
			<a href='<c:url value="/${path}${entry.id}"/>'>${f:xml(entry.title)}</a>
		</h3>

		<div class="indent">
			<span class="subinfo">${f:date(entry.postdate)}</span> <span class="tags_in_entry"> <c:if
					test="${isAdminPage==false}">
					<c:forEach var="tag" items="${entry.tagList}">
						<a href='<c:url value="/t/${f:ue(tag)}" />'>${f:xml(tag)}</a>
					</c:forEach>
				</c:if>
			</span>

			<c:if test="${isAdminPage==true}">
				<c:if test="${entry.state == 0}">
					<span class="state_draft">draft</span>
				</c:if>
				<c:if test="${entry.state == 2}">
					<span class="state_deleted">deleted</span>
				</c:if>
			</c:if>
		</div>
		<c:if test="${isAdminPage==false && entry.commentCount > 0}">
			<div class="indent subinfo cmcount"><span data-t="blog.comments">comments</span>(${entry.commentCount})</div>
		</c:if>
	</div>
</c:if>

<%-- 単一表示 --%>
<c:if test="${isSummaryPage == false}">
	<div class="entry_container">
		<h3 class="entry_title">
			<a href='<c:url value="/${path}${entry.id}"/>'>${f:xml(entry.title)}</a>
		</h3>

		<div class="text-right">
			<span class="subinfo">${f:date(entry.postdate)}</span> <span class="tags_in_entry"> <c:forEach var="tag"
					items="${entry.tagList}">
					<a href='<c:url value="/t/${f:ue(tag)}" />'>${f:xml(tag)}</a>
				</c:forEach>
			</span>
		</div>

		<div class="entry_content border_table_container">${entry.contentHtml}</div>

		<div class="text-right">
			<span class="subinfo">${f:date(entry.postdate)}</span> <span class="tags_in_entry"> <c:forEach var="tag"
					items="${entry.tagList}">
					<a href='<c:url value="/t/${f:ue(tag)}" />'>${f:xml(tag)}</a>
				</c:forEach>
			</span>
		</div>

		<div style="margin-top: 40px" class="cmcount"><span data-t="blog.comments">comments</span>(${entry.commentCount})</div>
	</div>
</c:if>
