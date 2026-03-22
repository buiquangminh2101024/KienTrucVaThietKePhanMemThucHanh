import React from 'react'

export default function CartSidebar({ cart, onQty, onRemove, onPay, onClear, checkout, setCheckout, totalPrice, customerName, setCustomerName, paymentMethod, setPaymentMethod, isProcessing }) {
  return (
    <aside className="cart-panel">
      <header>
        <h2>Orders #{Math.floor(1000 + Math.random() * 9000)}</h2>
        <p>{cart.length} item(s)</p>
      </header>
      <div className="order-items">
        {cart.length === 0 && <p className="empty">Chưa có món nào trong giỏ hàng</p>}
        {cart.map(({ dish, qty }) => (
          <div key={dish.id} className="order-item">
            <div>
              <strong>{dish.name}</strong>
              <small>{qty} × {dish.price.toLocaleString('vi-VN')} đ</small>
            </div>
            <div className="qty-actions">
              <button onClick={() => onQty(dish.id, qty - 1)}>-</button>
              <span>{qty}</span>
              <button onClick={() => onQty(dish.id, qty + 1)}>+</button>
            </div>
            <button className="remove" onClick={() => onRemove(dish.id)}>&times;</button>
          </div>
        ))}
      </div>

      <div className="summary">
        <p>Subtotal</p>
        <strong>{totalPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</strong>
      </div>

      <button className="primary" style={{width: "fit-content"}} onClick={() => setCheckout((v) => !v)} disabled={cart.length === 0}>
        {checkout ? 'Ẩn form thanh toán' : 'Tiếp tục thanh toán'}
      </button>

      {checkout && (
        <form className="payment-form" onSubmit={(e) => { e.preventDefault(); onPay() }}>
          <label>
            Tên KH
            <input value={customerName} onChange={(e) => setCustomerName(e.target.value)} required placeholder="Nhập tên" />
          </label>
          <label>
            Phương thức
            <select value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)}>
              <option value="CREDIT_CARD">Credit Card</option>
              <option value="PAYPAL">Paypal</option>
              <option value="CASH">Cash</option>
            </select>
          </label>
          <button className="primary" style={{width: "fit-content"}} type="submit" disabled={isProcessing}>{isProcessing ? 'Đang xử lý...' : 'Xác nhận thanh toán'}</button>
          <button type="button" className="secondary" onClick={onClear}>Huỷ đơn</button>
        </form>
      )}
    </aside>
  )
}
