import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { ShoppingCart, Trash2 } from 'lucide-react';

const CartPage = () => {
  const [cartItems, setCartItems] = useState([
    { id: 1, name: "Camiseta Oversized", price: 129.90, quantity: 1, image: "https://via.placeholder.com/100" },
    { id: 2, name: "Calça Cargo", price: 259.90, quantity: 2, image: "https://via.placeholder.com/100" },
  ]);
  const [couponCode, setCouponCode] = useState('');
  const [cep, setCep] = useState('');
  const [address, setAddress] = useState({});

  const handleRemoveItem = (itemId) => {
    setCartItems(cartItems.filter(item => item.id !== itemId));
  };

  const handleQuantityChange = (itemId, newQuantity) => {
    setCartItems(cartItems.map(item => 
      item.id === itemId ? { ...item, quantity: newQuantity } : item
    ));
  };

  const calculateTotal = () => {
    return cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
  };

  const handleCepChange = async (e) => {
    const enteredCep = e.target.value;
    setCep(enteredCep);

    if (enteredCep.length === 8) {
      try {
        const response = await fetch(`https://viacep.com.br/ws/${enteredCep}/json/`);
        const data = await response.json();

        if (!data.erro) {
          setAddress({
            street: data.logradouro,
            neighborhood: data.bairro,
            city: data.localidade,
            state: data.uf,
          });
        } else {
          setAddress({});
        }
      } catch (error) {
        console.error('Erro ao buscar o endereço:', error);
      }
    } else {
      setAddress({});
    }
  };

  return (
    <div className="min-h-screen bg-amber-50">
      <Header />

      <main className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-black mb-8">Carrinho de Compras</h1>

        {cartItems.length === 0 ? (
          <div className="text-center">
            <ShoppingCart className="mx-auto h-12 w-12 text-amber-400" />
            <h3 className="mt-2 text-sm font-medium text-black">Seu carrinho está vazio</h3>
            <Link to="/produtos" className="mt-6 inline-flex items-center px-4 py-2 border border-transparent text-base font-medium rounded-md shadow-sm text-white bg-amber-600 hover:bg-amber-700">
              Continuar comprando
            </Link>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {/* Itens do carrinho */}
            <div className="md:col-span-2">
              <ul className="divide-y divide-amber-200">
                {cartItems.map((item) => (
                  <li key={item.id} className="py-6 flex">
                    <div className="flex-shrink-0 w-24 h-24 border border-amber-200 rounded-md overflow-hidden">
                      <img
                        src={item.image}
                        alt={item.name}
                        className="w-full h-full object-center object-cover"
                      />
                    </div>

                    <div className="ml-4 flex-1 flex flex-col">
                      <div>
                        <div className="flex justify-between text-base font-medium text-black">
                          <h3>{item.name}</h3>
                          <p className="ml-4">R$ {item.price.toFixed(2)}</p>
                        </div>
                      </div>
                      <div className="flex-1 flex items-end justify-between text-sm">
                        <div className="flex items-center border border-amber-400 rounded">
                          <button 
                            className="text-black px-2 py-1 hover:bg-amber-400 transition-colors"
                            onClick={() => handleQuantityChange(item.id, item.quantity - 1)}
                            disabled={item.quantity === 1}
                          >
                            -
                          </button>
                          <span className="text-black px-4">{item.quantity}</span>
                          <button 
                            className="text-black px-2 py-1 hover:bg-amber-400 transition-colors" 
                            onClick={() => handleQuantityChange(item.id, item.quantity + 1)}
                          >
                            +
                          </button>
                        </div>
                        <div className="flex">
                          <button
                            type="button"
                            className="font-medium text-amber-600 hover:text-amber-500"
                            onClick={() => handleRemoveItem(item.id)}
                          >
                            <Trash2 className="h-5 w-5" />
                          </button>
                        </div>
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            </div>

            {/* Resumo do pedido */}
            <div className="mt-10 md:mt-0">
              <div className="bg-white shadow rounded-lg p-6">
                <h2 className="text-lg font-medium text-black">Resumo do pedido</h2>

                <div className="mt-6">
                  <label htmlFor="coupon-code" className="block text-sm font-medium text-black">
                    Cupom de desconto
                  </label>
                  <div className="mt-1 flex rounded-md shadow-sm">
                    <input
                      type="text"
                      name="coupon-code"
                      id="coupon-code"
                      value={couponCode}
                      onChange={(e) => setCouponCode(e.target.value)}
                      className="focus:ring-amber-500 focus:border-amber-500 flex-1 block w-full rounded-none rounded-l-md sm:text-sm border-amber-400"
                      placeholder="Insira o código"
                    />
                    <button
                      type="button"
                      className="inline-flex items-center px-3 rounded-r-md border border-l-0 border-amber-400 bg-amber-50 text-black sm:text-sm"
                    >
                      Aplicar
                    </button>
                  </div>
                </div>

                <div className="mt-6">
                  <label htmlFor="cep" className="block text-sm font-medium text-black">
                    CEP
                  </label>
                  <div className="mt-1">
                    <input
                      type="text"
                      name="cep"
                      id="cep"
                      value={cep}
                      onChange={handleCepChange}
                      className="focus:ring-amber-500 focus:border-amber-500 block w-full rounded-md sm:text-sm border-amber-400"
                      placeholder="00000-000"
                    />
                  </div>
                  {address.street && (
                    <div className="mt-2">
                      <p>{address.street}</p>
                      <p>{address.neighborhood}</p>
                      <p>{address.city} - {address.state}</p>
                    </div>
                  )}
                </div>

                <div className="border-t border-amber-200 mt-8 pt-8">
                  <div className="flex justify-between text-base font-medium text-black">
                    <p>Subtotal</p>
                    <p>R$ {calculateTotal().toFixed(2)}</p>
                  </div>
                  <p className="mt-0.5 text-sm text-black">Frete e impostos calculados no checkout.</p>
                  <div className="mt-6">
                    <button
                      className="w-full inline-flex justify-center items-center px-6 py-3 border border-transparent rounded-md shadow-sm text-base font-medium text-white bg-amber-600 hover:bg-amber-700"
                    >
                      Finalizar compra
                    </button>
                  </div>
                  <div className="mt-6 flex justify-center text-sm text-center text-black">
                    <p>
                      ou{' '}
                      <Link to="/produtos" className="font-medium text-amber-600 hover:text-amber-500">
                        Continuar comprando
                      </Link>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}
      </main>

      <Footer />
    </div>
  );
};

export default CartPage;