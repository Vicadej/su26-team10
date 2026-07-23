<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Registration</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
    <div class="customer-form">
        <h3>Register as a Customer</h3>
        <p>create a free account or <a href="/customer/login">log in</a></p>

        <#if error??>
            <p class="error-message">${error}</p>
        </#if>

        <form action="/customer/register" method="post">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name" placeholder="e.g. Lebron James!!">

            <label for="email">Email</label>
            <input type="text" id="email" name="email" placeholder="e.g. coffeemachine@gmail.com">

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="e.g. password">

            <button type="submit" class="register-btn">Register</button>
        </form>

    </div>

    <div class="back-link">
        <a href="#">back</a>
    </div>
</body>
</html>
