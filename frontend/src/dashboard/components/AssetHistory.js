import React from 'react';
import { Container, Header, Table } from 'semantic-ui-react'
import { XYPlot, XAxis, YAxis, VerticalGridLines, HorizontalGridLines, LineSeries } from 'react-vis'

import { accountService } from '../../apis'
import '../../../node_modules/react-vis/dist/style.css';

class AssetHistory extends React.Component {
    state = { allocations: [] };

    async componentDidMount() {
        const response = await accountService.get('assets/history');
        this.setState({ allocations: response.data });
    }

    render() {
        if (this.state.allocations) {
            const myData = this.state.allocations.slice(0).reverse().map(e => { return { x: e.recordDate, y: e.total } });

            return (
                <div className="ui two column row">
                    <div className="column">
                        <Header as='h3'>Asset History</Header>
                        <Table className="ui very basic table">
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>Date</Table.HeaderCell>
                                    <Table.HeaderCell textAlign="right">Amount</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>
                            <Table.Body>
                                {Object.values(this.state.allocations).map(
                                    ({ recordDate, total }) => {
                                        return (
                                            <Table.Row key={`row-${recordDate}`}>
                                                <Table.Cell>{recordDate}</Table.Cell>
                                                <Table.Cell textAlign="right">{total.toLocaleString(undefined, {minimumFractionDigits:2,  maximumFractionDigits:2})}</Table.Cell>
                                            </Table.Row>
                                        );
                                    }
                                )}
                            </Table.Body>
                        </Table>
                    </div>
                    <div className="column ui centered">
                        <XYPlot
                            xType="ordinal"
                            width={500}
                            height={300}>
                            <VerticalGridLines />
                            <HorizontalGridLines />
                            <XAxis title="Time" />
                            <YAxis title="Amount" />
                            <LineSeries data={myData} style={{ stroke: 'violet', strokeWidth: 3 }}/>
                        </XYPlot>
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

export default AssetHistory;