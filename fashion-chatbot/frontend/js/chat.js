const chatBox = document.getElementById("chatBox");

// -- GET DATA --
const category = localStorage.getItem("selectedCategory") || "general";
let chatId = localStorage.getItem("currentChatId");

// If no chatId → create new
if (!chatId) {
  chatId = "chat_" + Date.now();
  localStorage.setItem("currentChatId", chatId);
}

// -- LOAD CHAT --
window.onload = function () {
  document.getElementById("categoryTitle").innerText =
    category.toUpperCase() + " CHAT";

  loadChatHistory();
};

// -- SEND MESSAGE --
async function sendMessage() {
  const input = document.getElementById("userInput");
  const message = input.value.trim();
  if (!message) return;
  addMessage("You", message);
  saveMessage("You", message);
  input.value = "";
  try {
    const response = await fetch("http://localhost:3030/api/chat", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        message: message,
        category: category,
        chatId: chatId   
      })
    });
    const reply = await response.text();
    addMessage("Bot", reply);
    saveMessage("Bot", reply);
  } catch (err) {
    addMessage("Bot", "Server error. Make sure backend is running.");
  }
}

// -- DISPLAY MESSAGE --
function addMessage(sender, text) {
  const div = document.createElement("p");
  div.className = sender === "You" ? "user" : "bot";
  div.innerText = sender + ": " + text;
  chatBox.appendChild(div);
  chatBox.scrollTop = chatBox.scrollHeight;
}

// -- SAVE MESSAGE --
function saveMessage(sender, text) {
  let chats = JSON.parse(localStorage.getItem("chatHistory")) || [];
  let chat = chats.find(c => c.chatId === chatId);
  if (!chat) {
    chat = {
      chatId: chatId,      
      category: category,
      messages: []
    };
    chats.push(chat);
  }
  chat.messages.push({ sender, text });
  localStorage.setItem("chatHistory", JSON.stringify(chats));
}

// -- LOAD CHAT HISTORY --
function loadChatHistory() {
  let chats = JSON.parse(localStorage.getItem("chatHistory")) || [];
  let chat = chats.find(c => c.chatId === chatId);
  if (!chat) return;
  chat.messages.forEach(msg => {
    addMessage(msg.sender, msg.text);
  });
}

// -- NEW CHAT --
function startNewChat() {
  const newId = "chat_" + Date.now();
  localStorage.setItem("currentChatId", newId);
  chatBox.innerHTML = "";
}