
<c:if test="${pagingList != null && pagingList.limit >= 1}">
	<div class="pagination_container">
		<ul class="pagination">
			<%-- template.jspで afnfblog.pagenatorを起動 --%>
		</ul>
	</div>
</c:if>


<c:if test="${prevMonth != null || nextMonth != null}">
	<div class="pagination_container">
		<ul class="pager">
			<c:if test="${nextMonth != null}">
				<li class="next"><a href="<c:url value="/m/${nextMonth.key}"/>">&larr; Newer</a></li>
			</c:if>
			<c:if test="${prevMonth != null}">
				<li class="previous"><a href="<c:url value="/m/${prevMonth.key}"/>">Older &rarr;</a></li>
			</c:if>
		</ul>
	</div>
</c:if>


