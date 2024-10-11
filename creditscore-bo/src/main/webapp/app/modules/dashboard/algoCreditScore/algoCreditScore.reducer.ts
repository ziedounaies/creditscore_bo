/*import axios from 'axios';
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

const initialState = {
  loading: false,
  creditScore: null as number | null,
  errorMessage: null as string | null,
};

export type AlgoCreditScoreState = typeof initialState;

// Actions
export const calculateCreditScore = createAsyncThunk(
  'algoCreditScore/calculate',
  async () => {
    const response = await axios.get('/api/calculate-credit-score');
    return response.data;
  }
);

// Slice
const algoCreditScoreSlice = createSlice({
  name: 'algoCreditScore',
  initialState,
  reducers: {},
  extraReducers: builder => {
    builder
      .addCase(calculateCreditScore.pending, state => {
        state.loading = true;
        state.errorMessage = null;
        state.creditScore = null;
      })
      .addCase(calculateCreditScore.fulfilled, (state, action) => {
        state.loading = false;
        state.creditScore = action.payload;
      })
      .addCase(calculateCreditScore.rejected, (state, action) => {
        state.loading = false;
        state.errorMessage = action.error.message || 'Something went wrong';
      });
  },
});

export default algoCreditScoreSlice.reducer;

*/
