import React from 'react';
import { Trash2 } from 'lucide-react';

export const CartItemList = ({ items, onQuantityChange }) => {
  return (
    <div className="md:col-span-2">
      <ul className="divide-y divide-amber-200">
        {items?.map((item) => (
          <li key={item.produto.id} className="py-6 flex">
            <div className="flex-shrink-0 w-24 h-24 border border-amber-200 rounded-md overflow-hidden">
              <img
                src={item.produto.imagem || 'https://via.placeholder.com/100'}
                alt={item.produto.nome}
                className="w-full h-full object-center object-cover"
              />
            </div>

            <div className="ml-4 flex-1 flex flex-col">
              <div className="flex justify-between text-base font-medium text-black">
                <h3>{item.produto.nome}</h3>
                <p className="ml-4">R$ {item.valorUnitario.toFixed(2)}</p>
              </div>
              <div className="flex-1 flex items-end justify-between text-sm">
                <div className="flex items-center border border-amber-400 rounded">
                  <button 
                    className="px-2 py-1 hover:bg-amber-100 text-black disabled:opacity-50"
                    onClick={() => onQuantityChange(item.produto.id, item.quantidade - 1, item.valorUnitario)}
                    disabled={item.quantidade <= 1}
                  >
                    -
                  </button>
                  <span className="px-4 text-black">{item.quantidade}</span>
                  <button 
                    className="px-2 py-1 hover:bg-amber-100 text-black"
                    onClick={() => onQuantityChange(item.produto.id, item.quantidade + 1, item.valorUnitario)}
                  >
                    +
                  </button>
                </div>
                <button
                  className="text-red-500 hover:text-red-700"
                  onClick={() => onQuantityChange(item.produto.id, 0, item.valorUnitario)}
                >
                  <Trash2 className="h-5 w-5" />
                </button>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};