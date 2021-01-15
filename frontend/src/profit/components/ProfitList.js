import React from 'react'
import { connect } from 'react-redux';
import _ from 'lodash';

import { fetchProfits } from '../actions';
import { fetchStocks } from '../../stock/actions';
import { fetchExchanges } from '../../common/actions';

import { MuiTable } from '../../shared/components/MuiComponents';
import { ColorNumber } from '../../shared/components';
import { ReactTable } from '../../shared/components/react-table/ReactTable';

import IconButton from '@material-ui/core/IconButton';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';
import DescriptionIcon from '@material-ui/icons/Description';

import history from '../../app/components/history';

class ProfitList extends React.Component {
    componentDidMount() {
        if (_.isEmpty(this.props.exchanges)) this.props.fetchExchanges();
        this.props.fetchProfits();
    }

    columns = [
          { Header: 'Stock',
            accessor: 'stock.name',
            width: "300px",
          },
          { Header: 'Exchange',
            accessor: 'exchange',
          },
          { Header: 'Dividend',
            accessor: 'dividend',
            type: 'numeric',
            sortType: 'basic',
            Cell: ({ cell: { value } }) => <ColorNumber value={value} />,
            align: 'right',
          },
          { Header: 'Realized',
            accessor: 'realized',
            type: 'numeric',
            sortType: 'basic',
            Cell: ({ cell: { value } }) => <ColorNumber value={value} /> 
          },
          { Header: 'Unrealized',
            accessor: 'unrealized',
            type: 'numeric',
            sortType: 'basic',
            Footer: "Total",
            Cell: ({ cell: { value } }) => <ColorNumber value={value} />
          },
          { Header: 'Profit/Lost',
            accessor: 'profit',
            type: 'numeric',
            sortType: 'basic',
            Cell: ({ cell: { value } }) => <ColorNumber value={value} />,
            Footer: info => {
                const total = info.rows.reduce((sum, row) => row.values.profit + sum, 0);
                return <><ColorNumber value={total} /></>
            },
          },
    ];

    renderTable() {
        return (
            <>
            <ReactTable columns={this.columns} data={this.props.profits} title="Profit" baseUrl="/profits"/>
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
        const exchangeMap = _.mapValues(_.keyBy(state.config.exchanges, 'id'), 'code');
        const profits = _.orderBy(Object.values(state.profits), [p => p.stock.name.toLowerCase()], ['asc'])
                         .map(obj=> ({ ...obj, exchange: exchangeMap[obj.stock.exchange] }));
        return {
            profits: profits,
        };
    }
    return {};
};

export default connect(mapStateToProps, { fetchProfits, fetchExchanges })(ProfitList);
