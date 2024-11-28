import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  });

  const login = (cpf, nome) => {
    const userData = { cpf, nome }; // Inclui CPF e nome
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData)); // Salva todo o objeto no localStorage
    localStorage.setItem('userName', nome); // Salva o nome separadamente
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
    localStorage.removeItem('userName');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);