import { stockService } from '../../apis';
import history from '../../app/components/history';
import {
  CREATE_PORTFOLIO,
  FETCH_PORTFOLIOS,
  FETCH_PORTFOLIO,
  DELETE_PORTFOLIO,
  EDIT_PORTFOLIO
} from './types';

export const createPortfolio = formValues => async (dispatch, getState) => {
  const response = await stockService.post('/portfolios', formValues);

  dispatch({ type: CREATE_PORTFOLIO, payload: response.data });
  history.push('/portfolios');
};

export const fetchPortfolios = () => async dispatch => {
  const response = await stockService.get('/portfolios');

  dispatch({ type: FETCH_PORTFOLIOS, payload: response.data });
};

export const fetchPortfolio = id => async dispatch => {
  const response = await stockService.get(`/portfolios/${id}`);

  dispatch({ type: FETCH_PORTFOLIO, payload: response.data });
};

export const editPortfolio = (id, formValues) => async dispatch => {
  const response = await stockService.put(`/portfolios/${id}`, formValues);

  dispatch({ type: EDIT_PORTFOLIO, payload: response.data });
  history.push('/portfolios');
};

export const deletePortfolio = id => async dispatch => {
  await stockService.delete(`/portfolios/${id}`);

  dispatch({ type: DELETE_PORTFOLIO, payload: id });
  history.push('/portfolios');
};
