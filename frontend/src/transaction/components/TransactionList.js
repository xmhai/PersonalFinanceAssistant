import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchTransactions } from '../actions';
import { fetchStocks } from '../../stock/actions';
import { fetchActions } from '../../common/actions';
import { MuiTable } from '../../shared/components/MuiComponents';
import history from '../../app/components/history';
import { Number } from '../../shared/components';

class TransactionList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
        if (_.isEmpty(this.props.actions)) this.props.fetchActions();
        this.props.fetchTransactions();
    }

    renderTable() {
        return (
            <MuiTable
                style={{ maxWidth: "800px", margin: "auto" }}
                title="Transaction"
                baseUrl="/transactions"
                data={this.props.transactions}
                columns={[
                    { title: 'Stock', field: 'stockId', lookup: this.props.stocks },
                    { title: 'Transaction Date', field: 'transactionDate' },
                    { title: 'Action', field: 'action', lookup: this.props.actions },
                    { title: 'Price', field: 'price', type: 'numeric', render: r => <Number value={r.price} /> },
                    { title: 'Quantity', field: 'quantity', type: 'numeric' },
                ]}
                actions={[
                    {
                        icon: 'add',
                        tooltip: `Create Transaction`,
                        isFreeAction: true,
                        style: { "color": "green" },
                        onClick: (event, rowData) => {
                            history.push(`/transactions/new`);
                        }
                    },
                    {
                        icon: 'delete',
                        tooltip: `Delete Transaction`,
                        onClick: (event, rowData) => {
                            history.push(`/transactions/delete/${rowData.id}`);
                        }
                    }
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
        transactions: _.orderBy(Object.values(state.transactions), ['transactionDate'], ['desc']),
        stocks: _.mapValues(_.keyBy(state.stocks, 'id'), 'name'),
        actions: _.mapValues(_.keyBy(state.config.actions, 'id'), 'code'),
    };
};

export default connect(mapStateToProps, { fetchTransactions, fetchStocks, fetchActions })(TransactionList);
