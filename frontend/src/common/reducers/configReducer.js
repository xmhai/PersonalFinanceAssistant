import _ from 'lodash';
import {
    FETCH_CURRENCIES,
    FETCH_CATEGORIES,
    FETCH_EXCHANGES,
    FETCH_ACTIONS
} from '../actions/types';

export default (state = { "currencies": [], "categories": [], "exchanges": [], "transactionTypes":[] }, action) => {
    switch (action.type) {
        case FETCH_CURRENCIES:
            return { ...state, "currencies": _.mapKeys(action.payload, 'id') };
        case FETCH_CATEGORIES:
            return { ...state, "categories": _.mapKeys(action.payload, 'id') };
        case FETCH_EXCHANGES:
            return { ...state, "exchanges": { ..._.mapKeys(action.payload, 'id') } };
        case FETCH_ACTIONS:
            return { ...state, "actions": { ..._.mapKeys(action.payload, 'id') } };
        default:
            return state;
    }
};
