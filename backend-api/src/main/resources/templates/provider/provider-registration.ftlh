<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Provider Registration</title>
    <link rel="stylesheet" href="/providerStyle.css">
</head>
<body>
    <div class= "provider-form">
        <h3>Register as a Provider</h3>
        <p>create a free account or <a href="provider/login">log in</a></p>


        <form action="/provider/signup" method="post" enctype="multipart/form-data">
            <label for="organization">Organization Name</label>
            <input type="text" id="name" name="name" placeholder="e.g. The Carrot Cart" required>

            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="e.g. sillygoose@gmail.com" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="e.g. dogsandcats1234" required>

            <label for="profilePictureFile">Profile Picture</label>
            <input type="file" name="profilePictureFile" id="profilePictureFile" accept="image/*">

            <button type="submit" class="register-btn">Register</button>
        </form>

    </div>

    <div class="back-link">
        <a href="#">back</a>
    </div>
</body>
</html>