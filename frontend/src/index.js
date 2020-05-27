import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, combineReducers, applyMiddleware, compose } from 'redux';
import reduxThunk from 'redux-thunk';
import { reducer as formReducer } from 'redux-form';

import App from './app/components/App'
import accountReducer from './account/reducers/accountReducer';
import stockReducer from './stock/reducers/stockReducer';
import portfolioReducer from './portfolio/reducers/portfolioReducer';
import configReducer from './common/reducers/configReducer';
import transactionReducer from './transaction/reducers/transactionReducer';
import dividendReducer from './dividend/reducers/dividendReducer';
import profitReducer from './profit/reducers/profitReducer';
import setupAxiosInterceptors from './apis';
import loaderReducer from './app/reducers/loaderReducer';

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(
    combineReducers({
        form: formReducer,
        accounts: accountReducer,
        stocks: stockReducer,
        portfolios: portfolioReducer,
        config: configReducer,
        transactions: transactionReducer,
        dividends: dividendReducer,
        profits: profitReducer,
        loader: loaderReducer,
    }),
    composeEnhancers(applyMiddleware(reduxThunk))
);

setupAxiosInterceptors(store);

ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.querySelector('#root')
);
