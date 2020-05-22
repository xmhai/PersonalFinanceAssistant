import service from '../../apis/service';
import history from '../../history';
import {
  CREATE_ACCOUNT,
  FETCH_ACCOUNTS,
  FETCH_ACCOUNT,
  DELETE_ACCOUNT,
  EDIT_ACCOUNT
} from './types';

export const createAccount = formValues => async (dispatch, getState) => {
  const response = await service.post('/accounts', formValues);

  dispatch({ type: CREATE_ACCOUNT, payload: response.data });
  history.push('/accounts');
};

export const fetchAccounts = () => async dispatch => {
  const response = await service.get('/accounts');

  dispatch({ type: FETCH_ACCOUNTS, payload: response.data });
};

export const fetchAccount = id => async dispatch => {
  const response = await service.get(`/accounts/${id}`);

  dispatch({ type: FETCH_ACCOUNT, payload: response.data });
};

export const editAccount = (id, formValues) => async dispatch => {
  const response = await service.put(`/accounts/${id}`, formValues);

  dispatch({ type: EDIT_ACCOUNT, payload: response.data });
  history.push('/accounts');
};

export const deleteAccount = id => async dispatch => {
  await service.delete(`/accounts/${id}`);

  dispatch({ type: DELETE_ACCOUNT, payload: id });
  history.push('/accounts');
};
