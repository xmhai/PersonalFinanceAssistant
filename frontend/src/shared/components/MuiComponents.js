import React from 'react';
import TextField from '@material-ui/core/TextField';
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import MomentUtils from '@date-io/moment';
import { KeyboardDatePicker } from "@material-ui/pickers";
import MaterialTable from 'material-table';

import history from '../../history';

export const MuiTable = ({ ...props }) => {
  return (
    <MaterialTable
      {...props}
      options={{
        search: true,
        paging: false,
        actionsColumnIndex: -1,
        exportButton: true,
        headerStyle: { fontWight: 900 }

      }}
      actions={[
        {
          icon: 'add',
          tooltip: 'Create Account',
          isFreeAction: true,
          style: { "color": "green" },
          onClick: (event, rowData) => {
            history.push(`${props.baseUrl}/new`);
          }
        },
        {
          icon: 'edit',
          tooltip: 'Edit Account',
          onClick: (event, rowData) => {
            history.push(`${props.baseUrl}/edit/${rowData.id}`);
          }
        },
        {
          icon: 'delete',
          tooltip: 'Delete Account',
          onClick: (event, rowData) => {
            history.push(`${props.baseUrl}/delete/${rowData.id}`);
          }
        }
      ]}
    />
  );
}

export const MuiTextField = ({ input, label, meta, type }) => {
  const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
  return (
    <div className={className}>
      <label>{label}</label>
      <TextField
        {...input}
        type={type}
      />
      {renderError(meta)}
    </div>
  );
};

export const MuiDatePicker = ({ input, label, meta, type }) => {
  const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
  return (
    <div className={className}>
      <label>{label}</label>
      <MuiPickersUtilsProvider utils={MomentUtils}>
        <KeyboardDatePicker
          {...input}
          clearable
          minDate={new Date()}
          format="DD-MM-YYYY"
        />
      </MuiPickersUtilsProvider>
      {renderError(meta)}
    </div>
  );
};

const renderError = ({ error, touched }) => {
  if (touched && error) {
    return (
      <div className="ui error message">
        <div className="header">{error}</div>
      </div>
    );
  }
}


