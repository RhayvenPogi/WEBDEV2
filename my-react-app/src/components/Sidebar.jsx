const Sidebar = () => {
    const menuItems = [
        { label: "Home", link: "/" },
        { label: "About", link: "/about" },
        { label: "Users", link: "/users" },
        { label: "Settings", link: "/settings" }
    ];
    
    return (
        <div className="sidebar">
            <ul>
                {menuItems.map((item, index) => (
                    <li key={index}>{item.label}</li>
                ))}
            </ul>
        </div>
    );
};

export default Sidebar;