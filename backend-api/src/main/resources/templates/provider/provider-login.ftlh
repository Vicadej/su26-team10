<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Provider Login</title>
    <link rel="stylesheet" href="/providerStyle.css">
</head>
<body>
    <div class= "provider-form">
        <h3>Provider Login</h3>
        <p>don't have an account? <a href="/provider/register">Create one!</a></p>

        <form method="post">

            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="e.g. sillygoose@gmail.com" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="e.g. dogsandcats1234" required>

            <button type="submit" class="register-btn" formaction="/provider/login" formmethod="post">Log In</button>
        </form>

    </div>

    <div class="back-link">
        <a href="#">back</a>
    </div>
</body>
</html>