import React from 'react'
import Navbar from '../../components/rootcomponents/Navbar'
import HeroSection from '../../components/rootcomponents/HeroSection'
import StatsSection from '../../components/rootcomponents/StatsSection'
import FeaturesSection from '../../components/rootcomponents/FeaturesSection'
import ServicesSection from '../../components/rootcomponents/ServicesSection'
import TestimonialsSection from '../../components/rootcomponents/TestimonialsSection'
import ContactSection from '../../components/rootcomponents/ContactSection'
import Footer from '../../components/rootcomponents/Footer'

export const HomePage = () => {
  return (
    <div className="bg-[#0f172a] text-white font-sans">
    <HeroSection />
    <StatsSection />
    <FeaturesSection />
    <ServicesSection />
    <TestimonialsSection />
    <ContactSection />
  </div>
  )
}
