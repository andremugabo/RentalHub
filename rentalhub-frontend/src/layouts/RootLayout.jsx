import React from 'react'
import { Outlet } from 'react-router-dom'
import  Navbar  from '../components/rootcomponents/Navbar';
import  Footer  from '../components/rootcomponents/Footer';

export const RootLayout = () => {
  return (
    <div className="min-h-screen bg-[#0f172a]">
      <Navbar />
      <main>
        <Outlet />
      </main>
      <Footer />
    </div>
  )
}
