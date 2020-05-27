import { stockService } from '../../apis';
import history from '../../app/components/history';
import {
  CREATE_PROFIT,
  FETCH_PROFITS,
  FETCH_PROFIT,
  DELETE_PROFIT,
  EDIT_PROFIT
} from './types';

export const createProfit = formValues => async (dispatch, getState) => {
  const response = await stockService.post('/profits', formValues);

  dispatch({ type: CREATE_PROFIT, payload: response.data });
  history.push('/profits');
};

export const fetchProfits = () => async dispatch => {
  const response = await stockService.get('/profits');

  dispatch({ type: FETCH_PROFITS, payload: response.data });
};

export const fetchProfit = id => async dispatch => {
  const response = await stockService.get(`/profits/${id}`);

  dispatch({ type: FETCH_PROFIT, payload: response.data });
};

export const editProfit = (id, formValues) => async dispatch => {
  const response = await stockService.put(`/profits/${id}`, formValues);

  dispatch({ type: EDIT_PROFIT, payload: response.data });
  history.push('/profits');
};

export const deleteProfit = id => async dispatch => {
  await stockService.delete(`/profits/${id}`);

  dispatch({ type: DELETE_PROFIT, payload: id });
  history.push('/profits');
};
