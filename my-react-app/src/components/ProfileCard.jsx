import React from "react";

function ProfileCard({ name, age, role }) {
  return (
    <div className="profile-card">
      <p>Name: {name}</p>
      <p>Age: {age}</p>
      <p>Role: {role}</p>
      <hr />
    </div>
  );
}

export default ProfileCard;
