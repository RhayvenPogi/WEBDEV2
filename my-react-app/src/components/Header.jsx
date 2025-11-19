const Header = ({ onSidebarToggle }) => {
    return (
        <header>
            <button onClick={onSidebarToggle}>Menu</button>
            <h1>Pogi's Sweet Treats</h1>
        </header>
    );
};

export default Header;
