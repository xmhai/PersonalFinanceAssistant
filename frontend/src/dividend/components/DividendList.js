import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchDividends } from '../actions';
import { fetchStocks } from '../../stock/actions';

import { Number } from '../../shared/components';
import { ReactTable } from '../../shared/components/react-table/ReactTable';

class DividendList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
        this.props.fetchDividends();
    }

    columns = [
        { Header: 'Stock',
          accessor: 'stockName',
          width: "300px",
        },
        { Header: 'Pay Date',
          accessor: 'payDate',
        },
        { Header: 'Amount',
          accessor: 'amount',
          type: 'numeric',
          sortType: 'basic',
          Cell: ({ cell: { value } }) => <Number value={value} />,
          align: 'right',
          Footer: info => {
            const total = info.rows.reduce((sum, row) => row.values.amount + sum, 0);
            return <><Number value={total} /></>
        },
    },
    ];

    renderTable() {
        return (
            <>
            <ReactTable columns={this.columns} data={this.props.dividends} title="Divident" baseUrl="/dividends"/>
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
    const stocksMap = _.mapValues(_.keyBy(state.stocks, 'id'), 'name');
    const dividends = _.orderBy(Object.values(state.dividends), ['payDate'], ['desc'])
                     .map(obj=> ({ ...obj, stockName: stocksMap[obj.stockId] }));
    return {
        dividends: dividends,
        stocks: stocksMap,
    };
};

export default connect(mapStateToProps, { fetchDividends, fetchStocks })(DividendList);
