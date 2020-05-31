import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchProfits } from '../actions';
import { fetchStocks } from '../../stock/actions';
import { MuiTable } from '../../shared/components/MuiComponents';
import { ColorNumber } from '../../shared/components';

class ProfitList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
        this.props.fetchProfits();
    }

    renderTable() {
        return (
            <>
                <MuiTable
                    style={{ maxWidth: "1120px", margin:"auto" }}
                    title="Profit"
                    baseUrl="/profits"
                    data={this.props.profits}
                    columns={[
                        { title: 'Stock', field: 'stock.name' },
                        { title: 'Dividend', field: 'dividend', type: 'numeric', render: r => <ColorNumber value={r.dividend} /> },
                        { title: 'Realized', field: 'realized', type: 'numeric', render: r => <ColorNumber value={r.realized} /> },
                        { title: 'Unrealized', field: 'unrealized', type: 'numeric', render: r => <ColorNumber value={r.unrealized} /> },
                        { title: 'Profit/Lost', field: 'profit', type: 'numeric', render: r => <ColorNumber value={r.profit} /> },
                    ]}
                />
                <div><b>Total Profit: {this.props.totalProft}</b></div>
            </>
        );
    }

    render() {
        return (
            <div>{this.renderTable()}</div>
        );
    }
}

const mapStateToProps = state => {
    if (state.profits) {
        return {
            totalProft: Object.values(state.profits).reduce((a, b) => a + b.profit, 0),
            profits: _.orderBy(Object.values(state.profits), [p => p.stock.name.toLowerCase()], ['asc']),
            stocks: _.mapValues(_.keyBy(state.stocks, 'id'), 'name'),
        };
    }
    return {};
};

export default connect(mapStateToProps, { fetchProfits, fetchStocks })(ProfitList);
