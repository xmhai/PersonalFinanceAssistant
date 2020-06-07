import React, { useState, useEffect } from 'react';
import _ from 'lodash';

import Alert from '@material-ui/lab/Alert';
import IconButton from '@material-ui/core/IconButton';
import PhotoCamera from '@material-ui/icons/PhotoCamera';
import MaterialTable from "material-table";

import { accountService } from '../../apis';
import { Number } from '../../shared/components';

function BandTransaction() {
    var columns = [
        { title: "id", field: "id", hidden: true },
        { title: "Bank", field: "bank" },
        { title: "Transaction Date", field: "transactionDate", type: 'date' },
        { title: "Description", field: "description" },
        { title: "Debit", field: "debit", type: 'numeric', render: r => <Number value={r.debit} /> },
        { title: "Credit", field: "credit", type: 'numeric', render: r => <Number value={r.credit} /> },
    ]

    //for error handling
    const [iserror, setIserror] = useState(false)
    const [errorMessages, setErrorMessages] = useState([])

    const [data, setData] = useState([]); //table data
    useEffect(() => {
        accountService.get("/bank/transactions")
            .then(res => {
                setData(_.orderBy(Object.values(res.data), ['transactionDate'], ['desc']));
            })
            .catch(error => {
                console.log("Error");
                setIserror(true);
                setErrorMessages([error.errorMessages]);
            })
    }, []);

    const onFileSelect = e => {
        const formData = new FormData();
        formData.append('file', e.target.files[0]);
        const config = { headers: { 'content-type': 'multipart/form-data' } };
        accountService.post("/bank/transactions", formData, config)
            .then(res => {
                // refresh
            })
            .catch(error => {
                console.log(error);
                setIserror(true);
                setErrorMessages(["Failed to upload transaction file"]);
            })
    };

    return (
        <div>
            <div>
                {iserror &&
                    <Alert severity="error">
                        {errorMessages.map((msg, i) => {
                            return <div key={i}>{msg}</div>
                        })}
                    </Alert>
                }
            </div>
            <MaterialTable
                title="Bank Transaction History"
                columns={columns}
                data={data}
                options={{
                    search: true,
                    paging: true,
                    pageSize: 10,
                    pageSizeOptions: [10, 50, 100],
                    actionsColumnIndex: -1,
                    exportButton: true,
                    headerStyle: { fontWight: 900, fontSize: "14px" },
                    cellStyle: { fontSize: "14px" },
                    tableLayout: "fixed",
                }}
                actions={[
                    {
                        icon: () => {
                            return (<div>
                                <input accept="csv/*.csv" id="txnFile" style={{ display: 'none' }} type="file" onChange={onFileSelect} />
                                <label htmlFor="txnFile">
                                    <IconButton color="primary" aria-label="upload picture" component="span">
                                        <PhotoCamera />
                                    </IconButton>
                                </label> </div>)
                        },
                        tooltip: `Upload Transaction`,
                        isFreeAction: true,
                        onClick: () => {
                            //console.log('select file');
                        }
                    },
                ]}
            />
        </div>
    );
}

export default BandTransaction;
