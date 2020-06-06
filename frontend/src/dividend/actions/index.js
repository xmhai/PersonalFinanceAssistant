import { reset } from 'redux-form';

import { stockService } from '../../apis';
import history from '../../app/components/history';
import {
  CREATE_DIVIDEND,
  FETCH_DIVIDENDS,
  FETCH_DIVIDEND,
  DELETE_DIVIDEND,
  EDIT_DIVIDEND
} from './types';

export const createDividend = formValues => async (dispatch, getState) => {
  const response = await stockService.post('/dividends', formValues);

  dispatch({ type: CREATE_DIVIDEND, payload: response.data });
  dispatch(reset('dividendForm')); // reset form
};

export const fetchDividends = () => async dispatch => {
  const response = await stockService.get('/dividends');

  dispatch({ type: FETCH_DIVIDENDS, payload: response.data });
};

export const fetchDividend = id => async dispatch => {
  const response = await stockService.get(`/dividends/${id}`);

  dispatch({ type: FETCH_DIVIDEND, payload: response.data });
};

export const editDividend = (id, formValues) => async dispatch => {
  const response = await stockService.put(`/dividends/${id}`, formValues);

  dispatch({ type: EDIT_DIVIDEND, payload: response.data });
  history.push('/dividends');
};

export const deleteDividend = id => async dispatch => {
  await stockService.delete(`/dividends/${id}`);

  dispatch({ type: DELETE_DIVIDEND, payload: id });
  history.push('/dividends');
};
