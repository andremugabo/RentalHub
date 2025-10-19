const features = [
    {
      title: 'Easy Property Management',
      desc: 'Add listings with photos, videos, and updates in just a few clicks.',
      icon: '🏠',
    },
    {
      title: 'Smooth Experience',
      desc: 'Access everything on your phone, tablet, or computer with secure login.',
      icon: '💻',
    },
    {
      title: 'Smart Automation',
      desc: 'Get automatic bills, reminders, and AI-powered repair suggestions.',
      icon: '⚙️',
    },
    {
      title: 'Top-Notch Security',
      desc: 'Your data is safe with cloud-based encryption and backups.',
      icon: '🔒',
    },
  ]
  
  const FeaturesSection = () => (
    <section className="py-12 md:py-16 lg:py-20 bg-[#0f172a] text-white">
      <div className="container mx-auto px-4 sm:px-6 lg:px-8 max-w-7xl">
        {/* Header Section */}
        <div className="text-center mb-12 md:mb-16 lg:mb-20">
          <h2 className="text-2xl sm:text-3xl lg:text-4xl xl:text-5xl font-bold mb-4 bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
            What You Get
          </h2>
          <p className="text-gray-300 text-base sm:text-lg lg:text-xl max-w-3xl mx-auto leading-relaxed">
            Everything you need to manage your properties efficiently and securely
          </p>
        </div>
  
        {/* Features Grid */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 md:gap-8">
          {features.map((feature, index) => (
            <div 
              key={index}
              className="group p-6 md:p-8 bg-[#1e293b] rounded-xl shadow-lg border border-gray-700/50 hover:border-blue-500/30 transition-all duration-300 hover:scale-105 hover:shadow-2xl flex flex-col items-center text-center"
            >
              {/* Icon Container */}
              <div className="w-16 h-16 md:w-20 md:h-20 mb-4 md:mb-6 rounded-2xl bg-gradient-to-br from-blue-500/10 to-purple-500/10 flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                <span className="text-2xl md:text-3xl">{feature.icon}</span>
              </div>
  
              {/* Content */}
              <div className="flex-1">
                <h3 className="text-lg md:text-xl font-semibold mb-3 md:mb-4 text-white group-hover:text-blue-400 transition-colors duration-200">
                  {feature.title}
                </h3>
                <p className="text-gray-400 text-sm md:text-base leading-relaxed">
                  {feature.desc}
                </p>
              </div>
  
              {/* Hover Indicator */}
              <div className="w-0 group-hover:w-8 h-0.5 bg-gradient-to-r from-blue-500 to-purple-500 mt-4 md:mt-6 transition-all duration-300 rounded-full" />
            </div>
          ))}
        </div>
  
        {/* Optional CTA Section */}
        <div className="text-center mt-12 md:mt-16 lg:mt-20">
          <div className="inline-flex flex-col sm:flex-row gap-4 md:gap-6 items-center bg-[#1e293b] rounded-2xl p-6 md:p-8 border border-gray-700/50 shadow-lg">
            <div className="text-left">
              <h3 className="text-xl md:text-2xl font-bold mb-2 text-white">
                Ready to get started?
              </h3>
              <p className="text-gray-300 text-sm md:text-base">
                Join thousands of satisfied property managers
              </p>
            </div>
            <button className="bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white font-semibold py-3 px-6 md:px-8 rounded-lg transition-all duration-200 transform hover:scale-105 focus:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500/50 shadow-lg hover:shadow-xl whitespace-nowrap">
              Get Started Today
            </button>
          </div>
        </div>
      </div>
    </section>
  )
  
  export default FeaturesSection