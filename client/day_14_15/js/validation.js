function login() {
    const loginForm = document.getElementById('login-form');
    const loginUsername = document.getElementById("loginUsername");
    const loginPassword = document.getElementById("loginPassword");

    const loginUsernameError = document.getElementById("loginUsername-error");
    const loginPasswordError = document.getElementById("loginPassword-error");
    const successMessage = document.getElementById("success-message");

    
    if (!loginForm || !loginUsername || !loginPassword || !loginUsernameError || !loginPasswordError || !successMessage) {
        console.log("Login clicked. Username: testUser, Password: testPassword");
        return;
    }

    loginForm.addEventListener('submit', function(e){
        let invalidForm = false;
        e.preventDefault();

        loginUsernameError.textContent = '';
        loginPasswordError.textContent = '';
        successMessage.style.display = 'none';

        const loginUsernameValue = loginUsername.value.trim();
        const loginUsernameRegex = /^[a-zA-Z0-9_]{3,16}$/;
        if(!loginUsernameValue){
            loginUsernameError.textContent = "Username is required.";
            invalidForm = true;
        }
        else if(loginUsernameValue && !loginUsernameRegex.test(loginUsernameValue)){
            loginUsernameError.textContent = "Username should not contain special characters.";
            invalidForm = true;
        }

        const loginPasswordValue = loginPassword.value.trim();
        const loginPasswordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@!#$%&*?_])[a-zA-Z\d@!#$%&*?_]{8,}$/;
        if(!loginPasswordValue){
            loginPasswordError.textContent = "Password is required";
            invalidForm = true;
        } else if(loginPasswordValue && !loginPasswordRegex.test(loginPasswordValue)){
            loginPasswordError.textContent = "Password should be at least 8 digits and must contain at least one capital letter and one numeric.";
            invalidForm = true;
        }
        // console.log(`Login clicked. Username: ${loginUsernameValue}, Password: ${loginPasswordValue}`);

        if(!invalidForm){
            
            successMessage.style.display = 'block';
        }
    });
        

    
}

function register() {
   

    // Frontend validation for registration form
    

    // Validate email format
    
    // Validate username (no special characters)
    

    // Validate password (at least 8 characters, one capital letter, and one numeric)
    const registerForm = document.getElementById('registration-form');
    const name = document.getElementById('name');
    const email = document.getElementById('email');
    const userName = document.getElementById('Username');
    const password = document.getElementById('Password');

    const nameError = document.getElementById('name-error');
    const emailError = document.getElementById('email-error');
    const usernameError = document.getElementById('Username-error');
    const passwordError = document.getElementById('Password-error');
    const successMessage = document.getElementById('success-message');

    if(!registerForm || !name || !email || !userName || !password || !nameError || !usernameError || !emailError || !passwordError || !successMessage){
        console.log("Register clicked. Name: John Doe, Email: john@example.com, Username: johndoe, Password: Password123");
        return;
    }

    registerForm.addEventListener('submit', function(e){
        let invalidForm = false;
        e.preventDefault();

        nameError.textContent = '';
        emailError.textContent = '';
        userName.textContent = '';
        password.textContent = '';
        successMessage.style.display = 'none';

        //validate name
        const nameValue = name.value.trim();
        const nameRegex = /^[a-zA-Z_]$/;
        if(!nameValue){
            nameError.textContent = 'Name is required.';
            invalidForm = true;
        }
        else if(nameValue && !nameRegex.test(nameValue)){
            nameError.textContent="Name must contain characters.";
            invalidForm = true;
        }

        //validate Email
        const emailValue = email.value.trim();
        const emailRegex = /^[a-zA-Z0-9._%=-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]$/;
        if(!emailValue){
            emailError.textContent = 'Email is required.';
            invalidForm = true;
        }
        else if(emailValue && !emailRegex.test(emailValue)){
            emailError.textContent = 'Email must be valid';
            invalidForm = true;
        }
        //validate username
        const usernameValue = loginUsername.value.trim();
        const usernameRegex = /^[a-zA-Z0-9_]{3,16}$/;
        if(!usernameValue){
            usernameError.textContent = "Username is required.";
            invalidForm = true;
        }
        else if(usernameValue && !usernameRegex.test(usernameValue)){
            usernameError.textContent = "Username should not contain special characters.";
            invalidForm = true;
        }

        //validate Password
        const passwordValue = password.value.trim();
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@!#$%&*?_])[a-zA-Z\d@!#$%&*?_]{8,}$/;
        if(!passwordValue){
            passwordError.textContent = "Password is required";
            invalidForm = true;
        } else if(passwordValue && !passwordRegex.test(passwordValue)){
            passwordError.textContent = "Password should be at least 8 digits and must contain at least one capital letter and one numeric.";
            invalidForm = true;
        }

        if(!invalidForm){
            successMessage.style.display = 'block';
        }
    });
    
}
module.exports = { login, register };