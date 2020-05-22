import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import accountReducer from './accountReducer';

export default combineReducers({
  form: formReducer,
  accounts: accountReducer
});
