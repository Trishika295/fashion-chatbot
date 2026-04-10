function login() {
  const email = document.getElementById("email").value.trim();
  const name = document.getElementById("name").value.trim();

  const users = JSON.parse(localStorage.getItem("users") || "[]");
  const user = users.find(u => u.email === email && u.name === name);

  if (!user) {
    alert("User not found. Please create an account.");
    return;
  }

  localStorage.setItem("user", JSON.stringify(user));
  window.location.href = "index.html";
}

function createAccount() {
  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password")?.value.trim();

  if (!name || !email || (document.getElementById("password") && !password)) {
    alert("Fill all fields properly");
    return;
  }

  const users = JSON.parse(localStorage.getItem("users") || "[]");

  if (users.some(u => u.email === email)) {
    alert("Account already exists. Please login.");
    return;
  }

  users.push({ name, email, password });
  localStorage.setItem("users", JSON.stringify(users));
  alert("Account created successfully! Please login.");
  window.location.href = "login.html";
}