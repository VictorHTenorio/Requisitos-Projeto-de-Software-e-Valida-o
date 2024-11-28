import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NewProductsPage from './pages/NewProductsPage';
import WishlistPage from './pages/WishlistPage';
import AllProductsPage from './pages/AllProductsPage';
import CartPage from './pages/CartPage';
import CustomerRegistrationPage from './pages/CustomerRegistrationPage';
import ProductRegistrationPage from './pages/ProductRegistrationPage';
import LoginPage from './pages/LoginPage';
import CouponRegistrationPage from './pages/CouponRegistrationPage';
import CategoryRegistrationPage from './pages/CategoryRegistrationPage';
import AdminMenuPage from './pages/AdminMenuPage';
import { AuthProvider } from './contexts/AuthContext';
import PurchaseSuccessPage from './pages/PurchaseSuccessPage';
import { NotificationProvider } from './contexts/NotificationContext';


function App() {
  return (
    <AuthProvider>
    <NotificationProvider>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<NewProductsPage />} />
        <Route path="/wishlist" element={<WishlistPage />} />
        <Route path="/produtos" element={<AllProductsPage />} />
        <Route path="/carrinho" element={<CartPage />} />
        <Route path="/cadastro" element={<CustomerRegistrationPage />} />
        <Route path="/cadastro-produto" element={<ProductRegistrationPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/cupons" element={<CouponRegistrationPage />} />
        <Route path="/categorias" element={<CategoryRegistrationPage />} />
        <Route path="/admin" element={<AdminMenuPage />} />
        <Route path="/compra-sucesso" element={<PurchaseSuccessPage />} />
      </Routes>
    </BrowserRouter>
    </NotificationProvider>
    </AuthProvider>
  );
}

export default App;