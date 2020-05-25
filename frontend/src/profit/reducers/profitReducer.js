import _ from 'lodash';
import {
  FETCH_PROFIT,
  FETCH_PROFITS,
  CREATE_PROFIT,
  EDIT_PROFIT,
  DELETE_PROFIT
} from '../actions/types';

export default (state = {}, action) => {
  switch (action.type) {
    case FETCH_PROFITS:
      return { ...state, ..._.mapKeys(action.payload, 'id') };
    case FETCH_PROFIT:
      return { ...state, [action.payload.id]: action.payload };
    case CREATE_PROFIT:
      return { ...state, [action.payload.id]: action.payload };
    case EDIT_PROFIT:
      return { ...state, [action.payload.id]: action.payload };
    case DELETE_PROFIT:
      return _.omit(state, action.payload);
    default:
      return state;
  }
};
