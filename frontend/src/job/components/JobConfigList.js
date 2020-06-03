import React, { useState, useEffect } from 'react';
import { forwardRef } from 'react';

import Alert from '@material-ui/lab/Alert';
import MaterialTable from "material-table";

import { jobService } from '../../apis';

function JobConfigList() {
    var columns = [
        { title: "id", field: "id", hidden: true },
        { title: "Job Name", field: "name" },
        { title: "Description", field: "description" },
        { title: "Cron Expression", field: "cronExpression" },
        { title: "Job ClassName", field: "jobClassName" },
        { title: "Start Time", field: "startTime" },
        { title: "End Time", field: "endTime" },
    ]
    const [data, setData] = useState([]); //table data

    //for error handling
    const [iserror, setIserror] = useState(false)
    const [errorMessages, setErrorMessages] = useState([])

    useEffect(() => {
        jobService.get("/job/configs")
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
                title="Job Configs"
                columns={columns}
                data={data}
            />
        </div>
    );
}

export default JobConfigList;
