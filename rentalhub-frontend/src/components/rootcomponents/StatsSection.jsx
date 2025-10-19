const StatsSection = () => {
    const stats = [
      { 
        number: '10K+', 
        label: 'Properties Managed',
        icon: '🏠',
        suffix: '',
        color: 'from-green-500 to-cyan-500'
      },
      { 
        number: '500+', 
        label: 'Happy Clients',
        icon: '😊',
        suffix: '',
        color: 'from-blue-500 to-purple-500'
      },
      { 
        number: '99.9', 
        label: 'Uptime Reliability',
        icon: '⚡',
        suffix: '%',
        color: 'from-orange-500 to-red-500'
      },
      { 
        number: '2B+', 
        label: 'Revenue Tracked',
        icon: '💰',
        suffix: ' Rwf',
        color: 'from-purple-500 to-pink-500'
      },
    ]
  
    return (
      <section className="py-12 md:py-16 lg:py-20 bg-gradient-to-br from-[#1e293b] to-[#0f172a] text-white overflow-hidden">
        {/* Background Elements */}
        <div className="absolute inset-0 overflow-hidden">
          <div className="absolute -top-20 -right-20 w-40 h-40 bg-blue-500/5 rounded-full blur-3xl"></div>
          <div className="absolute -bottom-20 -left-20 w-40 h-40 bg-purple-500/5 rounded-full blur-3xl"></div>
        </div>
  
        <div className="container mx-auto px-4 sm:px-6 lg:px-8 max-w-6xl relative z-10">
          {/* Header Section */}
          <div className="text-center mb-12 md:mb-16 lg:mb-20">
            <h2 className="text-2xl sm:text-3xl lg:text-4xl font-bold mb-4 bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
              Trusted by Industry Leaders
            </h2>
            <p className="text-gray-300 text-base sm:text-lg lg:text-xl max-w-2xl mx-auto leading-relaxed">
              Join thousands of property managers who trust our platform to streamline their operations and drive growth
            </p>
          </div>
  
          {/* Stats Grid */}
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 md:gap-8">
            {stats.map((stat, index) => (
              <div 
                key={index}
                className="group p-6 md:p-8 bg-[#0f172a]/60 backdrop-blur-sm rounded-2xl border border-gray-700/50 hover:border-gray-600/70 transition-all duration-500 hover:scale-105 hover:shadow-2xl text-center relative overflow-hidden"
              >
                {/* Animated Background Gradient */}
                <div className={`absolute inset-0 bg-gradient-to-br ${stat.color} opacity-0 group-hover:opacity-5 transition-opacity duration-500`}></div>
                
                {/* Icon */}
                <div className="w-16 h-16 md:w-20 md:h-20 mx-auto mb-4 md:mb-6 rounded-2xl bg-gray-800/50 flex items-center justify-center group-hover:scale-110 transition-transform duration-300 border border-gray-700/50">
                  <span className="text-2xl md:text-3xl">{stat.icon}</span>
                </div>
  
                {/* Number with Counting Animation */}
                <div className="mb-2">
                  <h3 className="text-3xl sm:text-4xl lg:text-5xl font-bold bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
                    {stat.number}<span className="text-sm lg:text-base align-super">{stat.suffix}</span>
                  </h3>
                </div>
  
                {/* Label */}
                <p className="text-gray-300 text-sm md:text-base font-medium group-hover:text-white transition-colors duration-300">
                  {stat.label}
                </p>
  
                {/* Animated Border Bottom */}
                <div className={`absolute bottom-0 left-1/2 transform -translate-x-1/2 w-0 h-1 bg-gradient-to-r ${stat.color} group-hover:w-3/4 transition-all duration-500 rounded-full`}></div>
              </div>
            ))}
          </div>
  
          {/* Bottom CTA */}
          <div className="text-center mt-12 md:mt-16">
            <div className="inline-flex flex-col sm:flex-row items-center gap-4 md:gap-6 bg-[#0f172a]/40 backdrop-blur-sm rounded-2xl p-6 md:p-8 border border-gray-700/30">
              <div className="text-left">
                <h4 className="text-lg md:text-xl font-semibold text-white mb-2">
                  Ready to join them?
                </h4>
                <p className="text-gray-300 text-sm md:text-base">
                  Start managing your properties more efficiently today
                </p>
              </div>
              <button className="bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white font-semibold px-6 md:px-8 py-3 rounded-lg transition-all duration-200 transform hover:scale-105 focus:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500/50 shadow-lg hover:shadow-xl whitespace-nowrap">
                Get Started Now
              </button>
            </div>
          </div>
        </div>
      </section>
    )
  }
  
  export default StatsSection