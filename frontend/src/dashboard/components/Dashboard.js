import React from 'react';

import AssetAllocation from './AssetAllocation';
import AssetHistory from './AssetHistory';

class Dashboard extends React.Component {
    render() {
        return (
            <div id='content' className="ui container grid" style={{ marginTop: '1em' }}>
                <AssetAllocation />
                <AssetHistory />
            </div>
        );
    }
}

export default Dashboard;