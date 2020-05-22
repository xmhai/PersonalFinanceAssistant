import service from '../../apis/service';
import {
    FETCH_CURRENCIES,
    FETCH_CATEGORIES,
    FETCH_EXCHANGES,
    FETCH_TRANSACTION_TYPES
} from './types';

export const fetchCurrencies = () => async dispatch => {
    const response = await service.get('/config/currencies');

    dispatch({ type: FETCH_CURRENCIES, payload: response.data });
};

export const fetchCategories = () => async dispatch => {
    const response = await service.get('/config/categories');

    dispatch({ type: FETCH_CATEGORIES, payload: response.data });
};

export const fetchExchanges = () => async dispatch => {
    const response = await service.get('/config/exchanges');

    dispatch({ type: FETCH_EXCHANGES, payload: response.data });
};

export const fetchTransactionTypes = () => async dispatch => {
    const response = await service.get('/config/transactiontypes');

    dispatch({type: FETCH_TRANSACTION_TYPES, payload: response.data });
};
