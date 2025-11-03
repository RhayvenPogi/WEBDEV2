const apiBase = "http://localhost:8000/api/products";

document.addEventListener("DOMContentLoaded", fetchProducts);

function fetchProducts() {
    fetch(apiBase)
        .then(res => res.json())
        .then(products => {
            const body = document.getElementById("productTableBody");
            body.innerHTML = "";
            let counter = 0;
            products.forEach(product => {
                body.innerHTML += `
                <tr class="text-center">
                    <td class="border p-2">${++counter}</td>
                    <td class="border p-2">${product.name}</td>
                    <td class="border p-2">${product.description}</td>
                    <td class="border p-2">${product.unit}</td>
                    <td class="border p-2">${product.price}</td>
                    <td class="border p-2">${product.stock}</td>
                    <td class="border p-2">
                        <button onclick="openEditModal(${product.id}, \`${product.name}\`, \`${product.description}\`, \`${product.unit}\`, ${product.price}, ${product.stock})" class="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600">Edit</button>
                        <button onclick="deleteProduct(${product.id})" class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">Delete</button>
                    </td>
                </tr>`;
            });
        })
        .catch(err => console.error(err));
}

function openCreateModal() {
    document.getElementById("productForm").reset();
    clearErrors();
    document.getElementById("productId").value = "";
    document.getElementById("modalTitle").innerText = "Add Product";
    document.getElementById("productModal").classList.remove("hidden");
}

function openEditModal(id, name, description, unit, price, stock) {
    clearErrors();
    document.getElementById("productId").value = id;
    document.getElementById("productName").value = name;
    document.getElementById("productDescription").value = description;
    document.getElementById("productUnit").value = unit;
    document.getElementById("productPrice").value = price;
    document.getElementById("productStock").value = stock;
    document.getElementById("modalTitle").innerText = "Edit Product";
    document.getElementById("productModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("productModal").classList.add("hidden");
}

function clearErrors() {
    const inputs = ["productName", "productDescription", "productUnit", "productPrice", "productStock"];
    inputs.forEach(id => {
        const field = document.getElementById(id);
        field.classList.remove("border-red-500");
        const error = document.getElementById(id + "Error");
        if (error) error.innerText = "";
    });
}

function saveProduct(e) {
    e.preventDefault();
    clearErrors();

    const id = document.getElementById("productId").value.trim();
    const name = document.getElementById("productName").value.trim();
    const description = document.getElementById("productDescription").value.trim();
    const unit = document.getElementById("productUnit").value.trim();
    const price = parseFloat(document.getElementById("productPrice").value);
    const stock = parseInt(document.getElementById("productStock").value);

    let valid = true;

    if (!name) {
        showError("productName", "Name is required");
        valid = false;
    }
    if (!description) {
        showError("productDescription", "Description is required");
        valid = false;
    }
    if (!unit) {
        showError("productUnit", "Unit is required");
        valid = false;
    }
    if (isNaN(price) || price < 1) {
        showError("productPrice", "Price must be at least 1");
        valid = false;
    }
    if (isNaN(stock) || stock < 1) {
        showError("productStock", "Stock must be at least 1");
        valid = false;
    }

    if (!valid) return;

    const product = { name, description, unit, price, stock };
    const method = id ? "PUT" : "POST";
    const url = id ? `${apiBase}/${id}` : apiBase;

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product)
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to save product");
            return res.json();
        })
        .then(() => {
            closeModal();
            fetchProducts();
        })
        .catch(err => console.error(err));
}

function showError(id, message) {
    const field = document.getElementById(id);
    field.classList.add("border-red-500");
    let error = document.getElementById(id + "Error");
    if (!error) {
        error = document.createElement("div");
        error.id = id + "Error";
        error.className = "text-red-500 text-sm mt-1";
        field.parentNode.appendChild(error);
    }
    error.innerText = message;
}

function deleteProduct(id) {
    if (!confirm("Delete this product?")) return;
    fetch(`${apiBase}/${id}`, { method: "DELETE" })
        .then(() => fetchProducts())
        .catch(err => console.error(err));
}
