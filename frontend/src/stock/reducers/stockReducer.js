import _ from 'lodash';
import {
  FETCH_STOCK,
  FETCH_STOCKS,
  CREATE_STOCK,
  EDIT_STOCK,
  DELETE_STOCK
} from '../actions/types';

export default (state = {}, action) => {
  switch (action.type) {
    case FETCH_STOCKS:
      return { ...state, ..._.mapKeys(action.payload, 'id') };
    case FETCH_STOCK:
      return { ...state, [action.payload.id]: action.payload };
    case CREATE_STOCK:
      return { ...state, [action.payload.id]: action.payload };
    case EDIT_STOCK:
      return { ...state, [action.payload.id]: action.payload };
    case DELETE_STOCK:
      return _.omit(state, action.payload);
    default:
      return state;
  }
};
