import React from 'react';
import { Container, Header, Table } from 'semantic-ui-react'
import { RadialChart } from 'react-vis'

import { accountService } from '../../apis'
import '../../../node_modules/react-vis/dist/style.css';
  
class AssetAllocation extends React.Component {
    state = {allocation: null, sum: null};

    async componentDidMount() {
        const response = await accountService.get('assets/allocation');

        const sum = response.data.reduce((a, b) => a + b.amount,0);
        const allocation = response.data.map(x => { return {...x, percentage: x.amount*100/sum }});

        this.setState({allocation, sum});
    }

    render() {
        if (this.state.allocation) {
            const myData = this.state.allocation.map(x => { return {angle: x.percentage, label: x.category}});

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
                            {Object.values(this.state.allocation).map(
                                ({ category, amount, percentage }) => {
                                    return (
                                        <Table.Row key={`row-${category}`}>
                                            <Table.Cell>{category}</Table.Cell>
                                            <Table.Cell textAlign="right">{amount.toLocaleString(undefined, {minimumFractionDigits:2,  maximumFractionDigits:2})}</Table.Cell>
                                            <Table.Cell textAlign="right">{percentage.toFixed(2)}</Table.Cell>
                                        </Table.Row>
                                    );
                                }
                            )}                            
                        </Table.Body>
                        <Table.Footer>
                            <Table.Row>
                                <Table.HeaderCell><b>Summary</b></Table.HeaderCell>
                                <Table.HeaderCell textAlign="right"><b>{this.state.sum.toLocaleString(undefined, {minimumFractionDigits:2, maximumFractionDigits:2})}</b></Table.HeaderCell>
                                <Table.HeaderCell textAlign="right"><button>Save</button></Table.HeaderCell>
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
                        <Header as='h4'>Loading...</Header>
                    </Container>
                </div>
            );
        }
    }
}

export default AssetAllocation;