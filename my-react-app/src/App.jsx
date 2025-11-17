import React from "react";
import ProfileList from "./components/ProfileList";
import StepCounter from "./components/StepCounter";
import ProductInfo from "./components/ProductInfo";
import './App.css';

function App() {
  return (
    <div className="app-container">
      <h1>Pogi's Sweet Treats</h1>

      <section>
        <h2>1. Profile List</h2>
        <ProfileList />
      </section>

      <section>
        <h2>2. Step Counter</h2>
        <StepCounter />
      </section>

      <section>
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
  );
}

export default App;
