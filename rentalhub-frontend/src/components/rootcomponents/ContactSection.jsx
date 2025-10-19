const ContactSection = () => (
    <section className="py-12 md:py-16 lg:py-20 bg-[#1e293b] text-white">
      <div className="container mx-auto px-4 sm:px-6 lg:px-8 max-w-6xl">
        {/* Header Section */}
        <div className="text-center mb-12 md:mb-16">
          <h2 className="text-2xl sm:text-3xl lg:text-4xl font-bold mb-4 bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
            Get In Touch
          </h2>
          <p className="text-gray-300 text-base sm:text-lg lg:text-xl max-w-2xl mx-auto leading-relaxed">
            Ready to get started? Reach out to our team and we'll get back to you within 24 hours.
          </p>
        </div>
  
        {/* Contact Form */}
        <div className="max-w-lg mx-auto">
          <form className="bg-[#0f172a] p-6 sm:p-8 rounded-xl shadow-2xl space-y-6 border border-gray-700/50">
            {/* Name Input */}
            <div className="space-y-2">
              <label htmlFor="name" className="block text-sm font-medium text-gray-300 text-left">
                Full Name
              </label>
              <input
                id="name"
                type="text"
                className="w-full p-3 bg-[#1e293b] rounded-lg text-white border border-gray-600 focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20 transition-all duration-200 outline-none placeholder-gray-400"
                placeholder="Enter your full name"
                required
              />
            </div>
  
            {/* Email Input */}
            <div className="space-y-2">
              <label htmlFor="email" className="block text-sm font-medium text-gray-300 text-left">
                Email Address
              </label>
              <input
                id="email"
                type="email"
                className="w-full p-3 bg-[#1e293b] rounded-lg text-white border border-gray-600 focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20 transition-all duration-200 outline-none placeholder-gray-400"
                placeholder="Enter your email address"
                required
              />
            </div>
  
            {/* Message Textarea */}
            <div className="space-y-2">
              <label htmlFor="message" className="block text-sm font-medium text-gray-300 text-left">
                Message
              </label>
              <textarea
                id="message"
                className="w-full p-3 bg-[#1e293b] rounded-lg text-white border border-gray-600 focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20 transition-all duration-200 outline-none placeholder-gray-400 resize-none"
                placeholder="Tell us about your project or inquiry..."
                rows={5}
                required
              />
            </div>
  
            {/* Submit Button */}
            <button
              type="submit"
              className="w-full bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white font-semibold py-3 px-6 rounded-lg transition-all duration-200 transform hover:scale-[1.02] focus:scale-[1.02] focus:outline-none focus:ring-2 focus:ring-blue-500/50 shadow-lg hover:shadow-xl active:scale-100"
            >
              Send Message
            </button>
          </form>
  
          {/* Additional Contact Info */}
          <div className="mt-8 text-center">
            <p className="text-gray-400 text-sm mb-4">Or reach us directly at</p>
            <div className="flex flex-col sm:flex-row justify-center items-center gap-4 sm:gap-8 text-gray-300">
              <a href="mailto:hello@example.com" className="flex items-center gap-2 hover:text-blue-400 transition-colors">
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                </svg>
                hello@example.com
              </a>
              <a href="tel:+1234567890" className="flex items-center gap-2 hover:text-blue-400 transition-colors">
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                </svg>
                +1 (234) 567-890
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
  
  export default ContactSection;