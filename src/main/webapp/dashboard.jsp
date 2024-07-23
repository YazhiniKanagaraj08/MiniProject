<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="dashboardstyle.css">
</head>
<body>
    <div class="container">
        <div class="navigation">
            <ul>
                <ul>
				<li>
					<a href="dashboard.jsp">
						<span class="icon"><ion-icon name="school-outline"></ion-icon></span>
						<span class="title">MCA</span>
					</a>
				</li>
				<li>
					<a href="dashboard.jsp">
						<span class="icon"><ion-icon name="home-outline"></ion-icon></span>
						<span class="title">Dashboard</span>
					</a>
				</li>
				<li>
					<a href="department.jsp">
						<span class="icon"><ion-icon name="school-outline"></ion-icon></ion-icon></span>
						<span class="title">Departments</span>
					</a>
				</li>
				<li>
					<a href="staff.jsp">
						<span class="icon"><ion-icon name="people-outline"></ion-icon></span>
						<span class="title">Staff</span>
					</a>
				</li>
				<li>
					<a href="course.jsp">
						<span class="icon"><ion-icon name="save-outline"></ion-icon></span>
						<span class="title">Course</span>
					</a>
				</li>
				<li>
					<a href="enrollment.jsp">
						<span class="icon"><ion-icon name="bag-add-outline"></ion-icon></span>
						<span class="title">Enrollment</span>
					</a>
				</li>
				<li>
					<a href="allotmentHistory.jsp">
						<span class="icon"><ion-icon name="bookmark-outline"></ion-icon></span>
						<span class="title">Allotment</span>
					</a>
				</li>
				<li>
					<a href="index.jsp">
						<span class="icon"><ion-icon name="log-out-outline"></ion-icon></span>
						<span class="title">Sign Out</span>
					</a>
				</li>
			</ul>
            </ul>
        </div>
        <!-- main -->
        <div class="main">
            <div class="topbar">
                <div class="toggle">
                    <ion-icon name="menu"></ion-icon>
                </div>
                <!-- search -->
                <!--  div class="search_container">
                  <input type="text" id="searchInput" placeholder="Search...">
                  <button onclick="searchCentralized()">Search</button>  
                </div-->
            </div>
            <!-- cards -->
            <div class="cardBox">
                <div class="card">
                    <div>
                        <div class="numbers" id="departmentCount">0</div>
                        <div class="cardName">Departments</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="cube"></ion-icon>
                    </div>
                </div>
                <div class="card">
                    <div>
                        <div class="numbers" id="courseCount">0</div>
                        <div class="cardName">Courses</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="book"></ion-icon>
                    </div>
                </div>
                <div class="card">
                    <div>
                        <div class="numbers" id="staffCount">0</div>
                        <div class="cardName">Staffs</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="people"></ion-icon>
                    </div>
                </div>
                <div class="card">
                    <div>
                        <div class="numbers" id="allotmentCount">0</div>
                        <div class="cardName">Allotments</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="person-add"></ion-icon>
                    </div>
                </div>
            </div>
            <div id="allotmentDetailsContainer"></div>           
        </div>
    </div>
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
// Define a function to fetch data from the servlet and update the departmentCount element
function updateDepartmentCount() {
    fetch('departmentCount') // Assuming this URL matches the servlet mapping
    .then(response => response.text())
    .then(data => {
        // Update the innerHTML of departmentCount element with the fetched data
        document.getElementById('departmentCount').innerHTML = data;
    })
    .catch(error => console.error('Error fetching data:', error));
}

//Define a function to fetch data from the servlet and update the courseCount element
function updateCourseCount() {
    fetch('courseCount') // Assuming this URL matches the servlet mapping
    .then(response => response.text())
    .then(data => {
        // Update the innerHTML of courseCount element with the fetched data
        document.getElementById('courseCount').innerHTML = data;
    })
    .catch(error => console.error('Error fetching data:', error));
}

//Define a function to fetch data from the servlet and update the staffCount element
function updateStaffCount() {
    fetch('staffCount') // Assuming this URL matches the servlet mapping
    .then(response => response.text())
    .then(data => {
        // Update the innerHTML of courseCount element with the fetched data
        document.getElementById('staffCount').innerHTML = data;
    })
    .catch(error => console.error('Error fetching data:', error));
}
//Define a function to fetch data from the servlet and update the staffCount element
function updateAllotmentCount() {
    fetch('allotmentCount') // Assuming this URL matches the servlet mapping
    .then(response => response.text())
    .then(data => {
        // Update the innerHTML of courseCount element with the fetched data
        document.getElementById('allotmentCount').innerHTML = data;
    })
    .catch(error => console.error('Error fetching data:', error));
}
// Call the updateCount functions when the page loads
window.onload = function() {
    updateCourseCount();
    updateDepartmentCount();
    updateStaffCount();
    updateAllotmentCount();
};

//Define a function to call the servlet and handle the response
function callServlet() {
    fetch('latestAllotmentDetails') // Assuming this URL matches the servlet mapping
    .then(response => {
        // Check if the response is successful (status code 200)
        if (response.ok) {
            // Parse the response as text
            return response.text();
        }
        // If the response is not successful, throw an error
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        // Update the innerHTML of a container element with the fetched data
        document.getElementById('allotmentDetailsContainer').innerHTML = data;
    })
    .catch(error => {
        // Log any errors to the console
        console.error('Error fetching data:', error);
    });
}

