
<div class="text-right">

	<c:if test="${isAdminPage == true && isSummaryPage == true}">
		<c:if test="${entry.state == 0}">
			<span class="state_draft">draft</span>
		</c:if>
		<c:if test="${entry.state == 2}">
			<span class="state_deleted">deleted</span>
		</c:if>
	</c:if>
	
	<span class="tags_in_entry"> <c:forEach var="tag" items="${entry.tagList}">
			<a href='<c:url value="/t/${f:ue(tag)}" />'>${f:xml(tag)}</a>
		</c:forEach>
	</span>

	<c:if test="${isAdminPage == false && isProductionAndNormalSite == true}">
		<span class="indent_short"> <a href="http://b.hatena.ne.jp/entry/http://blog.afnf.net/blog/${entry.id}"> <img
				src="http://b.hatena.ne.jp/entry/image/http://blog.afnf.net/blog/${entry.id}" height="13px">
		</a>
		</span>
	</c:if>

	<span class="indent_short subinfo">${f:date(entry.postdate)}</span>
</div>

