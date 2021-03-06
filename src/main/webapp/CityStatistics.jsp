
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="shared/head.jsp"/>
</head>
<body class="bg-dark">
<jsp:include page="shared/body.jsp"/>
<div style="margin-top:5%;">
    <div class="text-center">
        <div class="row justify-content-md-center">
            <div class="col-5">
                <table class="table table-striped mt-4 table-inverse table-hover">
                    <thead class="thead-inverse">
                    <tr>
                        <th>Miasto</th>
                        <th>Liczba wyświetleń</th>
                    </tr>
                    </thead>
                        <c:forEach items="${places}" var="place">
                            <tr>
                                <td>${place.getName()}</td>
                                <td>${place.getNumber()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
