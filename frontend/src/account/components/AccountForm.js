import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';

import { fetchCategories } from '../../common/actions';
import { fetchCurrencies } from '../../common/actions';

class AccountForm extends React.Component {
  componentDidMount() {
    this.props.fetchCategories();
    this.props.fetchCurrencies();
  }

  renderError({ error, touched }) {
    if (touched && error) {
      return (
        <div className="ui error message">
          <div className="header">{error}</div>
        </div>
      );
    }
  }

  renderInput = ({ input, type, label, meta }) => {
    const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
    return (
      <div className={className}>
        <label>{label}</label>
        <input {...input} type={type} />
        {this.renderError(meta)}
      </div>
    );
  };

  renderDropdown = ({ input, label, data }) => {
    if (data === undefined || data.length === 0) {
      return (
        <div className="field">
          <label>{label}</label>
          <select></select>
        </div >
      );
    }

    return (
      <div className="field">
        <label>{label}</label>
        <select {...input}>
          { data.map( (elem, index) => <option key={elem.id} value={elem.id}>{elem.code}</option>) }
        </select>
      </div >
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
        <Field name="categoryId" component={this.renderDropdown} data={this.props.categories} label="Asset Category:" />
        <Field name="currencyId" component={this.renderDropdown} data={this.props.currencies} label="Currency:" />
        <Field name="amount" component={this.renderInput} label="Amount:" />
        <Field name="maturityDate" component={this.renderInput} type="date" label="Maturity Date:" />
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

const mapStateToProps = state => {
  return {
    categories: Object.values(state.config.categories),
    currencies: Object.values(state.config.currencies)
  };
};

AccountForm = connect(mapStateToProps, { fetchCategories, fetchCurrencies } )(AccountForm);

export default reduxForm({
  form: 'accountForm',
  validate
})(AccountForm);
