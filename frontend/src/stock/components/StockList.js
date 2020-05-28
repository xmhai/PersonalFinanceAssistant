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
                    { title: 'Exchange', field: 'exchange.code' },
                    { title: 'Category', field: 'category.code' },
                    { title: 'Currency', field: 'currency.code' },
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
    };
};

export default connect(mapStateToProps, { fetchStocks })(StockList);
