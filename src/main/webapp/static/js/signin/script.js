function togglePasswordVisibility() {
    let passwordInput = document.getElementById("password");
    let visibilityIcon = document.querySelector(".input-group-append .material-symbols-outlined");
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        visibilityIcon.textContent = "visibility_off";
    } else {
        passwordInput.type = "password";
        visibilityIcon.textContent = "visibility";
    }
}

function validateForm(event) {
    event.preventDefault();

    const nameInput = document.getElementById("name");
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const emailError = document.getElementById("email-error");
    const passwordError = document.getElementById("password-error");

    emailError.textContent = "";
    passwordError.textContent = "";

    let isValid = true;

    if (nameInput) {
        const name = nameInput.value;
        const nameError = document.getElementById("name-error");
        nameError.textContent = "";
        if (name.length < 3) {
            nameError.textContent = "Please enter a valid name.";
            isValid = false;
        }
    }

    if (!validateEmail(email)) {
        emailError.textContent = "Please enter a valid email address.";
        isValid = false;
    }

    if (!validatePassword(password)) {
        passwordError.textContent = "Password must contain: ";
        const requirements = [];
        if (password.length < 8) requirements.push("at least 8 characters");
        if (!/[A-Z]/.test(password)) requirements.push("one uppercase letter");
        if (!/[a-z]/.test(password)) requirements.push("one lowercase letter");
        if (!/\d/.test(password)) requirements.push("one number");
        if (!/[@$!%*?&]/.test(password)) requirements.push("one special character");
        passwordError.textContent += requirements.join(", ") + ".";
        isValid = false;
    }

    if (isValid) {
        window.location.href = "home.html";
    }
}

function validateEmail(email) {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(email);
}

function validatePassword(password) {
    const re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return re.test(password);
}
