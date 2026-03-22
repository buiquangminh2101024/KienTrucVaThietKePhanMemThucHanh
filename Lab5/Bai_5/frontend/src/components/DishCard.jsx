import React from 'react'

export default function DishCard({ dish, onAdd }) {
  return (
    <article className="dish-card">
      <div className="dish-image" aria-hidden="true">{dish.name.slice(0, 2)}</div>
      <div className="dish-body">
        <p className="dish-badge">Hot</p>
        <h3>{dish.name}</h3>
        <p>{dish.description || 'Món ăn hấp dẫn từ nhà hàng'}.</p>
        <div className="dish-meta">
          <strong>{dish.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</strong>
          <span>{dish.available_quantity || dish.quantity || 0} Available</span>
        </div>
        <button onClick={() => onAdd(dish)}>Add</button>
      </div>
    </article>
  )
}
