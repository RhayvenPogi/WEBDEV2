
import ProfileCard from "./ProfileCard";

function ProfileList() {
  return (
    <div>
      <ProfileCard name="Jose" age={25} role="Developer" />
      <ProfileCard name="Andres" age={30} role="Designer" />
      <ProfileCard name="Gabriela" age={28} role="Tester" />
    </div>
  );
}

export default ProfileList;
