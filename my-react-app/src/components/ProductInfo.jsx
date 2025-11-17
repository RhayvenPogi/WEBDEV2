import React, { useState } from "react";

function ProductInfo({ name, price, details }) {
  const [showDetails, setShowDetails] = useState(false);

  return (
    <div className="product-info">
      <h3>{name}</h3>
      <p>
        <strong>Price:</strong> {price}
      </p>
      {showDetails && <p>{details}</p>}
      <button onClick={() => setShowDetails(!showDetails)}>
        {showDetails ? "Hide Details" : "Show Details"}
      </button>
    </div>
  );
}

export default ProductInfo;
