const API_BASE = import.meta.env.VITE_API_URL || ''

export async function getRestaurants() {
  const res = await fetch(`${API_BASE}/api/v1/restaurants`)
  if (!res.ok) throw new Error('Không thể tải menu')
  return res.json()
}

export async function createOrder(customerName, items) {
  const res = await fetch(`${API_BASE}/api/v1/orders`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ customer_name: customerName, items }),
  })
  if (!res.ok) {
    const err = await res.json().catch(() => ({}))
    throw new Error(err.error || 'Tạo đơn hàng thất bại')
  }
  return res.json()
}

export async function processPayment(orderId, method) {
  const res = await fetch(`${API_BASE}/api/v1/payments`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ order_id: orderId, payment_method: method, transaction_id: `${orderId}-${Date.now()}`, status: 'SUCCESS' }),
  })
  if (!res.ok) {
    const err = await res.json().catch(() => ({}))
    throw new Error(err.error || 'Thanh toán thất bại')
  }
  return res.json()
}
