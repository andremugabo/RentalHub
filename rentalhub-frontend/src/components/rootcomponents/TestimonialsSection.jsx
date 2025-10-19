const testimonials = [
    { 
      name: 'Sarah Johnson', 
      role: 'Property Manager', 
      company: 'Elite Properties',
      text: 'This system has revolutionized how we manage our 200+ properties. The automation and reporting features have increased our efficiency by 40%.',
      rating: 5,
      avatar: '👩‍💼'
    },
    { 
      name: 'Mike Chen', 
      role: 'Real Estate Investor', 
      company: 'Metro Holdings',
      text: 'The automation saves us 20+ hours per week in administrative tasks. Our tenant satisfaction scores have never been higher!',
      rating: 5,
      avatar: '👨‍💼'
    },
    { 
      name: 'Lisa Rodriguez', 
      role: 'Tenant Coordinator', 
      company: 'Urban Living',
      text: 'Maintenance requests are handled efficiently now! The mobile app makes it so easy for tenants to report issues and track progress.',
      rating: 5,
      avatar: '👩‍🔧'
    },
    { 
      name: 'David Thompson', 
      role: 'Property Owner', 
      company: 'Thompson Estates',
      text: 'The financial tracking and reporting features have given me complete visibility into my portfolio. Revenue is up 25% since switching.',
      rating: 5,
      avatar: '👨‍💻'
    },
    { 
      name: 'Emily Watson', 
      role: 'Community Manager', 
      company: 'Skyline Apartments',
      text: 'Our team collaboration has improved dramatically. The shared dashboard keeps everyone on the same page across 15 properties.',
      rating: 5,
      avatar: '👩‍🎓'
    },
    { 
      name: 'James Wilson', 
      role: 'Facilities Director', 
      company: 'Premier Management',
      text: 'The maintenance scheduling and vendor management tools have reduced our response time from 3 days to just 4 hours on average.',
      rating: 5,
      avatar: '👨‍🔧'
    },
  ]
  
  const TestimonialsSection = () => (
    <section className="py-12 md:py-16 lg:py-20 bg-gradient-to-br from-[#0f172a] to-[#1e293b] text-white overflow-hidden">
      {/* Background Elements */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute top-10 left-10 w-32 h-32 bg-blue-500/5 rounded-full blur-3xl"></div>
        <div className="absolute bottom-10 right-10 w-40 h-40 bg-purple-500/5 rounded-full blur-3xl"></div>
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-60 h-60 bg-cyan-500/3 rounded-full blur-3xl"></div>
      </div>
  
      <div className="container mx-auto px-4 sm:px-6 lg:px-8 max-w-7xl relative z-10">
        {/* Header Section */}
        <div className="text-center mb-12 md:mb-16 lg:mb-20">
          <h2 className="text-2xl sm:text-3xl lg:text-4xl xl:text-5xl font-bold mb-4 bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
            Loved by Property Managers Worldwide
          </h2>
          <p className="text-gray-300 text-base sm:text-lg lg:text-xl max-w-3xl mx-auto leading-relaxed">
            Join thousands of satisfied customers who have transformed their property management operations
          </p>
          
          {/* Overall Rating */}
          <div className="flex items-center justify-center gap-4 mt-6">
            <div className="flex items-center gap-1">
              {[...Array(5)].map((_, i) => (
                <svg key={i} className="w-5 h-5 text-yellow-400" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
                </svg>
              ))}
            </div>
            <div className="text-gray-300 text-sm md:text-base">
              <span className="font-semibold text-white">4.9/5</span> average rating from 2,000+ reviews
            </div>
          </div>
        </div>
  
        {/* Testimonials Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 md:gap-8">
          {testimonials.map((testimonial, index) => (
            <div 
              key={index}
              className="group p-6 md:p-8 bg-[#1e293b]/80 backdrop-blur-sm rounded-2xl border border-gray-700/50 hover:border-blue-500/30 transition-all duration-500 hover:scale-105 hover:shadow-2xl flex flex-col"
            >
              {/* Rating Stars */}
              <div className="flex items-center gap-1 mb-4">
                {[...Array(testimonial.rating)].map((_, i) => (
                  <svg key={i} className="w-4 h-4 text-yellow-400" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
                  </svg>
                ))}
              </div>
  
              {/* Testimonial Text */}
              <div className="flex-1 mb-6">
                <p className="text-gray-300 text-sm md:text-base leading-relaxed italic">
                  "{testimonial.text}"
                </p>
              </div>
  
              {/* Author Info */}
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 rounded-full bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center text-lg">
                  {testimonial.avatar}
                </div>
                <div className="flex-1 min-w-0">
                  <h4 className="font-semibold text-white text-sm md:text-base truncate">
                    {testimonial.name}
                  </h4>
                  <p className="text-gray-400 text-xs md:text-sm truncate">
                    {testimonial.role}
                  </p>
                  <p className="text-blue-400 text-xs truncate">
                    {testimonial.company}
                  </p>
                </div>
              </div>
  
              {/* Hover Quote Mark */}
              <div className="absolute top-4 right-4 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                <svg className="w-8 h-8 text-blue-500/20" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M4.583 17.321C3.553 16.227 3 15 3 13.011c0-3.5 2.457-6.637 6.03-8.188l.893 1.378c-3.335 1.804-3.987 4.145-4.247 5.621.537-.278 1.24-.375 1.929-.311 1.804.167 3.226 1.648 3.226 3.489a3.5 3.5 0 01-3.5 3.5c-1.073 0-2.099-.49-2.748-1.179zm10 0C13.553 16.227 13 15 13 13.011c0-3.5 2.457-6.637 6.03-8.188l.893 1.378c-3.335 1.804-3.987 4.145-4.247 5.621.537-.278 1.24-.375 1.929-.311 1.804.167 3.226 1.648 3.226 3.489a3.5 3.5 0 01-3.5 3.5c-1.073 0-2.099-.49-2.748-1.179z"/>
                </svg>
              </div>
            </div>
          ))}
        </div>
  
        {/* Trust Indicators */}
        <div className="text-center mt-12 md:mt-16">
          <p className="text-gray-400 text-sm md:text-base mb-6">
            Trusted by leading companies in the industry
          </p>
          <div className="flex flex-wrap justify-center items-center gap-8 md:gap-12 opacity-60">
            {['🏢', '🏘️', '🏬', '🏨', '🏪', '🏛️'].map((icon, index) => (
              <div key={index} className="text-2xl md:text-3xl hover:scale-110 transition-transform duration-200">
                {icon}
              </div>
            ))}
          </div>
        </div>
      </div>
    </section>
  )
  
  export default TestimonialsSection