<%-- 
    Document   : list_product
    Created on : Oct 22, 2024, 2:11:45 PM
    Author     : ADMIN
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Hoa"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../shared/header.jsp" />

<jsp:include page="../shared/nav.jsp" />

<%
    if (request.getAttribute("success") != null) {

%>
<script>
    alert("<%=request.getAttribute("success")%>");
</script>
<% } %>

<div class="container">
    <h2> Danh sách sản phẩm</h2>
    <div class="mb-2 text-end">
        <a href="ManageProduct?action=ADD" class="btn btn-success"> <i class="bi bi-plus-circle"></i> Thêm mới</a>
    </div>

    <table class="table table-bordered table-striped">
        <tr>
            <th>Tên hoa</th>
            <th>Giá</th>
            <th>Hình ảnh</th>
            <th>Loại</th>
            <th>Action</th>
        </tr>  
        <%
            DecimalFormat fmt = new DecimalFormat("#,##0");
            ArrayList<Hoa> dsHoa = (ArrayList<Hoa>) request.getAttribute("dsHoa");
            for (Hoa x : dsHoa) {
        %>
        <tr>
            <td><%=x.getTenhoa()%></td>
            <td><%=fmt.format(x.getGia())%>Đ</td>
            <td> <img src="assets/images/products/<%=x.getHinh()%>" style="width: 100px">  </td>
            <td><%=x.getMaloai()%></td>
            <td>
                <a href="ProductManagement?action=edit&mahoa= <%=x.getMahoa()%>" class="btn btn-secondary"> <i class="bi bi-pencil-square"></i> Sửa</a>
                <a href="ProductManagement?action=delete&mahoa= <%=x.getMahoa()%>" class="btn btn-danger"> <i class="bi bi-trash"></i> Xoá</a>
            </td>
        </tr>    
        <%
            }
        %>
    </table>
    Chọn trang:
    <%
        int pageSum = (int) request.getAttribute("pageSum");
        for (int i = 1; i <= pageSum; i++) {
    %>
    <a href="ProductManagement?page=<%=i%>"><%=i%></a>
    <%
        }
    %>
</div>

<jsp:include page="../shared/footer.jsp" />