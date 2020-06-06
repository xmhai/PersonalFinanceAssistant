import React from 'react'
import _ from 'lodash';

import { MuiEditTable } from '../../shared/components/MuiComponents';
import { Number } from '../../shared/components';
import { stockService } from '../../apis';

class DividendList extends React.Component {
    state = { dividends: [], stocks: {}, isError: false, errorMessages: [] };

    componentDidMount() {
        stockService.get("/dividends")
            .then(res => {
                this.setState({ dividends: _.orderBy(Object.values(res.data), ['payDate'], ['desc']) });
            })
            .catch(error => {
                console.log("Error");
            });

        stockService.get("/stocks")
            .then(res => {
                this.setState({ stocks: _.mapValues(_.keyBy(res.data, 'id'), 'name') });
            })
            .catch(error => {
                console.log("Error");
            });
    }

    handleRowUpdate = (newData, oldData, resolve) => {
        let errorList = this.validateData(newData);
        if (errorList.length < 1) {
            stockService.put("/dividends/" + newData.id, newData)
                .then(res => {
                    const dataUpdate = [...this.state.dividends];
                    const index = oldData.tableData.id;
                    dataUpdate[index] = newData;
                    this.setState({ dividends: [...dataUpdate] });
                    resolve()
                })
                .catch(error => {
                    resolve()

                })
        } else {
            resolve()
        }
    }

    handleRowAdd = (newData, resolve) => {
        let errorList = this.validateData(newData);
        if (errorList.length < 1) { //no error
            stockService.post("/dividends", newData)
                .then(res => {
                    let dataToAdd = [...this.state.dividends];
                    dataToAdd.push(newData);
                    this.setState({ dividends: [...dataToAdd] });
                    resolve()
                })
                .catch(error => {
                    resolve()
                })
        } else {
            resolve()
        }
    }

    validateData(newData) {
        let errorList = []
        if (newData.stockId === undefined || newData.stockId === "") {
            errorList.push("Please enter stockId")
        }
        if (newData.payDate === undefined || newData.payDate === "") {
            errorList.push("Please enter payDate")
        }
        if (newData.amount === undefined || newData.amount === "") {
            errorList.push("Please enter amount")
        }
        return errorList;
    }

    handleRowDelete = (oldData, resolve) => {
        if (window.confirm("Delete the item?")) {
            stockService.delete("/dividends/" + oldData.id)
                .then(res => {
                    const dataDelete = [...this.state.dividends];
                    const index = oldData.tableData.id;
                    dataDelete.splice(index, 1);
                    this.setState({ dividends: [...dataDelete] });
                    resolve()
                })
                .catch(error => {
                    resolve()
                })
        };
    }

    renderTable() {
        return (
            <MuiEditTable
                style={{ maxWidth: "700px", margin: "auto" }}
                title="Dividend"
                baseUrl="/dividends"
                data={this.state.dividends}
                columns={[
                    { title: "id", field: "id", hidden: true },
                    { title: 'Stock', field: 'stockId', lookup: this.state.stocks, editable: 'onAdd' },
                    { title: 'Pay Date', field: 'payDate', type: 'date' },
                    { title: 'Amount', field: 'amount', type: 'numeric', editable: 'onAdd', render: r => <Number value={r.amount} /> },
                ]}
                editable={{
                    onRowUpdate: (newData, oldData) =>
                        new Promise((resolve) => {
                            this.handleRowUpdate(newData, oldData, resolve);

                        }),
                    onRowAdd: (newData) =>
                        new Promise((resolve) => {
                            this.handleRowAdd(newData, resolve)
                        }),
                    onRowDelete: (oldData) =>
                        new Promise((resolve) => {
                            this.handleRowDelete(oldData, resolve)
                        }),
                }}
            />
        );
    }

    render() {
        return (
            <div>{this.renderTable()}</div>
        );
    }
}

export default DividendList;