// Call the callServlet function when the page loads or refreshes
window.addEventListener('load', callServlet);
window.addEventListener('DOMContentLoaded', callServlet);
window.addEventListener('pageshow', callServlet);
window.addEventListener('beforeunload', callServlet);

 /* function searchCentralized() {
    // Get the search input value
    var searchInput = document.getElementById("searchInput").value.trim();

    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Configure the request
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Replace the container content with the response from the servlet
            document.getElementById("searchResults").innerHTML = xhr.responseText;
        }
    };

    // Construct the URL with the search query parameter
    var url = "CentralizedSearchServlet?searchQuery=" + encodeURIComponent(searchInput);

    // Open the request
    xhr.open("GET", url, true);

    // Send the request
    xhr.send();
}
*/

</script>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
<style>
@charset "ISO-8859-1";
@import url('https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&diaplay=swap');
*
{
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Ubuntu', sans-serif;
}
:root{
	--blue: #0F4EB9;
	--white:#fff;
	--grey: #f5f5f5;
	--black1: #222;
	--black2: #999;
}
body
{
	min-height: 100vh;
	overflow-x: hidden;
}
.container{
	position: relative;
	width:100%;
}
.navigation
{
	position: fixed;
	width: 300px;
	height: 100%;
	background: var(--blue);
	border-left: 10px solid var(--blue);
	transition: 0.5s;
	overflow: hidden;
}
.navigation.active 
{
	width: 80px;
}
.navigation ul li
{
	position: relative;
	width: 100%;
	list-style: none;	
	border-top-left-radius: 30px;
	border-bottom-left-radius: 30px;
}
.navigation ul li:hover,
.navigation ul li.hovered
{
	background: var(--white);
}
.navigation ul li:nth-child(1)
{
	margin-bottom: 40px;
	pointer-events: none;
}
.navigation ul li a
{
	position: relative;
	display: block;
	width: 100%;
	display: flex;
	text-decoration: none;
	color: var(--white);	
}
.navigation ul li:hover a,
.navigation ul li.hovered a
{
	color: var(--blue);	
}
.navigation ul li a .icon
{
	position: relative;
	display: block;
	min-width: 60px;
	height: 60px;
	line-height: 70px;
	text-align: center;
}
.navigation ul li a .icon ion-icon
{
	font-size: 1.75em;
}
.navigation ul li a .title 
{
	position: relative;
	display: block;
	padding: 0 10px;
	height: 60px;
	line-height: 60px;
	text-align: start;
	white-space: nowrap;
}
/*Curve outside */
.navigation ul li:hover a::before,
.navigation ul li.hovered a::before
{
	content: '';
	position: absolute;
	right:0;
	top: -50px;
	width: 50px;
	height: 50px;
	background: transparent;
	border-radius: 50%;
	box-shadow: 35px 35px 0 10px var(--white);
	pointer-events: none;
}
.navigation ul li:hover a::after,
.navigation ul li.hovered a::after
{
	content: '';
	position: absolute;
	right:0;
	bottom: -50px;
	width: 50px;
	height: 50px;
	background: transparent;
	border-radius: 50%;
	box-shadow: 35px -35px 0 10px var(--white);
	pointer-events: none;
}
.main
{
	position: absolute;
	width: calc(100% - 300px);
	left: 300px;
	min-height: 100vh;
	background: var(--white);
	transition: 0.5s;
}
.main.active 
{
	width: calc(100% - 80px);
	left: 80px;
}
.topbar
{
	width: 100%;
	height: 60px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0 10px;
}
.toggle
{
	position: relative;
	width: 60px;
	justify-content: center;
	align-items: center;
	font-size: 2.5em;
	cursor: pointer;
	display: flex;
}
button:hover {
    background-color: #023f94;
}
#type {
    padding: 10px;
    margin: 5px 0;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    color: #023f94;
}
#allotmentDetailsContainer {
	text-align: center;
    max-width: 1000px; 
    margin: 20px auto; 
    background-color: #fff; 
    border-radius: 10px; 
    overflow-x: auto;
    border: none;  
}

#allotmentDetailsContainer table {
    width: 100%; 
    border-collapse: collapse;
    border: none; 
}
table {
    max-width: 800px; /* Adjust as per your layout */
}
.table_header {
    position: fixed; 
    top: 0;
    right: 0; 
    display: flex;
    justify-content: space-between;
    align-items: right;
    padding: 20px;
    background-color: rgb(240, 240, 240);
    width: 80%;
    z-index: 1000;
}
.table_header p {
    color: #000000;
}
.table_header input {
    padding: 10px 20px;
    margin: 0 10px;
    outline: none;
    border: 1px solid #0f4eb9;
    border-radius: 6px;
    color: #0f4eb9;
}

table {
    text-align: center;
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px; 
}
th, td {
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}
thead th {
    background-color: rgb(240, 240, 240);
    color: #000000;
}
tbody tr:nth-child(even) {
    background-color: #f2f2f2;
}
/*.search_container {
    align-items: center;
    width: 80%; 
}

.search_container input[type="text"] {
    flex: 1; 
    padding: 8px;
    margin-right: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.search_container button {
    padding: 8px 12px;
    border: none;
    background-color: #0f4eb9;
    color: #ffffff;
    border-radius: 4px;
    cursor: pointer;
}
*/
.cardBox 
{
	padding: 20px;
	display: grid;
	grid-template-columns: repeat(4,1fr);
	grid-gap: 30px;
}
.cardBox .card
{
	position: relative;
	background: var(--white);
	padding: 20px;
	border-radius: 10px;
	display: flex;
	justify-content: space-between;
	cursor: pointer;
	box-shadow: 0 6px 6px rgb(0,0,0,0.08);
}
.cardBox .card .numbers 
{
	position: relative;
	font-weight: 500;
	font-size: 2.5em;
	color: var(--blue);
}
.cardBox .card  .cardName 
{
	color: var(--black2);
	font-size: 1.1em;
	margin-top: 5px;
} 
.cardBox .card .iconBx 
{
	font-size: 3.5em;
	color: var(--black2);
}
</style>
</body>
</html>
