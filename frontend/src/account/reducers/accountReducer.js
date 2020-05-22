import _ from 'lodash';
import {
  FETCH_ACCOUNT,
  FETCH_ACCOUNTS,
  CREATE_ACCOUNT,
  EDIT_ACCOUNT,
  DELETE_ACCOUNT
} from '../actions/types';

export default (state = {}, action) => {
  switch (action.type) {
    case FETCH_ACCOUNTS:
      return { ...state, ..._.mapKeys(action.payload, 'id') };
    case FETCH_ACCOUNT:
      return { ...state, [action.payload.id]: action.payload };
    case CREATE_ACCOUNT:
      return { ...state, [action.payload.id]: action.payload };
    case EDIT_ACCOUNT:
      return { ...state, [action.payload.id]: action.payload };
    case DELETE_ACCOUNT:
      return _.omit(state, action.payload);
    default:
      return state;
  }
};
