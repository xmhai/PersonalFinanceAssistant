import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchAccounts } from '../actions';
import { fetchCurrencies } from '../../common/actions';
import { fetchCategories } from '../../common/actions';
import { MuiTable } from '../../shared/components/MuiComponents';

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
                    { title: 'Category', field: 'categoryId', lookup: this.props.categories },
                    { title: 'Currency', field: 'currencyId', lookup: this.props.currencies },
                    { title: 'Amount', field: 'amount', type: 'numeric' },
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
    return {
        accounts: Object.values(state.accounts),
        currencies: _.mapValues(_.keyBy(state.config.currencies, 'id'), 'code'),
        categories: _.mapValues(_.keyBy(state.config.categories, 'id'), 'code')
    };
};

export default connect(mapStateToProps, { fetchAccounts, fetchCurrencies, fetchCategories })(AccountList);
