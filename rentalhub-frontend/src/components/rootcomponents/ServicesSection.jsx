const services = [
    { 
      title: 'Manage Listings Easily', 
      desc: 'Update property listings quickly with details and media.',
      icon: '📋',
      gradient: 'from-green-500 to-cyan-500'
    },
    { 
      title: 'Provide Access Everywhere', 
      desc: 'Secure dashboards for owners and tenants on any device.',
      icon: '🌐',
      gradient: 'from-blue-500 to-purple-500'
    },
    { 
      title: 'Offer Automatic Support', 
      desc: 'Automated billing, reminders, and repair notifications.',
      icon: '🤖',
      gradient: 'from-purple-500 to-pink-500'
    },
    { 
      title: 'Ensure Data Safety', 
      desc: 'Top-tier security with encryption and cloud reliability.',
      icon: '🛡️',
      gradient: 'from-orange-500 to-red-500'
    },
    { 
      title: 'Share Clear Reports', 
      desc: 'Comprehensive dashboards and performance tracking reports.',
      icon: '📊',
      gradient: 'from-indigo-500 to-blue-500'
    },
    { 
      title: 'Streamline Communication', 
      desc: 'Built-in messaging and notification system for all parties.',
      icon: '💬',
      gradient: 'from-teal-500 to-green-500'
    },
  ]
  
  const ServicesSection = () => (
    <section className="py-12 md:py-16 lg:py-20 bg-[#1e293b] text-white">
      <div className="container mx-auto px-4 sm:px-6 lg:px-8 max-w-7xl">
        {/* Header Section */}
        <div className="text-center mb-12 md:mb-16 lg:mb-20">
          <h2 className="text-2xl sm:text-3xl lg:text-4xl xl:text-5xl font-bold mb-4 bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
            Our Services
          </h2>
          <p className="text-gray-300 text-base sm:text-lg lg:text-xl max-w-3xl mx-auto leading-relaxed">
            Everything you need to manage your properties efficiently, securely, and profitably
          </p>
        </div>
  
        {/* Services Grid */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 md:gap-8">
          {services.map((service, index) => (
            <div 
              key={index}
              className="group p-6 md:p-8 bg-[#0f172a] rounded-xl shadow-lg border border-gray-700/50 hover:border-gray-600/50 transition-all duration-300 hover:scale-105 hover:shadow-2xl flex flex-col"
            >
              {/* Icon Container */}
              <div className={`w-14 h-14 md:w-16 md:h-16 mb-4 md:mb-6 rounded-2xl bg-gradient-to-br ${service.gradient} flex items-center justify-center group-hover:scale-110 transition-transform duration-300 shadow-lg`}>
                <span className="text-2xl md:text-3xl">{service.icon}</span>
              </div>
  
              {/* Content */}
              <div className="flex-1">
                <h3 className="text-lg md:text-xl font-semibold mb-3 md:mb-4 text-white group-hover:text-blue-400 transition-colors duration-200">
                  {service.title}
                </h3>
                <p className="text-gray-400 text-sm md:text-base leading-relaxed">
                  {service.desc}
                </p>
              </div>
  
              {/* Hover Arrow */}
              <div className="mt-4 md:mt-6 flex justify-end">
                <div className="w-8 h-8 rounded-full bg-gray-700/50 group-hover:bg-gradient-to-r group-hover:from-blue-500 group-hover:to-purple-600 flex items-center justify-center transition-all duration-300 group-hover:scale-110">
                  <svg 
                    className="w-4 h-4 text-gray-400 group-hover:text-white transform group-hover:translate-x-0.5 transition-all duration-200" 
                    fill="none" 
                    stroke="currentColor" 
                    viewBox="0 0 24 24"
                  >
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14 5l7 7m0 0l-7 7m7-7H3" />
                  </svg>
                </div>
              </div>
            </div>
          ))}
        </div>
  
        {/* CTA Section */}
        <div className="text-center mt-12 md:mt-16 lg:mt-20">
          <div className="bg-gradient-to-r from-blue-500/10 to-purple-500/10 border border-blue-500/20 rounded-2xl p-8 md:p-12">
            <h3 className="text-xl md:text-2xl lg:text-3xl font-bold mb-4 text-white">
              Ready to Transform Your Property Management?
            </h3>
            <p className="text-gray-300 text-base md:text-lg mb-6 md:mb-8 max-w-2xl mx-auto">
              Join thousands of property managers who are already saving time and increasing profits with our platform.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center items-center">
              <button className="bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white font-semibold px-8 py-3 rounded-lg transition-all duration-200 transform hover:scale-105 focus:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500/50 shadow-lg hover:shadow-xl">
                Start Free Trial
              </button>
              <button className="border-2 border-gray-600 hover:border-blue-400 text-white font-semibold px-8 py-3 rounded-lg transition-all duration-200 transform hover:scale-105 focus:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500/50 hover:bg-blue-500/10">
                Schedule Demo
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
  
  export default ServicesSection