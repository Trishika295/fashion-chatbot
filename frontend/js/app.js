// -- USER LOAD --
document.addEventListener('DOMContentLoaded', () => {
  const user = JSON.parse(localStorage.getItem("user"));
  if (user) {
    document.getElementById("userId").textContent =
      user.email || user.name || "User";
  }

  loadRecentChats();
});

// -- NAVIGATION --
function goHome() {
  window.location.href = "index.html";
}

function signOut() {
  localStorage.removeItem("user");
  window.location.href = "login.html";
}

// -- START NEW CHAT --
function startChat(category) {
  const chatId = "chat_" + Date.now();
  localStorage.setItem("selectedCategory", category);
  localStorage.setItem("currentChatId", chatId);
  window.location.href = "chat.html";
}

// -- OPEN OLD CHAT --
function openChat(chatId, category) {
  localStorage.setItem("selectedCategory", category);
  localStorage.setItem("currentChatId", chatId);
  window.location.href = "chat.html";
}

// -- RECENT CHATS --
function loadRecentChats() {
  const chats = JSON.parse(localStorage.getItem("chatHistory")) || [];
  const div = document.querySelector(".recent-chats");
  if (!div) return;
  if (chats.length === 0) {
    div.innerHTML = "<h4>RECENT CHATS</h4><p>No recent chats</p>";
    return;
  }

  let html = "<h4>RECENT CHATS</h4>";
  chats.slice(-5).reverse().forEach(chat => {
    html += `
      <div class="recent-item"
           onclick="openChat('${chat.chatId}', '${chat.category}')">
        ${formatCategory(chat.category)} (${chat.messages.length} msgs)
      </div>
    `;
  });
  div.innerHTML = html;
}

// -- FORMAT CATEGORY --
function formatCategory(cat) {
  switch(cat) {
    case "girls": return "Girls Style";
    case "boys": return "Boys Style";
    case "formal": return "Formal & Events";
    case "accessories": return "Accessories";
    default: return cat;
  }
}

// -- CLEAR CHATS --
function clearChats() {
  if (confirm("Are you sure you want to clear all chat history?")) {
    localStorage.removeItem("chatHistory");
    loadRecentChats();
  }
}

function signOut() {
  localStorage.removeItem("user");
  window.location.href = "login.html";
}