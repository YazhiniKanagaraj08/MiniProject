<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Staff Details</title>
    <%@ include file="navigationBar.jsp" %>
</head>
<body onload="loadStaffDetails()">
   <div class="main">
      <div class="table_header">
        <p>Staff Details</p>
        <div class="search_container">
        <input type="text" id="searchInput" placeholder="Search...">
        <button onclick="searchStaffs()">Search</button>
        </div>
        <div>
           <button class="add_new" onclick="openPopup('add')">+ Add New</button>
        </div>
      </div>
      <table id="staffTable">
          <thead>
              <tr>
                  <th>Staff ID</th>
                  <th>Staff Name</th>
                  <th>Designation</th>
                  <th>Department ID</th>
                  <th>Specialization 1</th>
                  <th>Specialization 2</th>
                  <th>Specialization 3</th>
                  <th>Specialization 4</th>
                  <th>Email ID</th>
                  <th>Contact Number</th>
                  <th>Actions</th>
              </tr>
          </thead>
          <tbody>
              <!-- Table rows will be populated dynamically from the servlet -->
          </tbody>
      </table>
   </div>
<!-- Popup form -->
   <div id="popupForm" class="popup" style="display: none;">
       <div class="popup-content">
           <span class="close" onclick="closePopup()">&times;</span>
           <h2 id="popupTitle"></h2>
           <form id="staffForm">
               <input type="hidden" id="staffIDHidden" />
               <input type="text" id="staffID" placeholder="Staff ID"/><br>
               <input id="staffName" placeholder="Staff Name"/><br>
               <select id="designation" name="designation">
                   <option value="" disabled selected hidden>Designation</option>
	                   <option value="Professor">Professor</option>
	                   <option value="Assistant Professor">Assistant Professor</option>
	                   <option value="HOD">HOD</option>
               </select><br>
               </select><br>
	               <input id="departmentID" placeholder="Department ID"/><br>
	               <select id="specialization1" name="specialization1">
	                       <option value="" disabled selected hidden>Specialization 1</option>
	                       <option value="Software Engineering Methodologies and Quality Assurance">Software Engineering Methodologies and Quality Assurance</option>
	                       <option value="Engineering Clinics">Engineering Clinics</option>
	                       <option value="Web Technologies">Web Technologies</option>
                           <option value="User Interface Design And User Experience">User Interface Design And User Experience</option>
                           <option value="Software Project Management">Software Project Management</option>
                           <option value="Data Intensive Computing">Data Intensive Computing</option>
                           <option value="Data Intensive Computing Lab Using Python">Data Intensive Computing Lab Using Python</option>
                           <option value="Big Data Analytics">Big Data Analytics</option>
                           <option value="Data Communication And Networks">Data Communication And Networks</option>
                           <option value="Data Structures And Algorithms">Data Structures And Algorithms</option>
                           <option value="Database Technologies">Database Technologies</option>
                           <option value="Advanced Operating Systems">Advanced Operating Systems</option>
                           <option value="Programming With Java">Programming With Java</option>
                           <option value="Data Structures Lab Using C">Data Structures Lab Using C</option>
                           <option value="Programming With Java Lab">Programming With Java Lab</option>
                           <option value="Database Technologies Laboratory">Database Technologies Laboratory</option>
	               </select><br>
	               <select id="specialization2" name="specialization2">
	                       <option value="" disabled selected hidden>Specialization 2</option>
	                       <option value="Software Engineering Methodologies and Quality Assurance">Software Engineering Methodologies and Quality Assurance</option>
	                       <option value="Engineering Clinics">Engineering Clinics</option>
	                       <option value="Web Technologies">Web Technologies</option>
                           <option value="User Interface Design And User Experience">User Interface Design And User Experience</option>
                           <option value="Software Project Management">Software Project Management</option>
                           <option value="Data Intensive Computing">Data Intensive Computing</option>
                           <option value="Data Intensive Computing Lab Using Python">Data Intensive Computing Lab Using Python</option>
                           <option value="Big Data Analytics">Big Data Analytics</option>
                           <option value="Data Communication And Networks">Data Communication And Networks</option>
                           <option value="Data Structures And Algorithms">Data Structures And Algorithms</option>
                           <option value="Database Technologies">Database Technologies</option>
                           <option value="Advanced Operating Systems">Advanced Operating Systems</option>
                           <option value="Programming With Java">Programming With Java</option>
                           <option value="Data Structures Lab Using C">Data Structures Lab Using C</option>
                           <option value="Programming With Java Lab">Programming With Java Lab</option>
                           <option value="Database Technologies Laboratory">Database Technologies Laboratory</option>
	               </select><br>
                       <select id="specialization3" name="specialization3">
                           <option value="" disabled selected hidden>Specialization 3</option>
	                       <option value="Software Engineering Methodologies and Quality Assurance">Software Engineering Methodologies and Quality Assurance</option>
	                       <option value="Engineering Clinics">Engineering Clinics</option>
	                       <option value="Web Technologies">Web Technologies</option>
                           <option value="User Interface Design And User Experience">User Interface Design And User Experience</option>
                           <option value="Software Project Management">Software Project Management</option>
                           <option value="Data Intensive Computing">Data Intensive Computing</option>
                           <option value="Data Intensive Computing Lab Using Python">Data Intensive Computing Lab Using Python</option>
                           <option value="Big Data Analytics">Big Data Analytics</option>
                           <option value="Data Communication And Networks">Data Communication And Networks</option>
                           <option value="Data Structures And Algorithms">Data Structures And Algorithms</option>
                           <option value="Database Technologies">Database Technologies</option>
                           <option value="Advanced Operating Systems">Advanced Operating Systems</option>
                           <option value="Programming With Java">Programming With Java</option>
                           <option value="Data Structures Lab Using C">Data Structures Lab Using C</option>
                           <option value="Programming With Java Lab">Programming With Java Lab</option>
                           <option value="Database Technologies Laboratory">Database Technologies Laboratory</option>
	               </select><br>
                       <select id="specialization4" name="specialization4">
	                       <option value="" disabled selected hidden>Specialization 4</option>
	                       <option value="Software Engineering Methodologies and Quality Assurance">Software Engineering Methodologies and Quality Assurance</option>
	                       <option value="Engineering Clinics">Engineering Clinics</option>
	                       <option value="Web Technologies">Web Technologies</option>
                           <option value="User Interface Design And User Experience">User Interface Design And User Experience</option>
                           <option value="Software Project Management">Software Project Management</option>
                           <option value="Data Intensive Computing">Data Intensive Computing</option>
                           <option value="Data Intensive Computing Lab Using Python">Data Intensive Computing Lab Using Python</option>
                           <option value="Big Data Analytics">Big Data Analytics</option>
                           <option value="Data Communication And Networks">Data Communication And Networks</option>
                           <option value="Data Structures And Algorithms">Data Structures And Algorithms</option>
                           <option value="Database Technologies">Database Technologies</option>
                           <option value="Advanced Operating Systems">Advanced Operating Systems</option>
                           <option value="Programming With Java">Programming With Java</option>
                           <option value="Data Structures Lab Using C">Data Structures Lab Using C</option>
                           <option value="Programming With Java Lab">Programming With Java Lab</option>
                           <option value="Database Technologies Laboratory">Database Technologies Laboratory</option>
	               </select><br>
	               <input id="emailID" placeholder="Email ID"/><br>
	               <input id="contactNumber" placeholder="Contact Number"/><br>
	               <button type="button" id="popupSaveButton" onclick="addStaff()">Save</button>
	           </form>
       </div>
   </div>
   <style>
   @charset "ISO-8859-1"; 
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
   
   body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
        }

        .main {
            padding: 20px;
            overflow-x: auto; /* Enable horizontal scrolling */
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
        .search_container {
            display: flex;
            align-items: center;
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

        .add_new {
            padding: 10px 20px;
            color: #ffffff;
            background-color: #0f4eb9;
            border-radius: 6px;
            cursor: pointer;
        }

#staffTable{
margin-left:20px;
margin-top:100px;
}
        /* Table styles */
         table {
            width: calc(100% - 40px); /* Adjusted width for the table */
            border-collapse: collapse;
            margin-top: 20px;
            margin-bottom: 20px;
            margin-left: 20px; /* Added margin to avoid overlap with navigation */
        }

        th, td {
            padding: 8px; /* Reduced padding for compactness */
            text-align: left;
            border-bottom: 1px solid #ddd;
            white-space: nowrap; /* Prevent text wrapping */
            overflow: hidden;
            text-overflow: ellipsis;
        }

        thead th {
            background-color: rgb(240, 240, 240);
            color: #000000;
        }

        tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        /* Popup styles */
        .popup {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .popup-content {
            background-color: #fefefe;
            padding: 20px;
            border: 1px solid #888;
            width: 70%;
            max-width: 500px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
        }

        /* Additional styles */
        input[type="text"],
        select {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
            width: 100%;
        }

        button {
            background-color: #0f4eb9;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #023f94;
        }
   </style>
<script src="staffScript.js"></script>
</body>
</html>