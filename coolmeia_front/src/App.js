import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NewProductsPage from './pages/NewProductsPage';
import WishlistPage from './pages/WishlistPage';
import AllProductsPage from './pages/AllProductsPage';
import CartPage from './pages/CartPage';
import CustomerRegistrationPage from './pages/CustomerRegistrationPage';
import ProductRegistrationPage from './pages/ProductRegistrationPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<NewProductsPage />} />
        <Route path="/wishlist" element={<WishlistPage />} />
        <Route path="/produtos" element={<AllProductsPage />} />
        <Route path="/carrinho" element={<CartPage />} />
        <Route path="/cadastro" element={<CustomerRegistrationPage />} />
        <Route path="/cadastro-produto" element={<ProductRegistrationPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;