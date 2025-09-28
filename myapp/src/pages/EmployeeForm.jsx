import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { motion } from 'framer-motion';
import { User, Mail, Briefcase, Save, RefreshCw } from 'lucide-react';
import { createEmployee, updateEmployee } from '../redux/employeeSlice';

const EmployeeForm = ({ currentEmployee, setCurrentEmployee }) => {
  const [form, setForm] = useState({ name: '', email: '', position: '' });
  const [errors, setErrors] = useState({});
  const dispatch = useDispatch();

  useEffect(() => {
    if (currentEmployee) {
      setForm(currentEmployee);
      setErrors({});
    }
  }, [currentEmployee]);

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
    setErrors({
      ...errors,
      [e.target.name]: '', // Clear error on change
    });
  };

  const validate = () => {
    const newErrors = {};
    if (!form.name || form.name.trim().length < 2) {
      newErrors.name = 'Name must be at least 2 characters.';
    }
    if (!form.email) {
      newErrors.email = 'Email is required.';
    } else {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(form.email)) {
        newErrors.email = 'Invalid email address.';
      }
    }
    if (!form.position || form.position.trim() === '') {
      newErrors.position = 'Position is required.';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validate()) return;

    if (form.id) {
      dispatch(updateEmployee({ id: form.id, data: form }));
    } else {
      dispatch(createEmployee(form));
    }
    setForm({ name: '', email: '', position: '' });
    setCurrentEmployee(null);
  };

  return (
    <motion.div
      className="max-w-md mx-auto mb-8"
      initial={{ opacity: 0, y: -20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <motion.form
        onSubmit={handleSubmit}
        className="bg-white shadow-lg rounded-2xl p-6 space-y-4 border border-gray-200"
        whileHover={{ scale: 1.01 }}
        whileTap={{ scale: 0.99 }}
      >
        <h2 className="text-xl font-bold text-gray-800 flex items-center gap-2">
          {form.id ? (
            <>
              <RefreshCw size={20} className="text-blue-500" /> Update Employee
            </>
          ) : (
            <>
              <Save size={20} className="text-green-500" /> Add Employee
            </>
          )}
        </h2>

        {/* Name Input */}
        <div className="flex flex-col">
          <div className="flex items-center border rounded-lg px-3 py-2 shadow-sm focus-within:ring-2 focus-within:ring-blue-400">
            <User className="text-gray-500 mr-2" size={18} />
            <input
              type="text"
              name="name"
              placeholder="Full Name"
              value={form.name}
              onChange={handleChange}
              className="w-full outline-none text-gray-700"
            />
          </div>
          {errors.name && <p className="text-red-500 text-sm mt-1">{errors.name}</p>}
        </div>

        {/* Email Input */}
        <div className="flex flex-col">
          <div className="flex items-center border rounded-lg px-3 py-2 shadow-sm focus-within:ring-2 focus-within:ring-blue-400">
            <Mail className="text-gray-500 mr-2" size={18} />
            <input
              type="email"
              name="email"
              placeholder="Email Address"
              value={form.email}
              onChange={handleChange}
              className="w-full outline-none text-gray-700"
            />
          </div>
          {errors.email && <p className="text-red-500 text-sm mt-1">{errors.email}</p>}
        </div>

        {/* Position Input */}
        <div className="flex flex-col">
          <div className="flex items-center border rounded-lg px-3 py-2 shadow-sm focus-within:ring-2 focus-within:ring-blue-400">
            <Briefcase className="text-gray-500 mr-2" size={18} />
            <input
              type="text"
              name="position"
              placeholder="Job Position"
              value={form.position}
              onChange={handleChange}
              className="w-full outline-none text-gray-700"
            />
          </div>
          {errors.position && <p className="text-red-500 text-sm mt-1">{errors.position}</p>}
        </div>

        {/* Submit Button */}
        <motion.button
          type="submit"
          className={`w-full py-2 px-4 rounded-lg text-white font-semibold shadow-md ${
            form.id ? 'bg-blue-500 hover:bg-blue-600' : 'bg-green-500 hover:bg-green-600'
          }`}
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
        >
          {form.id ? 'Update Employee' : 'Add Employee'}
        </motion.button>
      </motion.form>
    </motion.div>
  );
};

export default EmployeeForm;
