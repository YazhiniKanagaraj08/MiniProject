function loadStaffDetails() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("staffTable").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "StaffDetailsServlet", true);
    xhr.send();
}

function openPopup(action, staffID, staffName, designation, departmentID, specialization1, specialization2, specialization3, specialization4, emailID, contactNumber) {
    var popupTitle = document.getElementById("popupTitle");
    var saveButton = document.getElementById("popupSaveButton");

    if (action === 'add') {
        popupTitle.textContent = "Add New Staff";
        saveButton.textContent = "Add";
        saveButton.setAttribute("onclick", "addStaff()");
    } else if (action === 'update') {
        popupTitle.textContent = "Update Staff";
        saveButton.textContent = "Save";
        saveButton.setAttribute("onclick", "updateStaff()");
        
        // Populate the update form with existing values
        document.getElementById("staffID").value = staffID;
        document.getElementById("staffName").value = staffName;
        document.getElementById("designation").value = designation;
        document.getElementById("departmentID").value = departmentID;
        document.getElementById("specialization1").value = specialization1;
        document.getElementById("specialization2").value = specialization2;
        document.getElementById("specialization3").value = specialization3;
        document.getElementById("specialization4").value = specialization4;
        document.getElementById("emailID").value = emailID;
        document.getElementById("contactNumber").value = contactNumber;
        document.getElementById("staffIDHidden").value = staffID;
    }

    document.getElementById("popupForm").style.display = "block";
}

function closePopup() {
    document.getElementById("popupForm").style.display = "none";
}

function addStaff() {
    var staffID = document.getElementById("staffID").value;
    var staffName = document.getElementById("staffName").value;
    var designation = document.getElementById("designation").value;
    var departmentID = document.getElementById("departmentID").value;
    var specialization1 = document.getElementById("specialization1").value;
    var specialization2 = document.getElementById("specialization2").value;
    var specialization3 = document.getElementById("specialization3").value;
    var specialization4 = document.getElementById("specialization4").value;
    var emailID = document.getElementById("emailID").value;
    var contactNumber = document.getElementById("contactNumber").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Append new staff row to the table body
            var newRowHtml = xhr.responseText;

            var tbody = document.querySelector("#staffTable tbody");
            tbody.insertAdjacentHTML("beforeend", newRowHtml);

            // Clear input fields after adding the new course
            document.getElementById("staffForm").reset();

            // Close the popup after adding the course
            closePopup();
        }
    };
    xhr.open("POST", "AddStaffServlet?staffID=" + staffID + "&staffName=" + staffName + "&designation=" + designation + "&departmentID=" + departmentID + "&specialization1=" + specialization1 + "&specialization2=" + specialization2 + "&specialization3=" + specialization3 + "&specialization4=" + specialization4 + "&emailID=" + emailID + "&contactNumber=" + contactNumber, true);
    xhr.send();
}

function updateStaff() {
    var staffID = document.getElementById("staffID").value;
    var staffName = document.getElementById("staffName").value;
    var designation = document.getElementById("designation").value;
    var departmentID = document.getElementById("departmentID").value;
    var specialization1 = document.getElementById("specialization1").value;
    var specialization2 = document.getElementById("specialization2").value;
    var specialization3 = document.getElementById("specialization3").value;
    var specialization4 = document.getElementById("specialization4").value;
    var emailID = document.getElementById("emailID").value;
    var contactNumber = document.getElementById("contactNumber").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // Close the popup after updating the course
                closePopup();
                // Reload the table with updated data
                loadStaffDetails();
            } else {
                console.error('Failed to update staff.');
            }
        }
    };
    xhr.open("POST", "UpdateStaffServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var params = "staffID=" + encodeURIComponent(staffID) +
                 "&staffName=" + encodeURIComponent(staffName) +
                 "&designation=" + encodeURIComponent(designation) +
                 "&departmentID=" + encodeURIComponent(departmentID) +
                 "&specialization1=" + encodeURIComponent(specialization1) +
                 "&specialization2=" + encodeURIComponent(specialization2) +
                 "&specialization3=" + encodeURIComponent(specialization3) +
                 "&specialization4=" + encodeURIComponent(specialization4) +
                 "&emailID=" + encodeURIComponent(emailID) +
                 "&contactNumber=" + encodeURIComponent(contactNumber);
    xhr.send(params);
}

function deleteStaff(staffID) {
    // Send an AJAX request to delete the course from the backend
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            console.log("Response status:", xhr.status); // Log the response status code
            if (xhr.status == 200) {
                // If deletion is successful, reload the page
                location.reload();
            } else {
                console.error('Failed to delete staff.');
            }
        }
    };
    xhr.open("GET", "http://localhost:8083/MiniProjectStaffAllocation/DeleteStaffServlet?staffID=" + staffID, true);
    xhr.send();
}

function searchStaffs() {
    // Get the search input value
    var searchInput = document.getElementById("searchInput").value.trim();

    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Configure the request
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Replace the table body content with the response from the servlet
            document.getElementById("staffTable").innerHTML = xhr.responseText;
        }
    };

    // Construct the URL with the search query parameters
    var url = "SearchStaffServlet?staffID=" + encodeURIComponent(searchInput) +
              "&staffName=" + encodeURIComponent(searchInput) +
              "&designation=" + encodeURIComponent(searchInput) +
              "&departmentID=" + encodeURIComponent(searchInput) +
              "&specialization1=" + encodeURIComponent(searchInput) +
              "&specialization2=" + encodeURIComponent(searchInput) +
              "&specialization3=" + encodeURIComponent(searchInput) +
              "&specialization4=" + encodeURIComponent(searchInput) +
              "&emailID=" + encodeURIComponent(searchInput) +
              "&contactNumber=" + encodeURIComponent(searchInput);

    // Open the request
    xhr.open("GET", url, true);

    // Send the request
    xhr.send();
}
