import React from 'react';
import { Link } from 'react-router-dom';
import { ShoppingCart, Bell } from 'lucide-react';
import beequeenLogo from '../assets/beequen.png';

const Header = () => {
  return (
    <header className="bg-black shadow-md">
      <div className="max-w-7xl mx-auto px-4 py-4">
        <div className="flex justify-between items-center">

          {/* Logo central */}
          <div className="flex justify-between items-center">
            <Link to="/" className="block w-20 h-20">
              <img 
                src={beequeenLogo}
                alt="Beequeen Logo" 
                className="w-full h-full object-contain"
              />
            </Link>
          </div>

          {/* Navegação e ícones */}
          <div className="flex-1 flex justify-end items-center space-x-8">
            <nav className="flex space-x-8">
              <Link to="/login" className="text-amber-400 hover:text-amber-300">Login</Link>
              <Link to="/admin" className="text-amber-400 hover:text-amber-300">Admin</Link>
              <Link to="/wishlist" className="text-amber-400 hover:text-amber-300">WISHLIST</Link>
            </nav>
            <div className="flex items-center space-x-4">
              <Bell className="h-6 w-6 text-amber-400 cursor-pointer hover:text-amber-300" />
              <Link to="/carrinho">
                <ShoppingCart className="h-6 w-6 text-amber-400 cursor-pointer hover:text-amber-300" />
              </Link>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;