<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%
String searchWord = (String) request.getAttribute("searchWord");
searchWord = searchWord == null ? "" : searchWord;
String mode = (String) request.getAttribute("mode");
mode = mode == null ? "" : mode;
List<Word> list = (List<Word>) request.getAttribute("list");
Integer total = (Integer) request.getAttribute("total");
Integer limit = (Integer) request.getAttribute("limit");
Integer pageNo = (Integer) request.getAttribute("pageNo");
String pagination=(String)request.getAttribute("pagination");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>英和辞典デモデモ2</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<style>
.container{
min-height:calc(100vh - 70px);
}
form{
margin:20px auto;
}
input,select{
margin-right:5px;
}
.pager{
text-align:left;
}
.paginationBox{
text-align:center;
}
footer{
height:40px;
background:#347ab7;
color:white;
text-align:center;
line-height:40px;
margin-top:30px;
}
</style>
</head>
<body>
<div class="container">
	<form action="/ejDictionary/main" method="get">
		<input type="text" name="searchWord" value="<%=searchWord%>">
		<select name="mode">
			<option value="startsWith"
				<%if (mode.equals("startsWith"))
	out.print(" selected");%>>で始まる</option>
			<option value="contains"
				<%if (mode.equals("contains"))
	out.print(" selected");%>>含む</option>
			<option value="endsWith"
				<%if (mode.equals("endsWith"))
	out.print(" selected");%>>で終わる</option>
			<option value="match"
				<%if (mode.equals("match"))
	out.print(" selected");%>>一致する</option>
		</select>
		<button type="submit">検索</button>
	</form>
	<%
	if (list != null && list.size() > 0) {
	%>
	<%
	if (total <= limit) {
	%>
	<p>
		全<%=total%>件
	</p>
	<%
	} else {
	%>
	<p>
		全<%=total%>件中
		<%=(pageNo - 1) * limit + 1%>~<%=pageNo * limit > total ? total : pageNo * limit%>件を表示
	</p>
	<%--ページ番号が１より大きかったら前へのリンクを表示 --%>
	<ul>
		<%
		if (pageNo > 1) {
		%>
		<li><a
			href="/ejDictionary/main?searchWord=<%=searchWord%>&mode=<%=mode%>&page=<%=pageNo - 1%>"><span
				aria-hidden="true">&larr;</span>前へ</a></li>
		<%
		}
		%>
		<%--件数が全件数に届かないときは次へのリンクを表示 --%>
		<%
		if (pageNo * limit < total) {
		%>
		<li><a
			href="/ejDictionary/main?searchWord=<%=searchWord%>&mode=<%=mode%>&page=<%=pageNo + 1%>">次へ<span
				aria-hidden="true">&rarr;</span></a></li>
		<%
		}
		%>
	</ul>
	<%
	}
	%>
	<table border="1">
		<%
		for (Word w : list) {
		%>
		<tr>
			<th><%=w.getTitle()%></th>
			<td><%=w.getBody() %></td>
		</tr>
		<%} %>
	</table>
	<%} %>
	<%if(pagination != null){ %>
 <%=pagination %>
 <%} %>
</div><!-- container -->
<footer>
&copy; 2023 つるりんの自宅学習
</footer>
</body>
</html>