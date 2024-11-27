import React from 'react';

export const AddressForm = ({ cep, setCep, number, setNumber, addressInfo, onCepBlur }) => {
  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-lg font-medium mb-4">Endereço de Entrega</h2>
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">CEP</label>
          <input
            type="text"
            maxLength="8"
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-amber-500 focus:ring-amber-500"
            value={cep}
            onChange={(e) => setCep(e.target.value.replace(/\D/g, ''))}
            onBlur={onCepBlur}
            placeholder="00000000"
          />
        </div>

        {addressInfo && (
          <>
            <div>
              <label className="block text-sm font-medium text-gray-700">Rua</label>
              <input
                type="text"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm bg-gray-50"
                value={addressInfo.logradouro}
                disabled
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Número</label>
              <input
                type="text"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-amber-500 focus:ring-amber-500"
                value={number}
                onChange={(e) => setNumber(e.target.value.replace(/\D/g, ''))}
                placeholder="123"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Bairro</label>
              <input
                type="text"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm bg-gray-50"
                value={addressInfo.bairro}
                disabled
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Cidade/UF</label>
              <input
                type="text"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm bg-gray-50"
                value={`${addressInfo.localidade}/${addressInfo.uf}`}
                disabled
              />
            </div>
          </>
        )}
      </div>
    </div>
  );
};