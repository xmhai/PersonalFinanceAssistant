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
        <form onSubmit={this.props.handleSubmit(this.onSubmit)} className="ui form error pfa-form" >
          <Field name="instituteName" component={HtmlInput} label="Institute Name:" />
          <Field name="accountNo" component={HtmlInput} label="Account Number:" />
          <Field name="accountHolder" component={HtmlInput} label="Account Holder:" />
          <Field name="categoryId" component={HtmlSelect} data={this.props.categories} label="Asset Category:" />
          <Field name="currencyId" component={HtmlSelect} data={this.props.currencies} label="Currency:" />
          <Field name="amount" component={HtmlInput} label="Amount:" />
          <Field name="maturityDate" component={HtmlInput} type="date" label="Maturity Date:" />
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
  console.log("mapStateToProps() is called");
  console.log(state.config.categories);
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
