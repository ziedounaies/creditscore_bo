/*import reducer, { calculateCreditScore } from './algoCreditScore.reducer';
import configureStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

const mockStore = configureStore([thunk]);
const mock = new MockAdapter(axios);

describe('Algo Credit Score Reducer', () => {
  let store;

  beforeEach(() => {
    store = mockStore({});
  });

  it('should handle initial state', () => {
    expect(reducer(undefined, { type: '@@INIT' })).toEqual({
      loading: false,
      creditScore: null,
      errorMessage: null,
    });
  });

  it('should handle calculateCreditScore pending', () => {
    const action = { type: calculateCreditScore.pending.type };
    const state = reducer(undefined, action);
    expect(state).toEqual({
      loading: true,
      creditScore: null,
      errorMessage: null,
    });
  });

  it('should handle calculateCreditScore fulfilled', () => {
    const action = { type: calculateCreditScore.fulfilled.type, payload: 750 };
    const state = reducer(undefined, action);
    expect(state).toEqual({
      loading: false,
      creditScore: 750,
      errorMessage: null,
    });
  });

  it('should handle calculateCreditScore rejected', () => {
    const action = { type: calculateCreditScore.rejected.type, error: { message: 'Error' } };
    const state = reducer(undefined, action);
    expect(state).toEqual({
      loading: false,
      creditScore: null,
      errorMessage: 'Error',
    });
  });

  it('should dispatch calculateCreditScore action and update state', async () => {
    const creditScore = 750;
    mock.onGet('/api/calculate-credit-score').reply(200, creditScore);

    const expectedActions = [
      { type: calculateCreditScore.pending.type },
      { type: calculateCreditScore.fulfilled.type, payload: creditScore },
    ];

    await store.dispatch(calculateCreditScore() as any);
    expect(store.getActions()).toEqual(expectedActions);
  });
});*/

