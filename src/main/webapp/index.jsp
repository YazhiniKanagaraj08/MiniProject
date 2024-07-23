<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <link rel="stylesheet" href="style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="wrapper">
        <div class="form-container">
            <div class="form-box login">
                <form id="loginform" action="LoginServlet" method="post">
    <h1>Login</h1>
    <div class="input-box">
        <input type="text" id="Username" name="LoginUsername" placeholder="Username" required autocomplete="off">
        <i class='bx bxs-user'></i>
    </div>
    <div class="input-box">
        <input type="password" id="LoginPassword" name="LoginPassword" placeholder="Password" required>
        <span class="eye-icon bx bx-show" onclick="togglePasswordVisibility('LoginPassword', this)"></span>
    </div>
    <button type="Submit" class="btn" name="submitbtn" value="admin">Admin Login</button>
    <button type="Submit" class="btn" name="submitbtn" value="staff">Staff Login</button>
    <div class="register-link">
        <p> Don't have an Account ? <a href="signup.jsp">Sign Up</a></p>
    </div>
</form>

                <%
                  String btn=request.getParameter("submitbtn");
                if(btn!=null)
                {
                	String check=(String)request.getAttribute("msg");
                	if(check!=null)
                	{
                		out.print(check);
                	}
                }
                %>
            </div>
        </div>
    </div>
   <script src="script.js"></script>
<style>
@charset "ISO-8859-1";
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: "Poppins", sans-serif;
}
body{
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: url(img.jpg) no-repeat;
    background-size: cover;
    background-position: center;
}
.wrapper{
    width: 460px;
    background-color: transparent;
    backdrop-filter: blur(15px);
    border: 2px solid rgba(255, 255, 255, .2);
    color: #fff;
    border-radius: 12px;
    padding: 40px ;
}
.wrapper h1{
    font-size: 36px;
    text-align: center;
    margin-bottom: 20px;
}
.form-container {
    display: flex;
    justify-content: space-around;

}
.form-box {
    width: 350%;
    background-color: transparent;
    backdrop-filter: blur(15px);
    border-radius: 12px;
    padding: 20px;
    margin: 10px;
    display: none;
}
.form-box.login {
    display: block;
}

.input-box{
    position: relative;
    width: 100%;
    height: 50px;
    margin: 30px 0;
}
.input-box input{
    width: 100%;
    height: 100%;
    background: transparent;
    border: none;
    outline: none;
    border: 2px solid rgba(255, 255, 255, .2);
    border-radius: 40px;
    font-size: 16px;
    color: #fff;
    padding: 20px 45px 20px 20px;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    caret-color: #fff;
}

.input-box input::placeholder{
    color: #fff;
}
.input-box i{
    position: absolute;
    right: 20px;
    top: 30%;
    transform: translate(-50%);
    font-size: 20px;
}

.bx.bx-lock-open-alt:before {
    content: "\f693"; 
}

.wrapper .remember-forgot{
    display: flex;
    font-size: 14.5px;
    margin: -15px 0 15px;
  }
.remember-forgot a{
    color: #fff;
    text-decoration: none;
    padding-left: 90px;
}
.remember-forgot a:hover{
    text-decoration: underline;

  }
.wrapper .btn{
    width: 100%;
    height: 45px;
    background: #fff;
    border: none;
    outline: none;
    border-radius: 40px;
    box-shadow: 0 0 10px rgba(0, 0, 0, .1);
    cursor: pointer;
    font-size: 16px;
    color: #333;
    font-weight: 600;
    margin-top:20px;
}

.wrapper .register-link{
    font-size: 14.5px;
    text-align: center;
    margin: 20px 0 15px;

}
.register-link p a{
    color: #fff;
    text-decoration: none;
    font-weight: 600;
}
.register-link p a:hover{
    text-decoration: underline;
}
/* Add styling for the eye icon */
.input-box .eye-icon {
    position: absolute;
    right: 20px;
    top: 30%;
    transform: translate(-50%);
    font-size: 20px;
    cursor: pointer;
    color: #fff;
}
</style>
</body>
</html>
