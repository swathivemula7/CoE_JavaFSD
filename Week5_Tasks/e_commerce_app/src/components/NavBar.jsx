import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="bg-gray-600 p-4 text-white shadow-md">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/" className="text-xl font-bold">
          E-Commerce
        </Link>
        <div>
          <Link to="/" className="mr-4 hover:text-gray-300 transition-all">
            Home
          </Link>
          <Link to="/cart" className="hover:text-gray-300 transition-all">
            Cart
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
