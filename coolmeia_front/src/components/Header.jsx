import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ShoppingCart, Bell, LogOut } from 'lucide-react';
import { useNotification } from '../contexts/NotificationContext';
import beequeenLogo from '../assets/beequen.png';
import { useAuth } from '../contexts/AuthContext';

const Header = () => {
  const { user, logout } = useAuth();
  const [userName, setUserName] = useState('');
  const { hasLowStockItems } = useNotification();

  useEffect(() => {
    const storedUserName = localStorage.getItem('userName');
    if (storedUserName) {
      setUserName(storedUserName);
    }
  }, []);

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

          {/* Mensagem de saudação */}
          {userName && (
            <div className="text-amber-400">
              Olá, {userName}! Seja bem-vindo(a) à Coolmeia.
            </div>
          )}

          {/* Navegação e ícones */}
          <div className="flex-1 flex justify-end items-center space-x-8">
            <nav className="flex space-x-8">
              {!user && <Link to="/login" className="text-amber-400 hover:text-amber-300">Login</Link>}
              <Link to="/produtos" className="text-amber-400 hover:text-amber-300">+Produtos</Link>
              <Link to="/admin" className="text-amber-400 hover:text-amber-300">Admin</Link>
              <Link to="/wishlist" className="text-amber-400 hover:text-amber-300">Wishlist</Link>
              {user && <button onClick={() => {
                logout(); // Chama a função de logout
                setUserName(''); // Limpa o nome do usuário no estado local
                }} className="text-amber-400 hover:text-amber-300 flex items-center">
                <LogOut className="h-4 w-4 mr-1" /> Logout
              </button>}
              </nav>
            <div className="flex items-center space-x-4">
              <div className="relative">
                <Bell className="h-6 w-6 text-amber-400 cursor-pointer hover:text-amber-300" />
                {hasLowStockItems && (
                  <div className="absolute -top-1 -right-1 w-3 h-3 bg-red-500 rounded-full" />
                )}
              </div>
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