// ================= CONFIG =================
const apiAuth = "http://localhost:7070/api/auth";
const apiCars = "http://localhost:7070/api/cars";

// ================= SECTIONS =================
const loginSection = document.getElementById("loginSection");
const registerSection = document.getElementById("registerSection");
const dashboardSection = document.getElementById("dashboardSection");

// ================= INITIAL LOAD =================
let token = localStorage.getItem('token');

if (token) {
  console.log('Token found:', token);
  loginSection.classList.add("hidden");
  dashboardSection.classList.remove("hidden");
  fetchCars();
} else {
  console.log('No token found');
  showLogin();
}

// ================= SHOW LOGIN / REGISTER =================
function showLogin() {
  loginSection.classList.remove("hidden");
  registerSection.classList.add("hidden");
  dashboardSection.classList.add("hidden");
}

function showRegister() {
  loginSection.classList.add("hidden");
  registerSection.classList.remove("hidden");
  dashboardSection.classList.add("hidden");
}

// ================= LOGIN =================
document.getElementById("loginForm").addEventListener("submit", e => {
  e.preventDefault();

  const username = document.getElementById("loginUsername").value.trim();
  const password = document.getElementById("loginPassword").value.trim();
  const msg = document.getElementById("loginMessage");

  if (!username || !password) {
    msg.textContent = "Username and password are required.";
    msg.className = "text-red-500 text-center mt-3";
    return;
  }

  fetch(`${apiAuth}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  })
  .then(async res => {
    if (!res.ok) {
      let errorData;
      try { errorData = await res.json(); } catch { errorData = {}; }
      throw new Error(errorData.message || 'Login failed');
    }
    return res.json();
  })
  .then(data => {
    localStorage.setItem('token', data.token);
    token = data.token; // update local variable
    console.log('Token saved:', token);

    loginSection.classList.add("hidden");
    dashboardSection.classList.remove("hidden");
    fetchCars();
  })
  .catch(err => {
    console.error('Error:', err.message);
    msg.textContent = err.message;
    msg.className = "text-red-500 text-center mt-3";
  });
});

document.getElementById("registerForm").addEventListener("submit", async e => {
  e.preventDefault();

  const username = document.getElementById("registerUsername").value.trim();
  const password = document.getElementById("registerPassword").value.trim();
  const msg = document.getElementById("registerMessage");

  // --- Frontend validation matching backend constraints ---
  if (!username) {
    msg.textContent = "Username is required.";
    msg.className = "text-red-500 text-center mt-3";
    return;
  }
  if (username.length < 3 || username.length > 20) {
    msg.textContent = "Username must be between 3 and 20 characters.";
    msg.className = "text-red-500 text-center mt-3";
    return;
  }
  if (!password) {
    msg.textContent = "Password is required.";
    msg.className = "text-red-500 text-center mt-3";
    return;
  }
  if (password.length < 6) {
    msg.textContent = "Password must be at least 6 characters long.";
    msg.className = "text-red-500 text-center mt-3";
    return;
  }

  // --- Send to backend ---
  try {
    const res = await fetch(`${apiAuth}/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    if (!res.ok) {
      const data = await res.json().catch(() => ({}));
      const errMsg = data.message || "Registration failed";
      throw new Error(errMsg);
    }

    msg.textContent = "Registration successful! You can now login.";
    msg.className = "text-green-400 text-center mt-3";

    // Clear form and show login
    document.getElementById("registerForm").reset();
    showLogin();
  } catch (err) {
    msg.textContent = err.message;
    msg.className = "text-red-500 text-center mt-3";
    console.error("Registration error:", err.message);
  }
});


// ================= LOGOUT =================
function logout() {
  localStorage.removeItem('token');
  token = null;
  console.log('Token cleared');

  dashboardSection.classList.add("hidden");

    const loginForm = document.getElementById("loginForm");
  loginForm.reset();
  document.getElementById("loginMessage").textContent = "";

  showLogin();
}

// ================= DASHBOARD / CRUD =================

// ---- FETCH CARS ----
async function fetchCars() {
  if (!token) {
    console.log("No token, cannot fetch cars");
    return;
  }

  try {
    const res = await fetch(apiCars, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    let cars;
    try { cars = await res.json(); } catch { cars = []; }

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
        "Authorization": `Bearer ${token}`
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
      headers: { "Authorization": `Bearer ${token}` }
    });
    const data = await res.json().catch(() => ({}));
    if (!res.ok) throw new Error(data.error || "Failed to delete car");

    fetchCars();
  } catch (err) {
    alert(err.message);
  }
}
