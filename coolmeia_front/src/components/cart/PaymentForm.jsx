import React from 'react';

export const PaymentForm = ({ cardData, setCardData }) => {
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-lg font-medium mb-4">Dados do Cartão</h2>
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Nome no Cartão</label>
          <input
            type="text"
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-amber-500 focus:ring-amber-500"
            value={cardData.name}
            onChange={(e) => setCardData({ ...cardData, name: e.target.value })}
            placeholder="Como está no cartão"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Número do Cartão</label>
          <input
            type="text"
            maxLength="16"
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-amber-500 focus:ring-amber-500"
            value={cardData.number}
            onChange={(e) => setCardData({ ...cardData, number: e.target.value.replace(/\D/g, '') })}
            placeholder="0000000000000000"
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Validade</label>
            <input
              type="text"
              maxLength="5"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-amber-500 focus:ring-amber-500"
              value={cardData.validity}
              onChange={(e) => {
                let value = e.target.value.replace(/\D/g, '');
                if (value.length >= 2) {
                  value = value.slice(0, 2) + '/' + value.slice(2);
                }
                setCardData({ ...cardData, validity: value });
              }}
              placeholder="MM/YY"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">CVV</label>
            <input
              type="text"
              maxLength="3"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-amber-500 focus:ring-amber-500"
              value={cardData.cvv}
              onChange={(e) => setCardData({ ...cardData, cvv: e.target.value.replace(/\D/g, '') })}
              placeholder="123"
            />
          </div>
        </div>
      </div>
    </div>
  );
};