import React from 'react';

import AssetAllocation from './AssetAllocation';

class Dashboard extends React.Component {
    render() {
        return (
            <div id='content' className="ui container grid" style={{ marginTop: '1em' }}>
                <AssetAllocation asssetAllocation={this.props.asssetAllocation} sum={this.props.sum}/>
                <div className="ui row">
                    <h3>Asset History</h3>
                </div>
                <div className="ui row">
                    <h3>Monthly Expense History</h3>
                </div>
            </div>
        );
    }
}

export default Dashboard;