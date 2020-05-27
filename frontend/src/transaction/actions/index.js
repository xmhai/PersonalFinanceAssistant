import { stockService } from '../../apis';
import history from '../../app/components/history';
import {
  CREATE_TRANSACTION,
  FETCH_TRANSACTIONS,
  FETCH_TRANSACTION,
  DELETE_TRANSACTION,
  EDIT_TRANSACTION
} from './types';

export const createTransaction = formValues => async (dispatch, getState) => {
  const response = await stockService.post('/transactions', formValues);

  dispatch({ type: CREATE_TRANSACTION, payload: response.data });
  history.push('/transactions');
};

export const fetchTransactions = () => async dispatch => {
  const response = await stockService.get('/transactions');

  dispatch({ type: FETCH_TRANSACTIONS, payload: response.data });
};

export const fetchTransaction = id => async dispatch => {
  const response = await stockService.get(`/transactions/${id}`);

  dispatch({ type: FETCH_TRANSACTION, payload: response.data });
};

export const editTransaction = (id, formValues) => async dispatch => {
  const response = await stockService.put(`/transactions/${id}`, formValues);

  dispatch({ type: EDIT_TRANSACTION, payload: response.data });
  history.push('/transactions');
};

export const deleteTransaction = id => async dispatch => {
  await stockService.delete(`/transactions/${id}`);

  dispatch({ type: DELETE_TRANSACTION, payload: id });
  history.push('/transactions');
};
