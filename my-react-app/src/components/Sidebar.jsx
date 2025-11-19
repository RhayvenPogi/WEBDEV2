const Sidebar = ({ isOpen }) => {
  const menuItems = [
    { label: "Home", link: "/" },
    { label: "About", link: "/about" },
    { label: "Users", link: "/users" },
    { label: "Settings", link: "/settings" }
  ];

  return (
    <div
      className={`bg-blue-700 text-white w-64 p-6 space-y-4 
        fixed inset-y-0 left-0 transform transition-transform duration-300 
        ${isOpen ? "translate-x-0" : "-translate-x-full"}`}
    >
      <h2 className="text-2xl font-bold mb-6">Menu</h2>

      <ul className="space-y-3">
        {menuItems.map((item, index) => (
          <li
            key={index}
          >
            {item.label}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;
