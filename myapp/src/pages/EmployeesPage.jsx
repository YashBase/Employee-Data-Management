import React, { useState } from "react";
import { motion } from "framer-motion";
import { Users, UserPlus } from "lucide-react";
import EmployeeForm from "./EmployeeForm";
import EmployeeList from "./EmployeeList";

const EmployeePage = () => {
  const [currentEmployee, setCurrentEmployee] = useState(null);

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 p-8">
      {/* Header */}
      <motion.header
        initial={{ opacity: 0, y: -30 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.7 }}
        className="mb-10 text-center"
      >
        <h2 className="flex items-center justify-center gap-3 text-4xl font-extrabold text-gray-800 tracking-tight">
          <Users className="w-10 h-10 text-blue-600" />
          Employee Management
        </h2>
        <p className="text-gray-500 mt-2 text-lg">
          Manage your workforce easily — add, edit, or remove employees with real-time updates.
        </p>
      </motion.header>

      {/* Main content */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Left column - Form */}
        <motion.div
          initial={{ opacity: 0, x: -40 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.6 }}
          className="col-span-1"
        >
          <motion.div
            className="bg-white rounded-2xl shadow-xl p-6 transition hover:shadow-2xl hover:-translate-y-1"
            whileHover={{ scale: 1.02 }}
          >
            <h3 className="flex items-center gap-2 text-2xl font-semibold text-gray-700 mb-5">
              <UserPlus className="w-6 h-6 text-green-600" />
              {currentEmployee ? "Edit Employee" : "Add Employee"}
            </h3>
            <EmployeeForm
              currentEmployee={currentEmployee}
              setCurrentEmployee={setCurrentEmployee}
            />
          </motion.div>
        </motion.div>

        {/* Right column - Employee List */}
        <motion.div
          initial={{ opacity: 0, x: 40 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.6 }}
          className="col-span-2"
        >
          <motion.div
            className="bg-white rounded-2xl shadow-xl p-6 transition hover:shadow-2xl hover:-translate-y-1"
            whileHover={{ scale: 1.01 }}
          >
            <h3 className="flex items-center gap-2 text-2xl font-semibold text-gray-700 mb-5">
              <Users className="w-6 h-6 text-blue-600" />
              Employee Directory
            </h3>
            {/* EmployeeList now contains the search bar internally */}
            <EmployeeList onEdit={setCurrentEmployee} />
          </motion.div>
        </motion.div>
      </div>
    </div>
  );
};

export default EmployeePage;
