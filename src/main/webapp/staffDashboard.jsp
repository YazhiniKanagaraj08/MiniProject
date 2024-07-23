<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard</title>
    <%@ include file="staffNavigationBar.jsp" %>
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
                        <span class="icon"><ion-icon name="bag-add-outline"></ion-icon></span>
                        <span class="title">Enrollment</span>
                    </a>
                </li>
                <li>
                    <a href="staffAllotmentHistory.jsp">
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
        </div>
        <!-- main -->
        <div class="main">
            <div class="topbar">
                <h1>Welcome !</h1>
            </div>
            <!-- cards -->
            <div class="cardBox">
                <div class="card">
                    <div>
                        <div class="numbers" id="enrollmentCount">0</div>
                        <div class="cardName">Enrollments</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="person-add"></ion-icon>
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
            <div id="staffAllotmentDetailsContainer"></div>
            <div id="staffEnrollmentDetailsContainer"></div>
        </div>
    </div>
    <script>
        //add hovered class in selected list item
        let listItems = document.querySelectorAll('.navigation li');

        function activeLink() {
            listItems.forEach((item) => item.classList.remove('hovered'));
            this.classList.add('hovered');
        }

        listItems.forEach((item) => item.addEventListener('mouseover', activeLink));

        // Define a function to fetch data from the servlet and update the enrollmentCount element
        function updateStaffEnrollmentCount() {
            fetch('staffEnrollmentCount') // Assuming this URL matches the servlet mapping
            .then(response => response.text())
            .then(data => {
                // Update the innerHTML of enrollmentCount element with the fetched data
                document.getElementById('enrollmentCount').innerHTML = data;
            })
            .catch(error => console.error('Error fetching data:', error));
        }

        // Define a function to fetch data from the servlet and update the allotmentCount element
        function updateStaffAllotmentCount() {
            fetch('staffAllotmentCount') // Assuming this URL matches the servlet mapping
            .then(response => response.text())
            .then(data => {
                // Update the innerHTML of allotmentCount element with the fetched data
                document.getElementById('allotmentCount').innerHTML = data;
            })
            .catch(error => console.error('Error fetching data:', error));
        }

        // Define a function to call the servlet and handle the response
        function callServlet() {
            fetch('staffLatestAllotmentDetails') // Assuming this URL matches the servlet mapping
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
                // Update the innerHTML of allotmentDetailsContainer element with the fetched data
                document.getElementById('staffAllotmentDetailsContainer').innerHTML = data;
            })
            .catch(error => {
                // Log any errors to the console
                console.error('Error fetching data:', error);
            });
        }

        // Call the updateCount functions when the page loads
        window.onload = function() {
            updateStaffEnrollmentCount();
            updateStaffAllotmentCount();
        };

        // Call the callServlet function when the page loads or refreshes
        window.addEventListener('load', callServlet);
        window.addEventListener('DOMContentLoaded', callServlet);
        window.addEventListener('pageshow', callServlet);
        window.addEventListener('beforeunload', callServlet);
        
     // Define a function to call the servlet and handle the response
        function callEnrollmentServlet() {
            fetch('staffLatestEnrollmentDetails') // Assuming this URL matches the servlet mapping
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
                // Update the innerHTML of enrollmentDetailsContainer element with the fetched data
                document.getElementById('staffEnrollmentDetailsContainer').innerHTML = data;
            })
            .catch(error => {
                // Log any errors to the console
                console.error('Error fetching data:', error);
            });
        }

        // Call the callEnrollmentServlet function when the page loads or refreshes
        window.addEventListener('load', callEnrollmentServlet);
        window.addEventListener('DOMContentLoaded', callEnrollmentServlet);
        window.addEventListener('pageshow', callEnrollmentServlet);
        window.addEventListener('beforeunload', callEnrollmentServlet);

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
#staffAllotmentDetailsContainer {
	text-align: center;
    max-width: 1000px; 
    margin: 20px auto; 
    background-color: #fff; 
    border-radius: 10px; 
    overflow-x: auto;
    border: none;  
}

#staffAllotmentDetailsContainer table {
    width: 100%; 
    border-collapse: collapse;
    border: none; 
}
#staffEnrollmentDetailsContainer {
	text-align: center;
    max-width: 1000px; 
    margin: 20px auto; 
    background-color: #fff; 
    border-radius: 10px; 
    overflow-x: auto;
    border: none;  
}

#staffEnrollmentDetailsContainer table {
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
