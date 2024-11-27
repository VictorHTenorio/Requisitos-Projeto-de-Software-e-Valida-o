import React from 'react';
import { Link } from 'react-router-dom';

export const OrderSummary = ({ 
  cart, 
  activeStep, 
  couponCode, 
  setCouponCode, 
  onApplyCoupon,
  onStepChange,
  onFinishPurchase,
  canProceed 
}) => {
  return (
    <div className="mt-10 md:mt-0">
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-lg font-medium text-black">Resumo do pedido</h2>

        {activeStep === 'cart' && (
          <div className="mt-6">
            <label htmlFor="coupon" className="block text-sm font-medium text-gray-700">
              Cupom de desconto
            </label>
            <div className="mt-1 flex rounded-md shadow-sm">
              <input
                type="text"
                id="coupon"
                className="focus:ring-amber-500 focus:border-amber-500 flex-1 block w-full rounded-none rounded-l-md sm:text-sm border-gray-300"
                value={couponCode}
                onChange={(e) => setCouponCode(e.target.value)}
              />
              <button
                type="button"
                className="inline-flex items-center px-3 rounded-r-md border border-l-0 border-gray-300 bg-gray-50 text-gray-500 sm:text-sm"
                onClick={onApplyCoupon}
              >
                Aplicar
              </button>
            </div>
          </div>
        )}

        <div className="mt-6 border-t border-gray-200 pt-6">
          <div className="flex justify-between text-base font-medium text-gray-900">
            <p>Subtotal</p>
            <p>R$ {cart?.valorTotal.toFixed(2)}</p>
          </div>
          <div className="flex justify-between text-base font-medium text-gray-900 mt-2">
            <p>Frete</p>
            <p className="text-green-600">Grátis</p>
          </div>
          <div className="flex justify-between text-lg font-bold text-gray-900 mt-4">
            <p>Total</p>
            <p>R$ {cart?.valorTotal.toFixed(2)}</p>
          </div>
        </div>

        <div className="mt-6">
          {activeStep === 'cart' && cart?.itens?.length > 0 && (
            <button
              onClick={() => onStepChange('address')}
              className="w-full bg-amber-600 text-white px-6 py-3 rounded-md font-medium hover:bg-amber-700 transition-colors"
            >
              Continuar para endereço
            </button>
          )}

          {activeStep === 'address' && canProceed && (
            <button
              onClick={() => onStepChange('payment')}
              className="w-full bg-amber-600 text-white px-6 py-3 rounded-md font-medium hover:bg-amber-700 transition-colors"
            >
              Continuar para pagamento
            </button>
          )}

          {activeStep === 'payment' && (
            <button
              onClick={onFinishPurchase}
              className="w-full bg-amber-600 text-white px-6 py-3 rounded-md font-medium hover:bg-amber-700 transition-colors"
            >
              Finalizar compra
            </button>
          )}

          <div className="mt-6 text-center">
            <button
              onClick={() => onStepChange('cart')}
              className="text-amber-600 hover:text-amber-700 font-medium"
            >
              {activeStep !== 'cart' ? '← Voltar' : (
                <Link to="/produtos">Continuar comprando</Link>
              )}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};