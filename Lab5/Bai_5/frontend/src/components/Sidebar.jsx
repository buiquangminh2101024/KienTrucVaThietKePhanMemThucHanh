import React from 'react'

const items = [
  { label: 'Home', icon: '🏠' },
  { label: 'Hot Dishes', icon: '🔥' },
  { label: 'Salads', icon: '🥗' },
  { label: 'Drinks', icon: '🥤' },
  { label: 'Dessert', icon: '🍰' },
]

export default function Sidebar({ selected, onSelect }) {
  return (
    <aside className="sidebar" aria-label="navigation">
      <div className="sidebar-brand">
        <span>Jaegar Resto</span>
      </div>
      <nav className="sidebar-nav">
        {items.map((item) => (
          <button
            key={item.label}
            onClick={() => onSelect(item.label)}
            className={selected === item.label ? 'active' : ''}
          >
            <span>{item.icon}</span>
            {item.label}
          </button>
        ))}
      </nav>
    </aside>
  )
}
