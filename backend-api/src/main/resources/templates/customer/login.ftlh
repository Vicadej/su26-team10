<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Login</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
    <div class="customer-form">
        <h3>Customer Login</h3>
        <p>don't have an account? <a href="/customer/register">Create one!</a></p>

        <#if error??>
            <p class="error-message">${error}</p>
        </#if>

        <form action="/customer/login" method="post">

            <label for="email">Email</label>
            <input type="text" id="email" name="email" placeholder="e.g. coffeemachine@yahoo.com">

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="e.g. password">

            <button type="submit" class="register-btn">Log In</button>
        </form>

    </div>

    <div class="back-link">
        <a href="#">back</a>
    </div>
</body>
</html>
