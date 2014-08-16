<div class="col-md-3 sidebar">

	<div>
		<h3 class="sb_header">latests</h3>
		<div class="sb_recents">
			<c:forEach var="entry" items="${entryCache.recents}">
				<div class="sb_entry_title" title="${f:xml(entry.title)}">
					<a href='<c:url value="/${entry.id}" />'>${f:xml(entry.title)}</a>
				</div>
			</c:forEach>
			<div class="sb_more">
				<a href='<c:url value="/" />'>more...</a>
			</div>
		</div>
	</div>

	<hr />

	<div>
		<h3 class="sb_header">tags</h3>
		<div class="sb_tags">
			<c:set var="tsize" value="${entryCache.tagList.size()}" />
			<div class='<c:if test="${tsize >= 12}" >sb_clop</c:if>'>
				<c:forEach var="tagInfo" items="${entryCache.tagList}">
					<div class="sb_tag <c:if test="${currentTag == tagInfo.disp}">sb_matched</c:if>">
						<a href='<c:url value="/t/${f:ue(tagInfo.key)}" />'>${f:xml(tagInfo.disp)}</a> (${tagInfo.count})
					</div>
				</c:forEach>
			</div>
			<c:if test="${tsize >= 12}">
				<div class="sb_more">
					<a href="#" onclick="return afnfblog.expand(this);">more...</a>
				</div>
			</c:if>
		</div>
	</div>

	<hr />

	<div>
		<h3 class="sb_header">archive</h3>
		<div class="sb_archive">
			<div>
				<a href='<c:url value="/" />'>ALL</a> (${entryCache.totalNormalCount})
			</div>
			<c:set var="msize" value="${entryCache.monthlyList.size()}" />
			<div class='<c:if test="${msize >= 12}" >sb_clop</c:if>'>
				<c:forEach var="month" items="${entryCache.monthlyList}">
					<div class="sb_month <c:if test="${currentMonth == month.disp}">sb_matched</c:if>">
						<a href='<c:url value="/m/${month.key}" />'>${f:xml(month.disp)}</a> (${month.count})
					</div>
				</c:forEach>
			</div>
			<c:if test="${msize >= 12}">
				<div class="sb_more">
					<a href="#" onclick="return afnfblog.expand(this);">more...</a>
				</div>
			</c:if>
		</div>
	</div>

	<hr />

	<div>
		<h3 class="sb_header">author</h3>
		<div class="sb_content">
			afnfという名前でやってますよ
			<div>
				<img src='${as:url("/static/img/email1.png")}' /> <%-- <img src='${as:url("/static/img/email2.png")}' /> --%>
			</div>
		</div>
	</div>

	<hr />

	<div>
		<h3 class="sb_header">links</h3>
		<div class="sb_content">
			<div>
				<a href="http://blog.afnf.net/blogdemo/" target="_blank">ブログデモサイト</a>
			</div>
			<div>
				<a href="http://blog.afnf.net/blogdemo/_admin/" target="_blank">デモサイト管理画面</a>
			</div>
			<div>
				<a href="<c:url value='/rss.xml'/>" target="_blank">RSS</a>
			</div>
			<div>
				<a href="https://github.com/af-not-found/" target="_blank">github af-not-found</a>
			</div>
			<div>
				<a href="https://play.google.com/store/apps/details?id=com.appspot.afnf4199ga.twawm" target="_blank">twawm for WM3800R</a>
			</div>
			<div>
				<a href="https://play.google.com/store/apps/details?id=net.afnf.and.twawm2" target="_blank">twawm2 for NAD11</a>
			</div>
			<div>
				<a href="http://w.livedoor.jp/twawm/" target="_blank">twawm wiki</a>
			</div>
			<div>
				<a href="https://play.google.com/store/apps/details?id=com.appspot.afnf4199ga.wmgraph" target="_blank">WM Graph</a>
			</div>
		</div>
	</div>

</div>
