import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import { Link } from 'react-router-dom';
import _ from 'lodash';

import { fetchExchanges } from '../../common/actions';
import { fetchCategories } from '../../common/actions';
import { fetchCurrencies } from '../../common/actions';
import { HtmlInput, HtmlSelect } from '../../shared/components/HtmlComponents';

class StockForm extends React.Component {
  componentDidMount() {
    if (_.isEmpty(this.props.exchanges)) this.props.fetchExchanges();
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
          <Field name="name" label="Stock Name:" component={HtmlInput} />
          <Field name="code" label="Stock Code:" component={HtmlInput} />
          <Field name="exchange" label="Exchange:" component={HtmlSelect} data={this.props.exchanges} />
          <Field name="category" label="Asset Category:" component={HtmlSelect} data={this.props.categories} />
          <Field name="currency" label="Currency:" component={HtmlSelect} data={this.props.currencies} />
          <Field name="latestPrice" label="Latest Price:" component={HtmlInput} />
          <div className="pfa-form-button">
            <button className="ui button primary">Submit</button>
            <Link to="/stocks" className="ui button">
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

  if (!formValues.stockNo) {
    errors.stockNo = 'Please enter Stock Number';
  }

  return errors;
};

const mapStateToProps = state => {
  return {
    exchanges: Object.values(state.config.exchanges),
    categories: Object.values(state.config.categories),
    currencies: Object.values(state.config.currencies)
  };
};

StockForm = connect(mapStateToProps, { fetchExchanges, fetchCategories, fetchCurrencies } )(StockForm);

export default reduxForm({
  form: 'stockForm',
  validate
})(StockForm);
