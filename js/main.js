const apiBase = "http://localhost:7070/api/cars";

// =================== DOM CONTENT LOADED ===================
document.addEventListener("DOMContentLoaded", fetchCars);

// =================== FETCH CARS ===================
function fetchCars() {
    fetch(apiBase)
        .then(res => res.json())
        .then(cars => {
            const tbody = document.getElementById("carTableBody");
            tbody.innerHTML = "";
            let counter = 0;

            cars.forEach(car => {
                tbody.innerHTML += `
                    <tr class="text-center hover:bg-emerald-900 transition">
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
                    </tr>
                `;
            });
        })
        .catch(err => console.error("Error fetching cars:", err));
}

// =================== MODAL ===================
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

// =================== SAVE CAR ===================
function saveCar(e) {
    e.preventDefault();

    const id = document.getElementById("carId").value;
    const car = {
        licensePlateNumber: document.getElementById("licensePlateNumber").value,
        make: document.getElementById("make").value,
        model: document.getElementById("model").value,
        year: document.getElementById("year").value,
        color: document.getElementById("color").value,
        bodyType: document.getElementById("bodyType").value,
        engineType: document.getElementById("engineType").value,
        transmission: document.getElementById("transmission").value
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${apiBase}/${id}` : apiBase;

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(car)
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed to save car");
        return res.json();
    })
    .then(() => {
        closeModal();
        fetchCars();
    })
    .catch(err => console.error("Error saving car:", err));
}

// =================== DELETE CAR ===================
function deleteCar(id) {
    if (!confirm("Delete this car?")) return;

    fetch(`${apiBase}/${id}`, { method: "DELETE" })
        .then(res => {
            if (!res.ok) throw new Error("Failed to delete car");
            fetchCars();
        })
        .catch(err => alert("Error deleting car: " + err));
}
