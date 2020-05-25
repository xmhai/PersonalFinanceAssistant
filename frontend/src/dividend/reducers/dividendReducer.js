import _ from 'lodash';
import {
  FETCH_DIVIDEND,
  FETCH_DIVIDENDS,
  CREATE_DIVIDEND,
  EDIT_DIVIDEND,
  DELETE_DIVIDEND
} from '../actions/types';

export default (state = {}, action) => {
  switch (action.type) {
    case FETCH_DIVIDENDS:
      return { ...state, ..._.mapKeys(action.payload, 'id') };
    case FETCH_DIVIDEND:
      return { ...state, [action.payload.id]: action.payload };
    case CREATE_DIVIDEND:
      return { ...state, [action.payload.id]: action.payload };
    case EDIT_DIVIDEND:
      return { ...state, [action.payload.id]: action.payload };
    case DELETE_DIVIDEND:
      return _.omit(state, action.payload);
    default:
      return state;
  }
};
