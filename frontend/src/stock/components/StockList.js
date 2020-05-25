import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchStocks } from '../actions';
import { fetchExchanges } from '../../common/actions';
import { fetchCurrencies } from '../../common/actions';
import { fetchCategories } from '../../common/actions';
import { MuiTable } from '../../shared/components/MuiComponents';

class StockList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.exchanges)) this.props.fetchExchanges();
        if (_.isEmpty(this.props.categories)) this.props.fetchCategories();
        if (_.isEmpty(this.props.currencies)) this.props.fetchCurrencies();
        this.props.fetchStocks();
    }

    renderTable() {
        return (
            <MuiTable
                title="Stock"
                baseUrl="/stocks"
                data={this.props.stocks}
                columns={[
                    { title: 'Stock Name', field: 'name' },
                    { title: 'Stock Code', field: 'code' },
                    { title: 'Exchange', field: 'exchangeId', lookup: this.props.exchanges },
                    { title: 'Category', field: 'categoryId', lookup: this.props.categories },
                    { title: 'Currency', field: 'currencyId', lookup: this.props.currencies },
                    { title: 'Latest Price', field: 'latestPrice', type: 'numeric' },
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
        stocks: Object.values(state.stocks),
        exchanges: _.mapValues(_.keyBy(state.config.exchanges, 'id'), 'code'),
        currencies: _.mapValues(_.keyBy(state.config.currencies, 'id'), 'code'),
        categories: _.mapValues(_.keyBy(state.config.categories, 'id'), 'code')
    };
};

export default connect(mapStateToProps, { fetchStocks, fetchExchanges, fetchCurrencies, fetchCategories })(StockList);
