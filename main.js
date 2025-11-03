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
                    <td class="border p-2">${product.price}</td>
                    <td class="border p-2">${product.stock}</td>
                    <td class="border p-2">
                        <button onclick="openEditModal(${product.id}, \`${product.name}\`, \`${product.description}\`, ${product.price}, ${product.stock})" class="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600">Edit</button>
                        <button onclick="deleteProduct(${product.id})" class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">Delete</button>
                    </td>
                </tr>`;
            });
        })
        .catch(err => console.error(err));
}

function openCreateModal() {
    document.getElementById("productForm").reset();
    document.getElementById("productId").value = "";
    document.getElementById("modalTitle").innerText = "Add Product";
    document.getElementById("productModal").classList.remove("hidden");
}

function openEditModal(id, name, description, price, stock) {
    document.getElementById("productId").value = id;
    document.getElementById("productName").value = name;
    document.getElementById("productDescription").value = description;
    document.getElementById("productPrice").value = price;
    document.getElementById("productStock").value = stock;
    document.getElementById("modalTitle").innerText = "Edit Product";
    document.getElementById("productModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("productModal").classList.add("hidden");
}

function saveProduct(e) {
    e.preventDefault();

    const id = document.getElementById("productId").value;
    const name = document.getElementById("productName").value;
    const description = document.getElementById("productDescription").value;
    const price = parseFloat(document.getElementById("productPrice").value);
    const stock = parseInt(document.getElementById("productStock").value);

    const product = { name, description, price, stock };
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

function deleteProduct(id) {
    if (!confirm("Delete this product?")) return;
    fetch(`${apiBase}/${id}`, { method: "DELETE" })
        .then(res => {
            if (!res.ok) throw new Error("Failed to delete product");
            fetchProducts();
        })
        .catch(err => console.error(err));
}
