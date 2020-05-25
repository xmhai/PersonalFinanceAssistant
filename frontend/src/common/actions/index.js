import { commonService } from '../../apis';
import {
    FETCH_CURRENCIES,
    FETCH_CATEGORIES,
    FETCH_EXCHANGES,
    FETCH_ACTIONS
} from './types';

export const fetchCurrencies = () => async dispatch => {
    const response = await commonService.get('/config/currencies');

    dispatch({ type: FETCH_CURRENCIES, payload: response.data });
};

export const fetchCategories = () => async dispatch => {
    const response = await commonService.get('/config/categories');

    dispatch({ type: FETCH_CATEGORIES, payload: response.data });
};

export const fetchExchanges = () => async dispatch => {
    const response = await commonService.get('/config/exchanges');

    dispatch({ type: FETCH_EXCHANGES, payload: response.data });
};

export const fetchActions = () => async dispatch => {
    const response = await commonService.get('/config/actions');

    dispatch({ type: FETCH_ACTIONS, payload: response.data });
};
