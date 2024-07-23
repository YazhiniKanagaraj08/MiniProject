<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Staff Navigation Bar</title>
<link rel="stylesheet" type="text/css" href="navigationBarStyles.css">
</head>
<body>
<div class="container">
    <div class="navigation">
        <ul>
            <li>
                <a href="staffDashboard.jsp">
                    <span class="icon"><ion-icon name="school-outline"></ion-icon></span>
                    <span class="title">MCA</span>
                </a>
            </li>
            <li>
                <a href="staffDashboard.jsp">
                    <span class="icon"><ion-icon name="home-outline"></ion-icon></span>
                    <span class="title">Dashboard</span>
                </a>
            </li>
	    <li>
                <a href="staffEnrollmentHistory.jsp">
                    <span class="icon"><ion-icon name="bookmark-outline"></ion-icon></span>
                    <span class="title">Enrollment</span>
                </a>
            </li>
            <li>
                <a href="staffAllotmentHistory.jsp">
                    <span class="icon"><ion-icon name="bookmark-outline"></ion-icon></span>
                    <span class="title">Allotment History</span>
                </a>
            </li>
            <li>
                <a href="index.jsp">
                    <span class="icon"><ion-icon name="log-out-outline"></ion-icon></span>
                    <span class="title">Sign Out</span>
                </a>
            </li>
        </ul>
    </div>
</div>
<!-- Include scripts after closing container div -->
<script>
//add hovered class in selected list item
let list=document.querySelectorAll('.navigation li');
function activeLink(){
	list.forEach((item) =>
	item.classList.remove('hovered'));
	this.classList.add('hovered');
}
list.forEach((item) =>
item.addEventListener('mouseover',activeLink));

// Menutoggle
let toggle=document.querySelector('.toggle');
let navigation=document.querySelector('.navigation');
let main=document.querySelector('.main');

toggle.onclick=function(){
	navigation.classList.toggle('active');
	main.classList.toggle('active');
}
</script>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</body>
</html>
