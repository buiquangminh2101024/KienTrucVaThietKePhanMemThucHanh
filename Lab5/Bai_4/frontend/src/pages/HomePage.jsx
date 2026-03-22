import { useMemo, useState } from 'react'
import Sidebar from '../components/Sidebar'
import DishCard from '../components/DishCard'
import CartSidebar from '../components/CartSidebar'
import { useRestaurants } from '../hooks/useRestaurants'
import { useCart } from '../hooks/useCart'
import { createOrder, processPayment } from '../services/api'

export default function HomePage() {
  const [selectedCategory, setSelectedCategory] = useState('Home')
  const { restaurants, loading, error } = useRestaurants()
  const { items, addToCart, updateQty, removeFromCart, clearCart, totalPrice } = useCart()

  const [checkout, setCheckout] = useState(false)
  const [customerName, setCustomerName] = useState('')
  const [paymentMethod, setPaymentMethod] = useState('CREDIT_CARD')
  const [status, setStatus] = useState('')
  const [isProcessing, setIsProcessing] = useState(false)

  const filteredDishes = useMemo(() => {
    const all = restaurants.flatMap((r) => r.dishes.map((dish) => ({ ...dish, restaurant: r.name })))
    if (selectedCategory !== 'Home' && selectedCategory !== 'Hot Dishes') return all
    return all
  }, [restaurants, selectedCategory])

  const handlePayment = async () => {
    if (!customerName.trim()) return setStatus('Vui lòng nhập tên khách hàng.')
    if (items.length === 0) return setStatus('Giỏ hàng trống.')

    setIsProcessing(true)
    setStatus('')
    try {
      const order = await createOrder(customerName, items.map((x) => ({ dish_id: x.dish.id, quantity: x.qty })))
      await processPayment(order.id, paymentMethod)
      setStatus('Đặt hàng và thanh toán thành công!')
      clearCart()
      setCheckout(false)
      setCustomerName('')
    } catch (err) {
      setStatus(err.message || 'Lỗi hệ thống')
    } finally {
      setIsProcessing(false)
    }
  }

  return (
    <div className="app-layout">
      <Sidebar selected={selectedCategory} onSelect={setSelectedCategory} />
      <main className="main-content">
        <div className="page-header">
          <div>
            <h1>Jaegar Resto</h1>
            <p>Delivery food experience with dark theme</p>
          </div>
          <div className="card-summary">
            <span>{items.length} items</span>
            <strong>{totalPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</strong>
          </div>
        </div>

        <section className="menu-section">
          <h2>Choose Dishes</h2>
          {loading && <p>Loading...</p>}
          {error && <p className="error">{error}</p>}
          <div className="dish-grid">
            {filteredDishes.map((dish) => (
              <DishCard key={dish.id} dish={dish} onAdd={addToCart} />
            ))}
          </div>
        </section>

        <section className="orders-section">
          <h2>Order #{Math.floor(5600 + Math.random() * 4800)}</h2>
          <div className="order-subtitle">⭐ Hot & Fast</div>
        </section>

        {status && <div className="status-bar">{status}</div>}
      </main>

      <CartSidebar
        cart={items}
        onQty={updateQty}
        onRemove={removeFromCart}
        onPay={handlePayment}
        onClear={() => { clearCart(); setCheckout(false); }}
        totalPrice={totalPrice}
        checkout={checkout}
        setCheckout={setCheckout}
        customerName={customerName}
        setCustomerName={setCustomerName}
        paymentMethod={paymentMethod}
        setPaymentMethod={setPaymentMethod}
        isProcessing={isProcessing}
      />
    </div>
  )
}
