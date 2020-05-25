import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchProfits } from '../actions';
import { fetchStocks } from '../../stock/actions';
import { MuiTable } from '../../shared/components/MuiComponents';

class ProfitList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
        this.props.fetchProfits();
    }

    renderTable() {
        return (
            <MuiTable
                title="Profit"
                baseUrl="/profits"
                data={this.props.profits}
                columns={[
                    { title: 'Stock', field: 'stockId', lookup: this.props.stocks },
                    { title: 'Profit', field: 'amount', type: 'numeric' },
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
        profits: Object.values(state.profits),
        stocks: _.mapValues(_.keyBy(state.stocks, 'id'), 'name'),
    };
};

export default connect(mapStateToProps, { fetchProfits, fetchStocks })(ProfitList);
