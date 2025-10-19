import React, { useState } from 'react';

export const Signup = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    userType: 'propertyManager',
    agreeToTerms: false,
    newsletter: true,
  });
  const [isLoading, setIsLoading] = useState(false);
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));

    // Clear validation error when user types
    if (errors[name]) {
      setErrors((prev) => ({ ...prev, [name]: '' }));
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.firstName.trim()) newErrors.firstName = 'First name is required';
    if (!formData.lastName.trim()) newErrors.lastName = 'Last name is required';
    if (!formData.email.trim()) newErrors.email = 'Email is required';
    else if (!/\S+@\S+\.\S+/.test(formData.email)) newErrors.email = 'Email is invalid';
    if (!formData.password) newErrors.password = 'Password is required';
    else if (formData.password.length < 8)
      newErrors.password = 'Password must be at least 8 characters';
    if (formData.password !== formData.confirmPassword)
      newErrors.confirmPassword = 'Passwords do not match';
    if (!formData.agreeToTerms)
      newErrors.agreeToTerms = 'You must agree to the terms and conditions';

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    setIsLoading(true);

    // Simulate API call
    setTimeout(() => {
      console.log('Signup data:', formData);
      setIsLoading(false);
    }, 2000);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-[#0f172a] to-slate-800 py-12 px-4 sm:px-6 lg:px-8 relative">
      {/* Background Lights */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute top-10 left-10 w-20 h-20 bg-blue-500/10 rounded-full blur-xl animate-pulse"></div>
        <div className="absolute top-20 right-20 w-16 h-16 bg-purple-500/10 rounded-full blur-xl animate-pulse delay-1000"></div>
        <div className="absolute bottom-20 left-20 w-24 h-24 bg-cyan-500/10 rounded-full blur-xl animate-pulse delay-500"></div>
        <div className="absolute bottom-10 right-10 w-12 h-12 bg-indigo-500/10 rounded-full blur-xl animate-pulse delay-1500"></div>
      </div>

      {/* Signup Form */}
      <div className="max-w-md w-full space-y-8 relative z-10">
        <div className="text-center">
          <h1 className="text-3xl sm:text-4xl font-bold bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent mb-2">
            Join RentalHub
          </h1>
          <p className="text-gray-400 text-sm sm:text-base">
            Create your account and start managing properties efficiently
          </p>
        </div>

        <form
          onSubmit={handleSubmit}
          className="mt-8 space-y-6 bg-[#1e293b]/80 backdrop-blur-sm rounded-2xl p-8 border border-gray-700/50 shadow-2xl"
        >
          {/* User Type */}
          <div>
            <label className="block text-sm font-medium text-gray-300 mb-2">
              I am a
            </label>
            <select
              name="userType"
              value={formData.userType}
              onChange={handleChange}
              className="w-full px-4 py-3 bg-[#0f172a] border border-gray-600 rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
              <option value="propertyManager">Property Manager</option>
              <option value="propertyOwner">Property Owner</option>
              <option value="tenant">Tenant</option>
              <option value="realEstateAgent">Real Estate Agent</option>
            </select>
          </div>

          {/* Names */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            {['firstName', 'lastName'].map((field) => (
              <div key={field}>
                <label className="block text-sm font-medium text-gray-300 mb-2">
                  {field === 'firstName' ? 'First Name' : 'Last Name'}
                </label>
                <input
                  name={field}
                  type="text"
                  value={formData[field]}
                  onChange={handleChange}
                  placeholder={field === 'firstName' ? 'First name' : 'Last name'}
                  className={`w-full px-4 py-3 bg-[#0f172a] border ${
                    errors[field] ? 'border-red-500' : 'border-gray-600'
                  } rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
                />
                {errors[field] && (
                  <p className="mt-1 text-sm text-red-400">{errors[field]}</p>
                )}
              </div>
            ))}
          </div>

          {/* Email */}
          <div>
            <label className="block text-sm font-medium text-gray-300 mb-2">
              Email Address
            </label>
            <input
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Enter your email"
              className={`w-full px-4 py-3 bg-[#0f172a] border ${
                errors.email ? 'border-red-500' : 'border-gray-600'
              } rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
            />
            {errors.email && <p className="mt-1 text-sm text-red-400">{errors.email}</p>}
          </div>

          {/* Passwords */}
          {['password', 'confirmPassword'].map((field) => (
            <div key={field}>
              <label className="block text-sm font-medium text-gray-300 mb-2">
                {field === 'password' ? 'Password' : 'Confirm Password'}
              </label>
              <input
                name={field}
                type="password"
                value={formData[field]}
                onChange={handleChange}
                placeholder={
                  field === 'password' ? 'Create a password' : 'Confirm password'
                }
                className={`w-full px-4 py-3 bg-[#0f172a] border ${
                  errors[field] ? 'border-red-500' : 'border-gray-600'
                } rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
              />
              {errors[field] && (
                <p className="mt-1 text-sm text-red-400">{errors[field]}</p>
              )}
              {field === 'password' && (
                <p className="mt-1 text-xs text-gray-400">
                  Must be at least 8 characters
                </p>
              )}
            </div>
          ))}

          {/* Checkboxes */}
          <div className="space-y-3">
            <label className="flex items-start text-sm text-gray-300">
              <input
                type="checkbox"
                name="agreeToTerms"
                checked={formData.agreeToTerms}
                onChange={handleChange}
                className="h-4 w-4 text-blue-500 rounded bg-[#0f172a] mr-2 mt-1"
              />
              I agree to the{' '}
              <a href="#" className="text-blue-400 mx-1 hover:text-blue-300">
                Terms and Conditions
              </a>
              and{' '}
              <a href="#" className="text-blue-400 mx-1 hover:text-blue-300">
                Privacy Policy
              </a>
            </label>
            {errors.agreeToTerms && (
              <p className="text-sm text-red-400">{errors.agreeToTerms}</p>
            )}

            <label className="flex items-start text-sm text-gray-300">
              <input
                type="checkbox"
                name="newsletter"
                checked={formData.newsletter}
                onChange={handleChange}
                className="h-4 w-4 text-blue-500 rounded bg-[#0f172a] mr-2 mt-1"
              />
              Send me product updates and tips
            </label>
          </div>

          {/* Submit */}
          <button
            type="submit"
            disabled={isLoading}
            className="w-full flex justify-center py-3 px-4 rounded-lg text-white bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-all duration-200 transform hover:scale-105 shadow-lg disabled:opacity-50"
          >
            {isLoading ? 'Creating Account...' : 'Create Account'}
          </button>

          {/* Login Link */}
          <p className="text-center text-gray-400 text-sm">
            Already have an account?{' '}
            <a href="/login" className="text-blue-400 hover:text-blue-300 font-medium">
              Sign in
            </a>
          </p>
        </form>
      </div>
    </div>
  );
};
