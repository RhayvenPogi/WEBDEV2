import { useState } from "react";

function StepCounter() {
  const [count, setCount] = useState(0);
  const [step, setStep] = useState(1);

  const handleAdd = () => {
    setCount(prev => prev + Number(step));
  };

  return (
    <div className="step-counter">
      <h3>Counter</h3>
      <p>{count}</p>

      <input
        type="number"
        value={step}
        onChange={(e) => setStep(e.target.value)}
      />

      <button onClick={handleAdd}>Add</button>
    </div>
  );
}

export default StepCounter;
