import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { MuiTable } from '../../shared/components/MuiComponents';
import { fetchPortfolios } from '../actions';
import { fetchCurrencies } from '../../common/actions';
import { fetchCategories } from '../../common/actions';
import { Number, ColorNumber } from '../../shared/components';

class PortfolioList extends React.Component {
    componentDidMount() {
        this.props.fetchPortfolios();
        if (_.isEmpty(this.props.categories)) this.props.fetchCategories();
        if (_.isEmpty(this.props.currencies)) this.props.fetchCurrencies();
    }

    renderTable() {
        return (
            <MuiTable
                style={{ width: "100%" }}
                title="Portfolio"
                baseUrl="/portfolio"
                data={this.props.portfolios}
                columns={[
                    { title: 'Stock', field: 'stock.name' },
                    { title: 'Code', field: 'stock.code' },
                    { title: 'Category', field: 'stock.category', lookup: this.props.categories },
                    { title: 'Quantity', field: 'quantity', type: 'numeric' },
                    { title: 'Cost', field: 'cost', type: 'numeric' },
                    { title: 'Latest Price', field: 'stock.latestPrice', type: 'numeric' },
                    { title: 'Amount', field: 'amount', type: 'numeric', render: r => <Number value={r.amount} /> },
                    { title: 'Currency', field: 'stock.currency', lookup: this.props.currencies },
                    { title: 'Amount (SGD)', field: 'amountSGD', type: 'numeric', render: r => <Number value={r.amountSGD} /> },
                    { title: 'Profit/Lost', field: 'profit', type: 'numeric', render: r => <ColorNumber value={r.profit} /> },
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
    if (!_.isEmpty(state.config.currencies)) {
        return {
            portfolios: _.orderBy(Object.values(state.portfolios).map(el => ({ ...el, 
                amount: el.stock.latestPrice * el.quantity, 
                amountSGD: el.stock.latestPrice * el.quantity * state.config.currencies[el.stock.currency].exchangeRate, 
                profit: (el.stock.latestPrice - el.cost) * el.quantity * state.config.currencies[el.stock.currency].exchangeRate,
            })), ['amountSGD'], ['desc'] ),
            currencies: _.mapValues(_.keyBy(state.config.currencies, 'id'), 'code'),
            categories: _.mapValues(_.keyBy(state.config.categories, 'id'), 'code')
        };
    };

    return {};
};

export default connect(mapStateToProps, { fetchPortfolios, fetchCurrencies, fetchCategories })(PortfolioList);