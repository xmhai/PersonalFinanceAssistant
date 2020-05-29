import { stockService } from '../../apis';
import history from '../../app/components/history';
import {
  CREATE_STOCK,
  FETCH_STOCKS,
  FETCH_STOCK,
  DELETE_STOCK,
  EDIT_STOCK
} from './types';

export const createStock = formValues => async (dispatch, getState) => {
  const response = await stockService.post('/stocks', formValues);

  dispatch({ type: CREATE_STOCK, payload: response.data });
  history.push('/stocks');
};

export const fetchStocks = () => async dispatch => {
  const response = await stockService.get('/stocks');

  dispatch({ type: FETCH_STOCKS, payload: response.data });
};

export const fetchStock = id => async dispatch => {
  const response = await stockService.get(`/stocks/${id}`);

  dispatch({ type: FETCH_STOCK, payload: response.data });
};

export const editStock = (id, formValues) => async dispatch => {
  const response = await stockService.put(`/stocks/${id}`, formValues);

  dispatch({ type: EDIT_STOCK, payload: response.data });
  history.push('/stocks');
};

export const deleteStock = id => async dispatch => {
  await stockService.delete(`/stocks/${id}`);

  dispatch({ type: DELETE_STOCK, payload: id });
  history.push('/stocks');
};
