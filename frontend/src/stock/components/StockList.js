import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchStocks } from '../actions';
import { fetchExchanges } from '../../common/actions';
import { fetchCurrencies } from '../../common/actions';
import { fetchCategories } from '../../common/actions';
import { MuiTable } from '../../shared/components/MuiComponents';
import history from '../../app/components/history';

class StockList extends React.Component {
    componentDidMount() {
        this.props.fetchStocks();
        if (_.isEmpty(this.props.categories)) this.props.fetchCategories();
        if (_.isEmpty(this.props.currencies)) this.props.fetchCurrencies();
        if (_.isEmpty(this.props.exchanges)) this.props.fetchExchanges();
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
                    { title: 'Exchange', field: 'exchange', lookup: this.props.exchanges },
                    { title: 'Category', field: 'category', lookup: this.props.categories },
                    { title: 'Currency', field: 'currency', lookup: this.props.currencies },
                    { title: 'Latest Price', field: 'latestPrice', type: 'numeric' },
                ]}
                actions={[
                    {
                        icon: 'refresh',
                        tooltip: `Update Price`,
                        isFreeAction: true,
                        style: { color: "green" },
                        onClick: (event, rowData) => {
                            alert("Update Price");
                        }
                    },
                    {
                        icon: 'add',
                        tooltip: `Create Stock`,
                        isFreeAction: true,
                        style: { "color": "green" },
                        onClick: (event, rowData) => {
                            history.push(`/stocks/new`);
                        }
                    },
                    {
                        icon: 'edit',
                        tooltip: `Edit Stock`,
                        onClick: (event, rowData) => {
                            history.push(`/stocks/edit/${rowData.id}`);
                        }
                    },
                    {
                        icon: 'delete',
                        tooltip: `Delete Stock`,
                        onClick: (event, rowData) => {
                            history.push(`/stocks/delete/${rowData.id}`);
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
        stocks: Object.values(state.stocks),
        exchanges: _.mapValues(_.keyBy(state.config.exchanges, 'id'), 'code'),
        currencies: _.mapValues(_.keyBy(state.config.currencies, 'id'), 'code'),
        categories: _.mapValues(_.keyBy(state.config.categories, 'id'), 'code')
    };
};

export default connect(mapStateToProps, { fetchStocks, fetchExchanges, fetchCurrencies, fetchCategories })(StockList);
