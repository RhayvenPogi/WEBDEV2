import React, { useState } from "react";
import ProfileList from "./components/ProfileList";
import StepCounter from "./components/StepCounter";
import ProductInfo from "./components/ProductInfo";
import Sidebar from "./components/Sidebar";
import Header from "./components/Header";
import Footer from "./components/Footer";
import "./index.css";

function App() {
  const [sidebarToggle, setSidebarToggle] = useState(true);

  function toggleSidebar() {
    setSidebarToggle(!sidebarToggle);
  }

  return (
    <div className="bg-blue-700 min-h-screen flex ">
      <div className="flex-1 flex-col">
        <Sidebar isOpen={sidebarToggle} />
      </div>
      
      <Header onSidebarToggle={toggleSidebar} />

      <main className="flex-1 bg-slate-400 p-6">
        <p>{sidebarToggle && "sidebar open"}</p>

        <div className="app-container">
          <h1>Pogi's Sweet Treats</h1>

          <section className="card">
            <h2>1. Profile List</h2>
            <ProfileList />
          </section>

          <section className="card">
            <h2>2. Step Counter</h2>
            <StepCounter />
          </section>

          <section className="card">
            <h2>3. Products</h2>

            <ProductInfo
              name="Chocolate Cake"
              price={250}
              details="Rich and moist chocolate cake topped with chocolate ganache."
            />

            <ProductInfo
              name="Strawberry Shortcake"
              price={200}
              details="Layers of sponge cake, fresh strawberries, and whipped cream."
            />

            <ProductInfo
              name="Mango Float"
              price={150}
              details="Sweet and creamy dessert with layers of mango and cream."
            />
          </section>
        </div>
      </main>

      <Footer />
    </div>
  );
}

export default App;
