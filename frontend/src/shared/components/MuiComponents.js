import React from 'react';
import TextField from '@material-ui/core/TextField';
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import MomentUtils from '@date-io/moment';
import { KeyboardDatePicker } from "@material-ui/pickers";

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


