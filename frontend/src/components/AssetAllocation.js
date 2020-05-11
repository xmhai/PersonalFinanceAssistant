import React from 'react';
import { Container, Header, Table } from 'semantic-ui-react'
import { RadialChart } from 'react-vis'

import '../../node_modules/react-vis/dist/style.css';
  
class AssetAllocation extends React.Component {
    render() {
        if (this.props.asssetAllocation) {
            const myData = this.props.asssetAllocation.map(x => { return {angle: x.percentage, label: x.category}});
            //const myData = [{angel: 5}, {angel: 5}, {angel: 45}, {angel: 45}]
            console.log(myData);

            return (
                <div className="ui two column row">
                    <div className="column">
                        <Header as='h3'>Asset Allocation</Header>
                        <Table className="ui very basic table">
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Category</Table.HeaderCell>
                                <Table.HeaderCell textAlign="right">Amount</Table.HeaderCell>
                                <Table.HeaderCell textAlign="right">Percentage (%)</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {Object.values(this.props.asssetAllocation).map(
                                ({ category, amount, percentage }) => {
                                    return (
                                        <Table.Row key={`row-${category}`}>
                                            <Table.Cell>{category}</Table.Cell>
                                            <Table.Cell textAlign="right">{amount.toLocaleString()}</Table.Cell>
                                            <Table.Cell textAlign="right">{percentage.toFixed(2)}</Table.Cell>
                                        </Table.Row>
                                    );
                                }
                            )}                            
                        </Table.Body>
                        <Table.Footer>
                            <Table.Row>
                                <Table.HeaderCell><b>Summary</b></Table.HeaderCell>
                                <Table.HeaderCell textAlign="right"><b>{this.props.sum.toLocaleString()}</b></Table.HeaderCell>
                                <Table.HeaderCell textAlign="right"></Table.HeaderCell>
                            </Table.Row>
                        </Table.Footer>
                        </Table>
                    </div>
                    <div className="column ui centered">
                        <RadialChart className="chart" data={myData} height={300} width={300} showLabels={true} />
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

export default AssetAllocation;