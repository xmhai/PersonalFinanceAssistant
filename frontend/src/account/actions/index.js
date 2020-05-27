import { accountService } from '../../apis';
import history from '../../app/components/history';
import {
  CREATE_ACCOUNT,
  FETCH_ACCOUNTS,
  FETCH_ACCOUNT,
  DELETE_ACCOUNT,
  EDIT_ACCOUNT
} from './types';

export const createAccount = formValues => async (dispatch, getState) => {
  const response = await accountService.post('/accounts', formValues);

  dispatch({ type: CREATE_ACCOUNT, payload: response.data });
  history.push('/accounts');
};

export const fetchAccounts = () => async dispatch => {
  const response = await accountService.get('/accounts');

  dispatch({ type: FETCH_ACCOUNTS, payload: response.data });
};

export const fetchAccount = id => async dispatch => {
  const response = await accountService.get(`/accounts/${id}`);

  dispatch({ type: FETCH_ACCOUNT, payload: response.data });
};

export const editAccount = (id, formValues) => async dispatch => {
  const response = await accountService.put(`/accounts/${id}`, formValues);

  dispatch({ type: EDIT_ACCOUNT, payload: response.data });
  history.push('/accounts');
};

export const deleteAccount = id => async dispatch => {
  await accountService.delete(`/accounts/${id}`);

  dispatch({ type: DELETE_ACCOUNT, payload: id });
  history.push('/accounts');
};
