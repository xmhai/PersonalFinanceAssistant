import './App.css';

import React from 'react';
import { Router, Route, Switch } from 'react-router-dom';

import history from './history';
import AppMenu from './AppMenu'
import AppFooter from './AppFooter'
import Dashboard from './dashboard/components/Dashboard'

import AccountList from './account/components/AccountList'
import AccountCreate from './account/components/AccountCreate'
import AccountEdit from './account/components/AccountEdit'
import AccountDelete from './account/components/AccountDelete'
import AccountView from './account/components/AccountView'

import DividendList from './dividend/components/DividendList'
import ProfitList from './profit/components/ProfitList'
import StockList from './stock/components/StockList'
import TransactionList from './transaction/components/TransactionList'

import PortfolioList from './portfolio/components/PortfolioList'
import PortfolioCreate from './portfolio/components/PortfolioCreate'
import PortfolioEdit from './portfolio/components/PortfolioEdit'
import PortfolioDelete from './portfolio/components/PortfolioDelete'
import PortfolioView from './portfolio/components/PortfolioView'

class App extends React.Component {
    render() {
        return (
            <div className="ui container">
                <Router history={history}>
                    <AppMenu />
                    <div style={{ marginTop: '5em' }}>
                        <Switch>
                            <Route path="/" exact component={ Dashboard } />

                            <Route path="/accounts" exact component={ AccountList } />
                            <Route path="/accounts/new" exact component={ AccountCreate } />
                            <Route path="/accounts/edit/:id" exact component={AccountEdit} />
                            <Route path="/accounts/delete/:id" exact component={AccountDelete} />
                            <Route path="/accounts/:id" exact component={AccountView} />

                            <Route path="/dividends" exact component={ DividendList } />
                            <Route path="/profits" exact component={ ProfitList } />
                            <Route path="/stocks" exact component={ StockList } />
                            <Route path="/transactions" exact component={ TransactionList } />

                            <Route path="/portfolios" exact component={ PortfolioList } />
                            <Route path="/portfolios/new" exact component={PortfolioCreate} />
                            <Route path="/portfolios/edit/:id" exact component={PortfolioEdit} />
                            <Route path="/portfolios/delete/:id" exact component={PortfolioDelete} />
                            <Route path="/portfolios/:id" exact component={PortfolioView} />
                        </Switch>
                    </div>
                    <AppFooter />
                </Router>
            </div>
        );
    }
}

export default App;