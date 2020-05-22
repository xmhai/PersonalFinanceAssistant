import React from 'react';
import { Field, reduxForm } from 'redux-form';

class AccountForm extends React.Component {
  renderError({ error, touched }) {
    if (touched && error) {
      return (
        <div className="ui error message">
          <div className="header">{error}</div>
        </div>
      );
    }
  }

  renderInput = ({ input, label, meta }) => {
    const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
    return (
      <div className={className}>
        <label>{label}</label>
        <input {...input} />
        {this.renderError(meta)}
      </div>
    );
  };

  onSubmit = formValues => {
    this.props.onSubmit(formValues);
  };

  render() {
    return (
      <form onSubmit={this.props.handleSubmit(this.onSubmit)} className="ui form error" >
        <Field name="instituteName" component={this.renderInput} label="Institute Name" />
        <Field name="accountNo" component={this.renderInput} label="Account No.:" />
        <Field name="accountHolder" component={this.renderInput} label="Account Holder:" />
        <Field name="categoryId" component={this.renderInput} label="Asset Category:" />
        <Field name="currencyId" component={this.renderInput} label="Currency:" />
        <Field name="amount" component={this.renderInput} label="Amount:" />
        <Field name="maturityDate" component={this.renderInput} label="Maturity Date:" />
        <button className="ui button primary">Submit</button>
      </form>
    );
  }
}

const validate = formValues => {
  const errors = {};

  if (!formValues.instituteName) {
    errors.instituteName = 'You must enter a title';
  }

  if (!formValues.accountNo) {
    errors.accountNo = 'You must enter a description';
  }

  return errors;
};

export default reduxForm({
  form: 'accountForm',
  validate
})(AccountForm);
