import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchTransactions } from '../actions';
import { fetchStocks } from '../../stock/actions';
import { fetchActions } from '../../common/actions';
import { MuiTable } from '../../shared/components/MuiComponents';

class TransactionList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
        if (_.isEmpty(this.props.actions)) this.props.fetchActions();
        this.props.fetchTransactions();
    }

    renderTable() {
        return (
            <MuiTable
                title="Transaction"
                baseUrl="/transactions"
                data={this.props.transactions}
                columns={[
                    { title: 'Stock', field: 'stockId', lookup: this.props.stocks },
                    { title: 'Transaction Date', field: 'transactionDate' },
                    { title: 'Action', field: 'actionId', lookup: this.props.actions },
                    { title: 'Price', field: 'price', type: 'numeric' },
                    { title: 'Quantity', field: 'quantity', type: 'numeric' },
                ]}
            />
        );
    }

    render() {
        return (
            <div>{this.renderTable()}</div>
        );
    }
}

const mapStateToProps = state => {
    return {
        transactions: Object.values(state.transactions),
        stocks: _.mapValues(_.keyBy(state.stocks, 'id'), 'name'),
        actions: _.mapValues(_.keyBy(state.config.actions, 'id'), 'code'),
    };
};

export default connect(mapStateToProps, { fetchTransactions, fetchStocks, fetchActions })(TransactionList);
