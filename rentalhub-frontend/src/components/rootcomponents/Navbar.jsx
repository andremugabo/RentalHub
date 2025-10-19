import { useState } from 'react';

const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const navItems = [
    { name: 'Home', href: '#home' },
    { name: 'About Us', href: '#about' },
    { name: 'Services', href: '#services' },
    { name: 'Contact Us', href: '#contact' },
  ];

  return (
    <>
      <nav className="sticky top-0 z-50 bg-[#1e293b]/95 backdrop-blur-md border-b border-gray-700/50 shadow-xl">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16 lg:h-20">
            {/* Logo */}
            <div className="flex-shrink-0 flex items-center">
              <h1 className="text-2xl lg:text-3xl font-bold bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
                RentalHub
              </h1>
            </div>

            {/* Desktop Navigation */}
            <div className="hidden lg:flex lg:items-center lg:space-x-8">
              <ul className="flex space-x-8">
                {navItems.map((item) => (
                  <li key={item.name}>
                    <a
                      href={item.href}
                      className="text-gray-200 hover:text-blue-400 font-medium transition-all duration-200 relative group"
                    >
                      {item.name}
                      <span className="absolute -bottom-1 left-0 w-0 h-0.5 bg-blue-400 transition-all duration-200 group-hover:w-full"></span>
                    </a>
                  </li>
                ))}
              </ul>
            </div>

            {/* Desktop Buttons */}
            <div className="hidden lg:flex lg:items-center lg:space-x-4">
              <button className="text-gray-200 hover:text-blue-400 border border-gray-600 hover:border-blue-400 px-6 py-2.5 rounded-lg font-medium transition-all duration-200 hover:scale-105">
                Sign In
              </button>
              <button className="bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white px-6 py-2.5 rounded-lg font-medium transition-all duration-200 transform hover:scale-105 shadow-lg hover:shadow-blue-500/25">
                Get Started
              </button>
            </div>

            {/* Mobile menu button */}
            <div className="lg:hidden">
              <button
                onClick={() => setIsMenuOpen(!isMenuOpen)}
                className="inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700/50 focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all duration-200"
                aria-expanded="false"
              >
                <span className="sr-only">Open main menu</span>
                {/* Hamburger icon */}
                <div className="w-6 h-6 flex flex-col justify-center items-center">
                  <span 
                    className={`block h-0.5 w-6 bg-current transform transition duration-200 ${isMenuOpen ? 'rotate-45 translate-y-0.5' : '-translate-y-1'}`}
                  ></span>
                  <span 
                    className={`block h-0.5 w-6 bg-current transition duration-200 ${isMenuOpen ? 'opacity-0' : 'opacity-100'}`}
                  ></span>
                  <span 
                    className={`block h-0.5 w-6 bg-current transform transition duration-200 ${isMenuOpen ? '-rotate-45 -translate-y-0.5' : 'translate-y-1'}`}
                  ></span>
                </div>
              </button>
            </div>
          </div>
        </div>

        {/* Mobile Navigation Menu */}
        <div className={`lg:hidden transition-all duration-300 ease-in-out overflow-hidden ${isMenuOpen ? 'max-h-96 opacity-100' : 'max-h-0 opacity-0'}`}>
          <div className="px-4 pt-2 pb-6 space-y-4 bg-[#1e293b]/95 border-t border-gray-700/50 backdrop-blur-md">
            <ul className="space-y-3">
              {navItems.map((item) => (
                <li key={item.name}>
                  <a
                    href={item.href}
                    onClick={() => setIsMenuOpen(false)}
                    className="block px-4 py-3 text-gray-200 hover:text-blue-400 hover:bg-gray-700/30 rounded-lg font-medium transition-all duration-200"
                  >
                    {item.name}
                  </a>
                </li>
              ))}
            </ul>
            <div className="pt-4 space-y-3 border-t border-gray-700/50">
              <button 
                onClick={() => setIsMenuOpen(false)}
                className="w-full text-gray-200 hover:text-blue-400 border border-gray-600 hover:border-blue-400 px-6 py-3 rounded-lg font-medium transition-all duration-200"
              >
                Sign In
              </button>
              <button 
                onClick={() => setIsMenuOpen(false)}
                className="w-full bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white px-6 py-3 rounded-lg font-medium transition-all duration-200 transform hover:scale-105"
              >
                Get Started
              </button>
            </div>
          </div>
        </div>
      </nav>

      {/* Overlay for mobile menu */}
      {isMenuOpen && (
        <div 
          className="fixed inset-0 bg-black/50 z-40 lg:hidden"
          onClick={() => setIsMenuOpen(false)}
        ></div>
      )}
    </>
  );
};

export default Navbar;