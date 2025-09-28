import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { motion, AnimatePresence } from 'framer-motion';
import { Edit, Trash2, Loader2, Search, XCircle } from 'lucide-react';
import { fetchEmployees, deleteEmployee, searchEmployees } from '../redux/employeeSlice';

const EmployeeList = ({ onEdit }) => {
  const dispatch = useDispatch();
  const { list, loading, error } = useSelector((state) => state.employees);
  const [query, setQuery] = useState('');

  useEffect(() => {
    dispatch(fetchEmployees());
  }, [dispatch]);

  const handleSearch = () => {
    if (query.trim()) {
      dispatch(searchEmployees(query));
    } else {
      dispatch(fetchEmployees());
    }
  };

  const handleReset = () => {
    setQuery('');
    dispatch(fetchEmployees());
  };

  if (loading)
    return (
      <div className="flex justify-center items-center py-6">
        <Loader2 className="animate-spin text-indigo-500" size={32} />
        <p className="ml-2 text-indigo-600 font-medium">Loading employees...</p>
      </div>
    );

  if (error) return <p className="text-red-500 font-medium">Error: {error}</p>;

  return (
    <div className="mt-6 max-w-6xl mx-auto">
      {/* Search Bar */}
      <motion.div
        className="mb-4 max-w-md mx-auto"
        initial={{ opacity: 0, y: -15 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.4 }}
      >
        <div className="bg-white shadow-md rounded-2xl p-4 flex items-center gap-2 border border-gray-200">
          <div className="flex items-center w-full border rounded-lg px-3 py-2 shadow-sm focus-within:ring-2 focus-within:ring-indigo-400">
            <Search size={18} className="text-gray-500 mr-2" />
            <input
              type="text"
              placeholder="Search employees by name..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              className="w-full outline-none text-gray-700"
            />
          </div>
          <motion.button
            onClick={handleSearch}
            className="bg-indigo-500 text-white px-4 py-2 rounded-lg font-semibold shadow-md hover:bg-indigo-600"
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
          >
            Search
          </motion.button>
          <motion.button
            onClick={handleReset}
            className="bg-gray-200 text-gray-700 px-3 py-2 rounded-lg font-semibold shadow-md hover:bg-gray-300 flex items-center gap-1"
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
          >
            <XCircle size={16} /> Reset
          </motion.button>
        </div>
      </motion.div>

      {/* Employee Table */}
      <div className="overflow-hidden border border-gray-200 rounded-2xl shadow-lg">
        <div className="overflow-y-auto max-h-[400px] scrollbar-hide">
          <table className="w-full min-w-[700px] bg-white table-auto">
            <thead className="bg-indigo-500 text-white sticky top-0 z-10">
              <tr>
                <th className="px-4 py-3 text-left">ID</th>
                <th className="px-4 py-3 text-left">Name</th>
                <th className="px-4 py-3 text-left">Email</th>
                <th className="px-4 py-3 text-left">Position</th>
                <th className="px-4 py-3 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              <AnimatePresence>
                {list.length > 0 ? (
                  list.map((emp, index) => (
                    <motion.tr
                      key={emp.id}
                      initial={{ opacity: 0, y: 10 }}
                      animate={{ opacity: 1, y: 0 }}
                      exit={{ opacity: 0, y: -10 }}
                      transition={{ duration: 0.3 }}
                      className={`${index % 2 === 0 ? 'bg-gray-50' : 'bg-white'} hover:bg-indigo-50 transition-colors`}
                    >
                      <td className="px-4 py-3">{emp.id}</td>
                      <td className="px-4 py-3 font-medium text-gray-700">{emp.name}</td>
                      <td className="px-4 py-3 text-gray-600">{emp.email}</td>
                      <td className="px-4 py-3 text-gray-600">{emp.position}</td>
                      <td className="px-4 py-3 flex justify-center gap-3">
                        <motion.button
                          onClick={() => onEdit(emp)}
                          className="flex items-center gap-1 bg-blue-500 text-white px-3 py-1 rounded-lg shadow-md hover:bg-blue-600"
                          whileHover={{ scale: 1.05 }}
                          whileTap={{ scale: 0.95 }}
                        >
                          <Edit size={16} /> Edit
                        </motion.button>
                        <motion.button
                          onClick={() => dispatch(deleteEmployee(emp.id))}
                          className="flex items-center gap-1 bg-red-500 text-white px-3 py-1 rounded-lg shadow-md hover:bg-red-600"
                          whileHover={{ scale: 1.05 }}
                          whileTap={{ scale: 0.95 }}
                        >
                          <Trash2 size={16} /> Delete
                        </motion.button>
                      </td>
                    </motion.tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="5" className="text-center py-6 text-gray-500 font-medium">
                      No employees found.
                    </td>
                  </tr>
                )}
              </AnimatePresence>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default EmployeeList;
