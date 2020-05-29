import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchDividends } from '../actions';
import { fetchStocks } from '../../stock/actions';
import { MuiTable } from '../../shared/components/MuiComponents';
import { Number } from '../../shared/components';

class DividendList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
        this.props.fetchDividends();
    }

    renderTable() {
        return (
            <MuiTable
                style={{ maxWidth: "700px", margin: "auto" }}
                title="Dividend"
                baseUrl="/dividends"
                data={this.props.dividends}
                columns={[
                    { title: 'Stock', field: 'stockId', lookup: this.props.stocks },
                    { title: 'Pay Date', field: 'payDate' },
                    { title: 'Amount', field: 'amount', type: 'numeric', render: r => <Number value={r.amount} /> },
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
        dividends: _.orderBy(Object.values(state.dividends), ['payDate'], ['desc']),
        stocks: _.mapValues(_.keyBy(state.stocks, 'id'), 'name'),
    };
};

export default connect(mapStateToProps, { fetchDividends, fetchStocks })(DividendList);
