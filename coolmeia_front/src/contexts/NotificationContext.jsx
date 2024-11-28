import React, { createContext, useContext, useState } from 'react';

const NotificationContext = createContext();

export const useNotification = () => {
  const context = useContext(NotificationContext);
  if (!context) {
    throw new Error('useNotification must be used within a NotificationProvider');
  }
  return context;
};

export const NotificationProvider = ({ children }) => {
  const [hasLowStockItems, setHasLowStockItems] = useState(false);

  return (
    <NotificationContext.Provider value={{ hasLowStockItems, setHasLowStockItems }}>
      {children}
    </NotificationContext.Provider>
  );
};