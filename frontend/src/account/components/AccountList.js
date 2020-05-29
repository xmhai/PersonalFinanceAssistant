import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchAccounts } from '../actions';
import { fetchCurrencies } from '../../common/actions';
import { fetchCategories } from '../../common/actions';
import { MuiTable } from '../../shared/components/MuiComponents';
import { Number } from '../../shared/components';

class AccountList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.categories)) this.props.fetchCategories();
        if (_.isEmpty(this.props.currencies)) this.props.fetchCurrencies();
        this.props.fetchAccounts();
    }

    renderTable() {
        return (
            <MuiTable
                title="Account"
                baseUrl="/accounts"
                data={this.props.accounts}
                columns={[
                    { title: 'Institute Name', field: 'instituteName' },
                    { title: 'Account No.', field: 'accountNo' },
                    { title: 'Account Holder', field: 'accountHolder' },
                    { title: 'Category', field: 'category', lookup: this.props.categories },
                    { title: 'Currency', field: 'currency', lookup: this.props.currencies },
                    { title: 'Amount', field: 'amount', type: 'numeric', render: r => <Number value={r.amount} /> },
                    { title: 'Amount (SGD)', field: 'amountSGD', type: 'numeric', render: r => <Number value={r.amountSGD} /> },
                    { title: 'Maturity Date', field: 'maturityDate', type: 'date' },
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
    if (!_.isEmpty(state.config.currencies) && !_.isEmpty(state.config.categories)) {
        return {
            accounts: _.orderBy(Object.values(state.accounts).map(e => ({
                ...e,
                amountSGD: e.amount * state.config.currencies[e.currency].exchangeRate,
            })), ['accountHolder'], ['asc'] ),
            currencies: _.mapValues(_.keyBy(state.config.currencies, 'id'), 'code'),
            categories: _.mapValues(_.keyBy(state.config.categories, 'id'), 'code')
        };
    }

    return {};
};

export default connect(mapStateToProps, { fetchAccounts, fetchCurrencies, fetchCategories })(AccountList);
