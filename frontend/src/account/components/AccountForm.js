import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';

import { fetchCategories } from '../../common/actions';
import { fetchCurrencies } from '../../common/actions';
import { HtmlInput, HtmlSelect } from '../../shared/components/HtmlComponents';

class AccountForm extends React.Component {
  componentDidMount() {
    this.props.fetchCategories();
    this.props.fetchCurrencies();
  }

  onSubmit = formValues => {
    this.props.onSubmit(formValues);
  };

  render() {
    return (
      <form onSubmit={this.props.handleSubmit(this.onSubmit)} className="ui form error" >
        <Field name="instituteName" component={HtmlInput} label="Institute Name" />
        <Field name="accountNo" component={HtmlInput} label="Account No.:" />
        <Field name="accountHolder" component={HtmlInput} label="Account Holder:" />
        <Field name="categoryId" component={HtmlSelect} data={this.props.categories} label="Asset Category:" />
        <Field name="currencyId" component={HtmlSelect} data={this.props.currencies} label="Currency:" />
        <Field name="amount" component={HtmlInput} label="Amount:" />
        <Field name="maturityDate" component={HtmlInput} type="date" label="Maturity Date:" />
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
