import { useEffect, useState } from 'react'
import { getRestaurants } from '../services/api'

export function useRestaurants() {
  const [restaurants, setRestaurants] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    setLoading(true)
    getRestaurants()
      .then((data) => setRestaurants(data))
      .catch((err) => setError(err.message || 'Lỗi tải dữ liệu'))
      .finally(() => setLoading(false))
  }, [])

  return { restaurants, loading, error }
}
