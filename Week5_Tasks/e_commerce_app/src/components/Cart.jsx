
import React from 'react';
import { useCart } from '../context/CartContext';

const Cart = () => {
  const { cart, removeFromCart } = useCart();

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">Your Cart</h2>
      {cart.length === 0 ? (
        <p>Your cart is empty</p>
      ) : (
        <ul>
          {cart.map((item) => (
            <li key={item.cartItemId} className="border-b p-2 flex justify-between items-center">
              <div>
                <p className="font-semibold">{item.name}</p>
                <p className="text-gray-600">${item.price}</p>
              </div>
              <button 
                onClick={() => removeFromCart(item.cartItemId)} 
                className="bg-red-500 text-white px-2 py-1 rounded">
                Remove
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Cart;
