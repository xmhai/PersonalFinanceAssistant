import './App.css';

import React from 'react';
import { Router, Route, Switch } from 'react-router-dom';
import { connect } from 'react-redux';

import history from './history';

import AppMenu from './AppMenu'
import AppFooter from './AppFooter'
import Loader from './Loader'

import Dashboard from '../../dashboard/components/Dashboard'

import AccountList from '../../account/components/AccountList'
import AccountCreate from '../../account/components/AccountCreate'
import AccountEdit from '../../account/components/AccountEdit'
import AccountDelete from '../../account/components/AccountDelete'
import AccountView from '../../account/components/AccountView'
import BankTransaction from '../../account/components/BankTransaction'

import PortfolioList from '../../portfolio/components/PortfolioList'
import PortfolioCreate from '../../portfolio/components/PortfolioCreate'
import PortfolioEdit from '../../portfolio/components/PortfolioEdit'
import PortfolioDelete from '../../portfolio/components/PortfolioDelete'
import PortfolioView from '../../portfolio/components/PortfolioView'

import StockList from '../../stock/components/StockList'
import StockCreate from '../../stock/components/StockCreate'
import StockEdit from '../../stock/components/StockEdit'
import StockDelete from '../../stock/components/StockDelete'
import StockView from '../../stock/components/StockView'

import TransactionList from '../../transaction/components/TransactionList'
import TransactionCreate from '../../transaction/components/TransactionCreate'
import TransactionEdit from '../../transaction/components/TransactionEdit'
import TransactionDelete from '../../transaction/components/TransactionDelete'
import TransactionView from '../../transaction/components/TransactionView'

import DividendList from '../../dividend/components/DividendList'
import DividendCreate from '../../dividend/components/DividendCreate'
import DividendEdit from '../../dividend/components/DividendEdit'
import DividendDelete from '../../dividend/components/DividendDelete'
import DividendView from '../../dividend/components/DividendView'
import Dividend from '../../dividend/components/Dividend'

import ProfitList from '../../profit/components/ProfitList'
import ProfitCreate from '../../profit/components/ProfitCreate'
import ProfitEdit from '../../profit/components/ProfitEdit'
import ProfitDelete from '../../profit/components/ProfitDelete'
import ProfitView from '../../profit/components/ProfitView'

import JobConfigList from '../../job/components/JobConfigList'
import JobLogList from '../../job/components/JobLogList'

class App extends React.Component {
    render() {
        return (
            <div className="ui container">
                {this.props.loader.counter>0 ? <Loader /> : null}
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

                            <Route path="/bank/transactions" exact component={BankTransaction} />

                            <Route path="/portfolios" exact component={PortfolioList} />
                            <Route path="/portfolios/new" exact component={PortfolioCreate} />
                            <Route path="/portfolios/edit/:id" exact component={PortfolioEdit} />
                            <Route path="/portfolios/delete/:id" exact component={PortfolioDelete} />
                            <Route path="/portfolios/:id" exact component={PortfolioView} />

                            <Route path="/stocks" exact component={StockList} />
                            <Route path="/stocks/new" exact component={StockCreate} />
                            <Route path="/stocks/edit/:id" exact component={StockEdit} />
                            <Route path="/stocks/delete/:id" exact component={StockDelete} />
                            <Route path="/stocks/:id" exact component={StockView} />

                            <Route path="/transactions" exact component={TransactionList} />
                            <Route path="/transactions/new" exact component={TransactionCreate} />
                            <Route path="/transactions/edit/:id" exact component={TransactionEdit} />
                            <Route path="/transactions/delete/:id" exact component={TransactionDelete} />
                            <Route path="/transactions/:id" exact component={TransactionView} />

                            <Route path="/dividends" exact component={DividendList} />
                            <Route path="/dividends/new" exact component={DividendCreate} />
                            <Route path="/dividends/edit/:id" exact component={DividendEdit} />
                            <Route path="/dividends/delete/:id" exact component={DividendDelete} />
                            <Route path="/dividends/:id" exact component={DividendView} />

                            <Route path="/profits" exact component={ProfitList} />
                            <Route path="/profits/new" exact component={ProfitCreate} />
                            <Route path="/profits/edit/:id" exact component={ProfitEdit} />
                            <Route path="/profits/delete/:id" exact component={ProfitDelete} />
                            <Route path="/profits/:id" exact component={ProfitView} />

                            <Route path="/job/configs" exact component={JobConfigList} />
                            <Route path="/job/logs" exact component={JobLogList} />

                            <Route path="/test" exact component={Dividend} />
                        </Switch>
                    </div>
                    <AppFooter />
                </Router>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        loader: state.loader
    }
}

export default connect(mapStateToProps, { })(App);
