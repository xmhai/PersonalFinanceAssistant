import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { MuiTable } from '../../shared/components/MuiComponents';
import { fetchStocks } from '../../stock/actions';
import { fetchPortfolios } from '../actions';
import { fetchCurrencies } from '../../common/actions';
import { fetchCategories } from '../../common/actions';

class PortfolioList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.currencies)) this.props.fetchCurrencies();
        if (_.isEmpty(this.props.categories)) this.props.fetchCategories();
        if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
        this.props.fetchPortfolios();
    }

    renderTable() {
        return (
            <MuiTable
                title="Portfolio"
                baseUrl="/portfolio"
                data={this.props.portfolios}
                columns={[
                    { title: 'Stock', field: 'stockId', lookup: this.props.stocks },
                    { title: 'Quantity', field: 'quantity', type: 'currency' },
                    { title: 'Cost', field: 'cost', type: 'numeric' },
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
        portfolios: Object.values(state.portfolios),
        currencies: state.config.currencies,
        categories: state.config.categories,
        stocks: _.mapValues(_.keyBy(state.stocks, 'id'), 'name'),
    };
};

export default connect(mapStateToProps, { fetchPortfolios, fetchCurrencies, fetchCategories, fetchStocks })(PortfolioList);