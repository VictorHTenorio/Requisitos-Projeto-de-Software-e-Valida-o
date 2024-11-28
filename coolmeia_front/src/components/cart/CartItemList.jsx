import React from 'react';
import { Trash2 } from 'lucide-react';

export const CartItemList = ({ items, onQuantityChange, onRemoveItem, onRemoveCoupon }) => (
  <div className="md:col-span-2">
    <ul className="divide-y divide-amber-200">
      {[...items].sort((a, b) => items.indexOf(a) - items.indexOf(b)).map((item) => (
        <li key={item.produto.id.id} className="py-6 flex">
          <div className="flex-shrink-0 w-24 h-24 border border-amber-200 rounded-md overflow-hidden">
            <img
              src={item.produto.imagem || 'https://via.placeholder.com/100'}
              alt={item.produto.nome}
              className="w-full h-full object-center object-cover"
            />
          </div>

          <div className="ml-4 flex-1 flex flex-col">
            <div>
              <div className="flex justify-between text-base font-medium text-black">
                <div>
                  <h3>{item.produto.nome}</h3>
                  <p className="mt-1 text-sm text-gray-500">{item.produto.descricao}</p>
                  {item.cupomCodigo && (
                    <div className="mt-1 flex items-center gap-2">
                      <span className="inline-flex items-center px-2 py-1 rounded-md text-xs font-medium bg-green-100 text-green-800">
                        Cupom {item.cupomCodigo.id}
                        <button 
                          onClick={() => onRemoveCoupon(item.produto.id.id)}
                          className="ml-2 text-red-500 hover:text-red-700"
                        >
                          ×
                        </button>
                      </span>
                    </div>
                  )}
                </div>
                <div className="text-right">
                  {item.cupomCodigo ? (
                    <>
                      <p className="text-sm line-through text-gray-500">
                        R$ {(item.produto.valor * item.quantidade).toFixed(2)}
                      </p>
                      <p className="text-green-600">
                        R$ {(item.valorUnitario * item.quantidade).toFixed(2)}
                      </p>
                    </>
                  ) : (
                    <p>R$ {(item.valorUnitario * item.quantidade).toFixed(2)}</p>
                  )}
                </div>
              </div>
            </div>
            <div className="flex-1 flex items-end justify-between text-sm">
              <div className="flex flex-col">
                <div className="flex items-center border border-amber-400 rounded">
                  <button 
                    className="px-2 py-1 hover:bg-amber-100 text-black disabled:opacity-50"
                    onClick={() => onQuantityChange(item.produto.id.id, item.quantidade - 1)}
                    disabled={item.quantidade <= 1}
                  >
                    -
                  </button>
                  <span className="px-4 text-black">{item.quantidade}</span>
                  <button 
                    className="px-2 py-1 hover:bg-amber-100 text-black disabled:opacity-50"
                    onClick={() => onQuantityChange(item.produto.id.id, item.quantidade + 1)}
                    disabled={item.quantidade >= item.produto.quantidade}
                  >
                    +
                  </button>
                </div>
                {item.quantidade >= item.produto.quantidade && (
                  <span className="text-xs text-red-600 mt-1">
                    Limite de estoque
                  </span>
                )}
              </div>
              <button
                className="text-red-500 hover:text-red-700"
                onClick={() => onRemoveItem(item.produto.id.id)}
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