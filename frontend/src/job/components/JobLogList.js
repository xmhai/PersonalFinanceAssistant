import React, { useState, useEffect } from 'react';

import Alert from '@material-ui/lab/Alert';
import MaterialTable from "material-table";

import { jobService } from '../../apis';

function JobLogList() {
    var columns = [
        { title: "id", field: "id", hidden: true },
        { title: "Job Name", field: "jobName" },
        { title: "Start Time", field: "startTime" },
        { title: "End Time", field: "endTime" },
        { title: "Completed", field: "isCompleted" },
        { title: "Message", field: "message" },
    ]
    const [data, setData] = useState([]); //table data

    //for error handling
    const [iserror, setIserror] = useState(false)
    const [errorMessages, setErrorMessages] = useState([])

    useEffect(() => {
        jobService.get("/job/logs")
            .then(res => {
                setData(res.data);
            })
            .catch(error => {
                console.log("Error");
                setIserror(true);
                setErrorMessages([error.errorMessages]);
            })
    }, [])

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
                title="Job Logs"
                columns={columns}
                data={data}
            />
        </div>
    );
}

export default JobLogList;
