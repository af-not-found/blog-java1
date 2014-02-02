
<%@page import="net.afnf.blog.bean.EntryCache"%>
<c:import url="/WEB-INF/jsp/common/template.jsp">

	<c:param name="title">cache</c:param>

	<c:param name="body_content">
		<%@include file="/WEB-INF/jsp/common/admin_menu.jsp"%>
		<div class="container admindiv">

			<div class="cache_container border_table_container">
				<table>
					<thead>
						<tr>
							<th>name</th>
							<th>value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>last modified</td>
							<td>${f:time(cache.lastModified)}</td>
						</tr>
						<tr>
							<td>cache updated</td>
							<td>${f:time(cache.updated)}</td>
						</tr>
						<tr>
							<td>totalNormal count</td>
							<td class="totalNormalCount">${cache.totalNormalCount}</td>
						</tr>
						<tr>
							<td>tagList count</td>
							<td class="tagCount">${cache.tagList.size()}</td>
						</tr>
						<tr>
							<td>monthlyList count</td>
							<td class="monthCount">${cache.monthlyList.size()}</td>
						</tr>
					</tbody>
				</table>

				<c:if test="${param.elapsed != null}">
					<div class="div_elapsed">elapsed : ${f:xml(param.elapsed)}ms</div>
				</c:if>

				<form:form cssClass="ajaxform">
					<input type="submit" name="update" value="update" class="btn btn-primary">
				</form:form>
			</div>
		</div>
	</c:param>

	<c:param name="footer_script">
		<script>
			$(".btn_cache").removeClass("btn-default").addClass("btn-primary");
			afnfblog.ajaxSuccess = function(thisform, data) {
				location.href = location.pathname + "?elapsed=" + data.elapsed;
			};
		</script>
	</c:param>

</c:import>
