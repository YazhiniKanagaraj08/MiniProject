<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up Form</title>
    <link rel="stylesheet" href="style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <div class="wrapper">
        <div class="form-container">
            <div class="form-box SignUp" style="display: block;">
                <form id="signupForm" action="RegisterServlet" method="post"> <!-- Changed method to post -->
                    <h1>Sign Up</h1>
                    <div class="input-box">
                        <input type="text" id="SignUpUsername" name="Username" placeholder="Username" required autocomplete="off">
                        <i class='bx bxs-user'></i>
                    </div>
                    <div class="input-box">
                        <input type="password" id="SignUpPassword" name="Password" placeholder="Password" required>
                        <span class="eye-icon bx bx-show" onclick="togglePasswordVisibility('SignUpPassword', this)"></span>
                    </div>
                    <div class="input-box">
                        <input type="password" id="RepeatPassword" name="RepeatPassword" placeholder=" Repeat Password" required>
                        <span class="eye-icon bx bx-show" onclick="togglePasswordVisibility('RepeatPassword', this)"></span>
                    </div>
                    <div class="input-box"> <!-- Added dropdown for user type -->
                        <select name="UserType" required>
                            <option value="">Select User Type</option>
                            <option value="admin">Admin</option>
                            <option value="staff">Staff</option>
                        </select>
                    </div>
                    <button type="submit" class="btn">Save</button> <!-- Changed type to submit -->
                    <div class="register-link">
                        <p> Already have an Account ?<a href="index.jsp">Login</a></p>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="script.js"></script>
</body>
</html>
