import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchDividends } from '../actions';
import { fetchStocks } from '../../stock/actions';
import { MuiTable } from '../../shared/components/MuiComponents';

class DividendList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
        this.props.fetchDividends();
    }

    renderTable() {
        return (
            <MuiTable
                title="Dividend"
                baseUrl="/dividends"
                data={this.props.dividends}
                columns={[
                    { title: 'Stock', field: 'stockId', lookup: this.props.stocks },
                    { title: 'Pay Date', field: 'payDate' },
                    { title: 'Amount', field: 'amount', type: 'numeric' },
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
        dividends: Object.values(state.dividends),
        stocks: _.mapValues(_.keyBy(state.stocks, 'id'), 'name'),
    };
};

export default connect(mapStateToProps, { fetchDividends, fetchStocks })(DividendList);
