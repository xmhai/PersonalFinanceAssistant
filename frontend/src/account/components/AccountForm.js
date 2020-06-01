import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import { Link } from 'react-router-dom';
import _ from 'lodash';

import { fetchCategories } from '../../common/actions';
import { fetchCurrencies } from '../../common/actions';
import { HtmlInput, HtmlSelect } from '../../shared/components/HtmlComponents';

class AccountForm extends React.Component {
  componentDidMount() {
    if (_.isEmpty(this.props.categories)) this.props.fetchCategories();
    if (_.isEmpty(this.props.currencies)) this.props.fetchCurrencies();
  }

  onSubmit = formValues => {
    this.props.onSubmit(formValues);
  };

  render() {
    return (
      <>
        <div className="pfa-form-title">{this.props.title}</div>
        <div className="ui divider"></div>
        <form onSubmit={this.props.handleSubmit(this.onSubmit)} className="ui form error pfa-form-2" >
          <Field name="instituteName" label="Institute Name:" colspan="2" component={HtmlInput} />
          <Field name="accountNo" label="Account Number:" component={HtmlInput} />
          <Field name="accountHolder" label="Account Holder:" component={HtmlInput} />
          <Field name="category" label="Asset Category:" component={HtmlSelect} data={this.props.categories} />
          <Field name="currency" label="Currency:" component={HtmlSelect} data={this.props.currencies} />
          <Field name="amount" label="Amount:" component={HtmlInput} />
          <Field name="maturityDate" label="Maturity Date:" component={HtmlInput} type="date" />
          <div className="pfa-form-button">
            <button className="ui button primary">Submit</button>
            <Link to="/accounts" className="ui button">
              Cancel
            </Link>
            </div>
        </form>
      </>
    );
  }
}

const validate = formValues => {
  const errors = {};

  if (!formValues.instituteName) {
    errors.instituteName = 'Please enter Institute Name';
  }

  if (!formValues.accountNo) {
    errors.accountNo = 'Please enter Account Number';
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
