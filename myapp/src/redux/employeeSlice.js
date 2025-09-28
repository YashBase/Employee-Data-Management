import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/employees'; // change if needed

// Async Thunks
export const fetchEmployees = createAsyncThunk(
  'employees/fetchAll',
  async () => {
    const response = await axios.get(API_URL);
    return response.data;
  }
);

export const createEmployee = createAsyncThunk(
  'employees/create',
  async (employeeData) => {
    const response = await axios.post(API_URL, employeeData);
    return response.data;
  }
);

export const updateEmployee = createAsyncThunk(
  'employees/update',
  async ({ id, data }) => {
    const response = await axios.put(`${API_URL}/${id}`, data);
    return response.data;
  }
);

export const deleteEmployee = createAsyncThunk(
  'employees/delete',
  async (id) => {
    await axios.delete(`${API_URL}/${id}`);
    return id;
  }
);

export const searchEmployees = createAsyncThunk(
  'employees/search',
  async (name) => {
    const response = await axios.get(`${API_URL}/search?name=${name}`);
    return response.data;
  }
);

// Slice
const employeeSlice = createSlice({
  name: 'employees',
  initialState: {
    list: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      // Fetch
      .addCase(fetchEmployees.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchEmployees.fulfilled, (state, action) => {
        state.loading = false;
        state.list = action.payload;
      })
      .addCase(fetchEmployees.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      })

      // Create
      .addCase(createEmployee.fulfilled, (state, action) => {
        state.list.push(action.payload);
      })

      // Update
      .addCase(updateEmployee.fulfilled, (state, action) => {
        const index = state.list.findIndex((emp) => emp.id === action.payload.id);
        if (index !== -1) state.list[index] = action.payload;
      })

      // Delete
      .addCase(deleteEmployee.fulfilled, (state, action) => {
        state.list = state.list.filter((emp) => emp.id !== action.payload);
      })

      // Search
      .addCase(searchEmployees.fulfilled, (state, action) => {
        state.list = action.payload;
      });
  },
});

export default employeeSlice.reducer;
