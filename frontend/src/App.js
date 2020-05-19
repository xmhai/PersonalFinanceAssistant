import './App.css';

import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import AppMenu from './AppMenu'
import AppFooter from './AppFooter'
import AppBreadcrumb from './AppBreadcrumb';
import Dashboard from './dashboard/components/Dashboard'
import AccountList from './account/components/AccountList'
import DividendList from './dividend/components/DividendList'
import ProfitList from './profit/components/ProfitList'
import StockList from './stock/components/StockList'
import TransactionList from './transaction/components/TransactionList'
import PortfolioList from './portfolio/components/PortfolioList'

class App extends React.Component {
    render() {
        return (
            <div className="ui container">
                <BrowserRouter>
                    <div>
                        <AppMenu />
                        <AppBreadcrumb />
                        <Switch>
                            <Route path="/" exact component={ Dashboard } />
                            <Route path="/account" exact component={ AccountList } />
                            <Route path="/dividend" exact component={ DividendList } />
                            <Route path="/profit" exact component={ ProfitList } />
                            <Route path="/stock" exact component={ StockList } />
                            <Route path="/transaction" exact component={ TransactionList } />
                            <Route path="/portfolio" exact component={ PortfolioList } />
                        </Switch>
                        <AppFooter />
                    </div>
                </BrowserRouter>
            </div>
        );
    }
}

export default App;