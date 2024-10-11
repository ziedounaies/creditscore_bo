import { AnyAction, configureStore, ThunkAction } from '@reduxjs/toolkit';
import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import { loadingBarMiddleware } from 'react-redux-loading-bar';
//import searchReducer from '../modules/dashboard/chercher/chercher.reducer';
import sharedReducers from 'app/shared/reducers';
import errorMiddleware from './error-middleware';
import notificationMiddleware from './notification-middleware';
import loggerMiddleware from './logger-middleware';
//import algoCreditScoreReducer from '../modules/dashboard/algoCreditScore/algoCreditScore.reducer';

// Combine sharedReducers with algoCreditScoreReducer
const rootReducer = {
  ...sharedReducers,
  //algoCreditScore: algoCreditScoreReducer,
  //searchReducer
};

const store = configureStore({
  reducer: rootReducer,
  middleware: getDefaultMiddleware =>
    getDefaultMiddleware({
      serializableCheck: {
        // Ignore these field paths in all actions
        ignoredActionPaths: ['payload.config', 'payload.request', 'payload.headers', 'error', 'meta.arg'],
      },
    }).concat(errorMiddleware, notificationMiddleware, loadingBarMiddleware(), loggerMiddleware),
});

const getStore = () => store;

export type IRootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppSelector: TypedUseSelectorHook<IRootState> = useSelector;
export const useAppDispatch = () => useDispatch<AppDispatch>();
export type AppThunk<ReturnType = void> = ThunkAction<ReturnType, IRootState, unknown, AnyAction>;
export default getStore;
