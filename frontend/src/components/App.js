import './App.css';

import React from 'react';

import AppMenu from './AppMenu'
import AppContent from './AppContent'
import AppFooter from './AppFooter'
import AppBreadcrumb from './AppBreadcrumb';

import service from '../api/service'

class App extends React.Component {
    state = {assetAllocation: null};

    async componentDidMount() {
        const response = await service.get('asset/allocation');

        const sum = response.data.reduce((a, b) => a + b.amount,0);
        const assetAllocation = response.data.map(x => { return {...x, percentage: x.amount*100/sum }});
        console.log(assetAllocation);

        this.setState({assetAllocation: assetAllocation, sum: sum});
    }

    render() {
        console.log(this.state);
        return (
            <div>
                <AppMenu />
                <AppBreadcrumb />
                <AppContent asssetAllocation={this.state.assetAllocation} sum={this.state.sum} />
                <AppFooter />
            </div>
        );
    }
}

export default App;