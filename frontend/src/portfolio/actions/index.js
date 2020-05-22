import accountService from '../../apis/accountService';
import history from '../../history';
import {
  CREATE_PORTFOLIO,
  FETCH_PORTFOLIOS,
  FETCH_PORTFOLIO,
  DELETE_PORTFOLIO,
  EDIT_PORTFOLIO
} from './types';

export const createPortfolio = formValues => async (dispatch, getState) => {
  const response = await service.post('/portfolios', formValues);

  dispatch({ type: CREATE_PORTFOLIO, payload: response.data });
  history.push('/portfolios');
};

export const fetchPortfolios = () => async dispatch => {
  const response = await service.get('/portfolios');

  dispatch({ type: FETCH_PORTFOLIOS, payload: response.data });
};

export const fetchPortfolio = id => async dispatch => {
  const response = await service.get(`/portfolios/${id}`);

  dispatch({ type: FETCH_PORTFOLIO, payload: response.data });
};

export const editPortfolio = (id, formValues) => async dispatch => {
  const response = await service.put(`/portfolios/${id}`, formValues);

  dispatch({ type: EDIT_PORTFOLIO, payload: response.data });
  history.push('/portfolios');
};

export const deletePortfolio = id => async dispatch => {
  await service.delete(`/portfolios/${id}`);

  dispatch({ type: DELETE_PORTFOLIO, payload: id });
  history.push('/portfolios');
};
