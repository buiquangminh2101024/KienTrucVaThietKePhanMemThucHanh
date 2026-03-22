import { useMemo, useState } from 'react'

export function useCart() {
  const [items, setItems] = useState([])

  const addToCart = (dish) => {
    setItems((prev) => {
      const exist = prev.find((x) => x.dish.id === dish.id)
      if (exist) {
        return prev.map((x) => (x.dish.id === dish.id ? { ...x, qty: x.qty + 1 } : x))
      }
      return [...prev, { dish, qty: 1 }]
    })
  }

  const updateQty = (dishId, qty) => {
    if (qty <= 0) {
      return removeFromCart(dishId)
    }
    setItems((prev) => prev.map((x) => (x.dish.id === dishId ? { ...x, qty } : x)))
  }

  const removeFromCart = (dishId) => {
    setItems((prev) => prev.filter((x) => x.dish.id !== dishId))
  }

  const clearCart = () => setItems([])

  const totalQty = useMemo(() => items.reduce((sum, x) => sum + x.qty, 0), [items])
  const totalPrice = useMemo(() => items.reduce((sum, x) => sum + x.qty * x.dish.price, 0), [items])

  return { items, addToCart, updateQty, removeFromCart, clearCart, totalQty, totalPrice }
}
