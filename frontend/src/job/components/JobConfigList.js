import React, { useState, useEffect } from 'react';

import Alert from '@material-ui/lab/Alert';
/*
import { forwardRef } from 'react';
import MaterialTable from "material-table";
import AddBox from '@material-ui/icons/AddBox';
import ArrowDownward from '@material-ui/icons/ArrowDownward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
*/
import DirectionsRun from '@material-ui/icons/DirectionsRun';

import { MuiEditTable } from '../../shared/components/MuiComponents';
import { jobService } from '../../apis';

function JobConfigList() {
    const [data, setData] = useState([]); //table data

    //for error handling
    const [iserror, setIserror] = useState(false)
    const [errorMessages, setErrorMessages] = useState([])

    /*
    const tableIcons = {
        Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
        Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
        Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
        Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
        DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
        Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
        Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
        Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
        FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
        LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
        NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
        PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
        ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
        Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
        SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref} />),
        ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
        ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
    };
    */

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

    const handleRowUpdate = (newData, oldData, resolve, reject) => {
        let errorList = validateData(newData);
        if (errorList.length < 1) {
            jobService.put("/job/configs/" + newData.id, newData)
                .then(res => {
                    const dataUpdate = [...data];
                    const index = oldData.tableData.id;
                    dataUpdate[index] = newData;
                    setData([...dataUpdate]);
                    resolve();
                })
                .catch(error => {
                    setErrorMessages(errorList);
                    setIserror(true);
                    reject();

                })
        } else {
            setErrorMessages(errorList);
            setIserror(true);
            reject();
        }
    }

    const handleRowAdd = (newData, resolve, reject) => {
        let errorList = validateData(newData);
        if (errorList.length < 1) { //no error
            jobService.post("/job/configs", newData)
                .then(res => {
                    let dataToAdd = [...data];
                    dataToAdd.push(newData);
                    setData(dataToAdd);
                    resolve();
                    setErrorMessages([]);
                    setIserror(false);
                })
                .catch(error => {
                    setErrorMessages(["Cannot add data. Server error!"]);
                    setIserror(true);
                    reject();
                })
        } else {
            setErrorMessages(errorList)
            setIserror(true)
            reject()
        }
    }    

    const validateData = (newData) => {
        let errorList = []
        if (newData.name === undefined || newData.name === "") {
            errorList.push("Please enter job name")
        }
        if (newData.cronExpression === undefined || newData.cronExpression === "") {
            errorList.push("Please enter cron expression")
        }
        if (newData.jobClassName === undefined || newData.jobClassName === "") {
            errorList.push("Please enter Job ClassName")
        }
        return errorList;
    }

    const handleRowDelete = (oldData, resolve) => {

        jobService.delete("/job/configs/" + oldData.id)
            .then(res => {
                const dataDelete = [...data];
                const index = oldData.tableData.id;
                dataDelete.splice(index, 1);
                setData([...dataDelete]);
                resolve()
            })
            .catch(error => {
                setErrorMessages(["Delete failed! Server error"])
                setIserror(true)
                resolve()
            })
    }

    const triggerJob = (event, rowData) => {
        jobService.post("/job/configs/run/" + rowData.id)
            .then(res => {
                alert("Job triggered!");
            })
            .catch(error => {
                setErrorMessages(["Delete failed! Server error"])
                setIserror(true)
            })
    }

    var columns = [
        { title: "id", field: "id", hidden: true },
        { title: "Job Name", field: "name" },
        { title: "Description", field: "description" },
        { title: "Cron Expression", field: "cronExpression" },
        { title: "Job ClassName", field: "jobClassName" },
        { title: "Start Time", field: "startTime", type: "datetime"},
        { title: "End Time", field: "endTime", type: "datetime" },
    ];

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
            <MuiEditTable
                title="Job Configs"
                columns={columns}
                data={data}
                //icons={tableIcons}
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
                        icon: () => <DirectionsRun />,
                        tooltip: `Trigger`,
                        onClick: triggerJob,
                    },
                ]}
                editable={{
                    onRowUpdate: (newData, oldData) =>
                        new Promise((resolve, reject) => {
                            handleRowUpdate(newData, oldData, resolve);
                        }),
                    onRowAdd: (newData) =>
                        new Promise((resolve, reject) => {
                            handleRowAdd(newData, resolve)
                        }),
                    onRowDelete: (oldData) =>
                        new Promise((resolve) => {
                            handleRowDelete(oldData, resolve)
                        }),
                }}            />
        </div>
    );
}

export default JobConfigList;
