import React from 'react';
import { Container, Header } from 'semantic-ui-react'

import AssetAllocation from './AssetAllocation';

class AppContent extends React.Component {
    render() {
        if (this.props.asssetAllocation) {
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
        } else {
            return (
                <div id='content'>
                    <Container text style={{ marginTop: '7em' }}>
                        <Header as='h4'>Dashboard</Header>
                    </Container>
                </div>
            );
        }
    }
}

export default AppContent;