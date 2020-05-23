import _ from 'lodash';
import {
  FETCH_PORTFOLIO,
  FETCH_PORTFOLIOS,
  CREATE_PORTFOLIO,
  EDIT_PORTFOLIO,
  DELETE_PORTFOLIO
} from '../actions/types';

export default (state = {}, action) => {
  switch (action.type) {
    case FETCH_PORTFOLIOS:
      return { ...state, ..._.mapKeys(action.payload, 'id') };
    case FETCH_PORTFOLIO:
      return { ...state, [action.payload.id]: action.payload };
    case CREATE_PORTFOLIO:
      return { ...state, [action.payload.id]: action.payload };
    case EDIT_PORTFOLIO:
      return { ...state, [action.payload.id]: action.payload };
    case DELETE_PORTFOLIO:
      return _.omit(state, action.payload);
    default:
      return state;
  }
};
