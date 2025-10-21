// ================= CONFIG =================
const apiAuth = "http://localhost:7070/api/auth";
const apiCars = "http://localhost:7070/api/cars";

// ================= SECTIONS =================
const loginSection = document.getElementById("loginSection");
const registerSection = document.getElementById("registerSection");
const dashboardSection = document.getElementById("dashboardSection");

// ================= SHOW LOGIN / REGISTER =================
function showLogin() {
  loginSection.classList.remove("hidden");
  registerSection.classList.add("hidden");

  document.getElementById("loginForm").reset();
  document.getElementById("loginMessage").textContent = "";
}

function showRegister() {
  loginSection.classList.add("hidden");
  registerSection.classList.remove("hidden");

  document.getElementById("registerForm").reset();
  document.getElementById("registerMessage").textContent = "";
}

// ================= LOGIN =================
document.getElementById("loginForm").addEventListener("submit", async e => {
  e.preventDefault();

  const username = document.getElementById("loginUsername").value.trim();
  const password = document.getElementById("loginPassword").value.trim();
  const msg = document.getElementById("loginMessage");

  if (!username || !password) {
    msg.textContent = "Username and password are required.";
    msg.className = "text-red-500 text-center mt-3";
    return;
  }

  try {
    const res = await fetch(`${apiAuth}/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    let data;
    const contentType = res.headers.get("content-type");
    if (contentType && contentType.includes("application/json")) {
      data = await res.json();
    } else {
      const text = await res.text();
      throw new Error(text || "Login failed");
    }

    if (!res.ok) throw new Error(data.error || "Invalid username or password");

    localStorage.setItem("token", data.token);
    localStorage.setItem("username", data.username);

    loginSection.classList.add("hidden");
    dashboardSection.classList.remove("hidden");
    fetchCars();
  } catch (err) {
    msg.textContent = err.message;
    msg.className = "text-red-500 text-center mt-3";
  }
});

// ================= REGISTER =================
document.getElementById("registerForm").addEventListener("submit", async e => {
  e.preventDefault();

  const username = document.getElementById("registerUsername").value.trim();
  const password = document.getElementById("registerPassword").value.trim();
  const msg = document.getElementById("registerMessage");

  // Basic validation
  if (!username || username.length < 3 || username.length > 20) {
    msg.textContent = "Username must be between 3 and 20 characters.";
    msg.className = "text-red-500 text-center mt-3";
    return;
  }
  if (!password || password.length < 6) {
    msg.textContent = "Password must be at least 6 characters long.";
    msg.className = "text-red-500 text-center mt-3";
    return;
  }

  try {
    const res = await fetch(`${apiAuth}/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    const data = await res.json().catch(() => ({}));

    if (!res.ok) {
      // Handle username already taken
      const errMsg = data.error || (data.message && data.message.includes("Username") ? "Username already taken" : "Registration failed");
      throw new Error(errMsg);
    }

    msg.textContent = "Registration successful! You can now login.";
    msg.className = "text-green-400 text-center mt-3";

    showLogin();
  } catch (err) {
    msg.textContent = err.message;
    msg.className = "text-red-500 text-center mt-3";
  }
});

// ================= LOGOUT =================
function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("username");

  document.getElementById("loginForm").reset();
  document.getElementById("loginMessage").textContent = "";

  dashboardSection.classList.add("hidden");
  showLogin();
}

// ================= DASHBOARD / CRUD =================
const token = () => localStorage.getItem("token");

// ---- FETCH CARS ----
async function fetchCars() {
  if (!token()) return;
  try {
    const res = await fetch(apiCars, {
      headers: { Authorization: `Bearer ${token()}` }
    });
    const cars = await res.json();
    if (!res.ok) throw new Error(cars.error || "Failed to fetch cars");

    const tbody = document.getElementById("carTableBody");
    tbody.innerHTML = "";
    let counter = 0;
    cars.forEach(car => {
      tbody.innerHTML += `
      <tr class="text-center hover:bg-emerald-200 transition">
        <td class="border p-2">${++counter}</td>
        <td class="border p-2">${car.licensePlateNumber}</td>
        <td class="border p-2">${car.make}</td>
        <td class="border p-2">${car.model}</td>
        <td class="border p-2">${car.year}</td>
        <td class="border p-2">${car.color}</td>
        <td class="border p-2">${car.bodyType}</td>
        <td class="border p-2">${car.engineType}</td>
        <td class="border p-2">${car.transmission}</td>
        <td class="border p-2">
          <button onclick="openEditModal(${car.carId}, '${car.licensePlateNumber}', '${car.make}', '${car.model}', ${car.year}, '${car.color}', '${car.bodyType}', '${car.engineType}', '${car.transmission}')"
            class="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600">Edit</button>
          <button onclick="deleteCar(${car.carId})"
            class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">Delete</button>
        </td>
      </tr>`;
    });
  } catch (err) {
    alert(err.message);
  }
}

// ---- MODAL HANDLING ----
function openCreateModal() {
  document.getElementById("carForm").reset();
  document.getElementById("carId").value = "";
  document.getElementById("modalTitle").innerText = "Add Car";
  document.getElementById("carModal").classList.remove("hidden");
}

function openEditModal(carId, licensePlateNumber, make, model, year, color, bodyType, engineType, transmission) {
  document.getElementById("carId").value = carId;
  document.getElementById("licensePlateNumber").value = licensePlateNumber;
  document.getElementById("make").value = make;
  document.getElementById("model").value = model;
  document.getElementById("year").value = year;
  document.getElementById("color").value = color;
  document.getElementById("bodyType").value = bodyType;
  document.getElementById("engineType").value = engineType;
  document.getElementById("transmission").value = transmission;

  document.getElementById("modalTitle").innerText = "Edit Car";
  document.getElementById("carModal").classList.remove("hidden");
}

function closeModal() {
  document.getElementById("carModal").classList.add("hidden");
}

// ---- SAVE CAR ----
async function saveCar(e) {
  e.preventDefault();
  const id = document.getElementById("carId").value;
  const car = {
    licensePlateNumber: document.getElementById("licensePlateNumber").value,
    make: document.getElementById("make").value,
    model: document.getElementById("model").value,
    year: parseInt(document.getElementById("year").value),
    color: document.getElementById("color").value,
    bodyType: document.getElementById("bodyType").value,
    engineType: document.getElementById("engineType").value,
    transmission: document.getElementById("transmission").value
  };
  const method = id ? "PUT" : "POST";
  const url = id ? `${apiCars}/${id}` : apiCars;

  try {
    const res = await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token()}`
      },
      body: JSON.stringify(car)
    });
    const data = await res.json();
    if (!res.ok) throw new Error(data.error || "Failed to save car");

    closeModal();
    fetchCars();
  } catch (err) {
    alert(err.message);
  }
}

// ---- DELETE CAR ----
async function deleteCar(id) {
  if (!confirm("Delete this car?")) return;
  try {
    const res = await fetch(`${apiCars}/${id}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token()}` }
    });
    const data = await res.json().catch(() => ({}));
    if (!res.ok) throw new Error(data.error || "Failed to delete car");

    fetchCars();
  } catch (err) {
    alert(err.message);
  }
}
