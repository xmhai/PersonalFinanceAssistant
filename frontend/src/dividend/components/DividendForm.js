import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import { Link } from 'react-router-dom';
import _ from 'lodash';

import { fetchStocks } from '../../stock/actions';
import { HtmlInput, HtmlSelect } from '../../shared/components/HtmlComponents';

class DividendForm extends React.Component {
  componentDidMount() {
    if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
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
          <Field name="stockId" label="Stock:" component={HtmlSelect} data={this.props.stocks} display="name" />
          <Field name="payDate" label="Pay Date:" component={HtmlInput} type="date" />
          <Field name="amount" label="Amount:" component={HtmlInput} autoComplete="off" />
          <div className="pfa-form-button">
            <button className="ui button primary">Submit</button>
            <Link to="/dividends" className="ui button">
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

  if (!formValues.dividendNo) {
    errors.dividendNo = 'Please enter Dividend Number';
  }

  return errors;
};

const mapStateToProps = state => {
  return {
    stocks: _.orderBy(Object.values(state.stocks), ['name'], ['asc']),
  }
};

DividendForm = connect(mapStateToProps, { fetchStocks } )(DividendForm);

export default reduxForm({
  form: 'dividendForm',
  validate
})(DividendForm);
